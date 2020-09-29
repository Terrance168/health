package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-23 22:11
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改套餐的信息
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer setmealId = setmeal.getId();
        //添加关系
        if (checkgroupIds != null){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //第一个参数为页码，第二个为每页大小，默认查询总是count
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //判断是否有条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //有则执行模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //调用dao层查询
        Page<Setmeal> setmealPage = setmealDao.selectByCondition(queryPageBean.getQueryString());
        //封装分页结果
        return new PageResult<Setmeal>(setmealPage.getTotal(), setmealPage.getResult());
    }

    /**
     * 通过id查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 通过套餐id查询选中的检查组id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 编辑后确认提交
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 修改套餐的信息
        setmealDao.update(setmeal);
        // 获取套餐的id
        Integer setmealId = setmeal.getId();
        // 先删除后添加
        setmealDao.removeSetmealCheckGroup(setmealId);
        // 添加新关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 根据套餐id删除套餐信息
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {

        //先判断是否存在订单，如果存在则不能删除
        int count = setmealDao.findOrderCountBySetmeal(id);
        if (count > 0){
            //有订单，抛出异常
            throw new HealthException("已经有订单使用了这个套餐，暂不能删除！");
        }
        //没被使用，先删除套餐与检查组的关系
        setmealDao.removeSetmealCheckGroup(id);
        //再删除套餐
        setmealDao.deleById(id);

    }

    /**
     * 获取所有套餐的图片
     *
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }

    /**
     * 查询所有的套餐
     *
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }


}
