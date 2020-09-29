package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Terrance
 * @Date: 2020-09-23 20:42
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result uploadImage(MultipartFile imgFile){
        //1、获取图片的扩展名
        String originalFilename = imgFile.getOriginalFilename();
        //获取图片的后缀(.jpg)
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //2、生成唯一的名称
        String newFilename = UUID.randomUUID().toString() + extension;

        try {
            //3、调用七牛上传
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), newFilename);
            //4、返回结果给前端
            Map<String, String> mapData = new HashMap<String, String>();
            mapData.put("domain", QiNiuUtils.DOMAIN);
            mapData.put("imgName", newFilename);

            //存入redis中的所有的上传图片
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFilename);
            jedis.close();
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, mapData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new HealthException("上传图片失败");
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        //调用服务层
        setmealService.add(setmeal, checkgroupIds);
        //添加成功，要记录有用的图片到redis集合中
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        jedis.close();
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页条件查询
     * 注意：Get请求不可以使用RequestBody来接收参数
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务层查询
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 通过id查询套餐信息
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务查询套餐信息
        Setmeal setmeal = setmealService.findById(id);
        // 补全图片的完整路径,
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    /**
     * 通过套餐id查询选中的检查组id集合
     * @return
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id){
        //调用服务层查询
        List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,checkGroupIds);
    }

    /**
     * 编辑后确认提交
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        // 获取原有图片的名称，判断图片是否更改了，如果更改了，那么旧的图片应该从有用的集合中移除
        Setmeal setmealInDb = setmealService.findById(setmeal.getId());
        // 调用服务更新
        setmealService.update(setmeal, checkgroupIds);
        Jedis jedis = jedisPool.getResource();
        // 判断是否是需要从有用的集合中删除
        if(!setmealInDb.getImg().equals(setmeal.getImg())){
            //图片修改了，旧的就没用，就要删除
            jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmealInDb.getImg());
        }
        // 修改成功，要记录有用的图片到redis集合中
        jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        jedis.close();
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 根据套餐id删除套餐信息
     * @return
     */
    @PostMapping("/delete")
    public Result deleteById(int id){
        //查一下图片名称
        Setmeal setmeal = setmealService.findById(id);
        //调用业务层删除
        setmealService.deleteById(id);
        //从redis，保存了数据库存放的图片集合中移除这个被删除的图片
        Jedis jedis = jedisPool.getResource();
        jedis.srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        jedis.close();
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
