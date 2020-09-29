package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-23 22:09
 */
public interface SetmealService {

    /**
     * 添加
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 通过套餐id查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 编辑后确认提交
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);


    /**
     * 根据套餐id删除套餐信息
     * @param id
     */
    void deleteById(int id) throws HealthException;

    /**
     * 获取所有套餐的图片
     * @return
     */
    List<String> findImgs();

    /**
     * 查询所有的套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
