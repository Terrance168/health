package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

/**
 * @Author: Terrance
 * @Date: 2020-09-18 19:50
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /**
     * Reference 使用的是Dubbo的包，调用远程服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        //调用业务服务查询
        List<CheckItem> list = checkItemService.findAll();
        //封装查询结果并返回
        return new Result(true, "查询成功", list);
    }

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //调用业务服务添加
        checkItemService.add(checkItem);
        //返回操作的结果
        return new Result(true, "添加成功");
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务服务分页查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //响应给前端 res.data => {flag,message,data:{rows:[],total:}}
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result deleteById(int id){
        //调用业务层删除
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 编辑检查项：根据id查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        //根据业务层查询
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 编辑检查项：更新
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //调用业务层更新
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
