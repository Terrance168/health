package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-23 22:12
 */
public interface SetmealDao {

    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId")Integer checkgroupId);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<Setmeal> selectByCondition(String queryString);

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
     * 更新套餐信息
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除旧的关系
     * @param setmealId
     */
    void removeSetmealCheckGroup(Integer setmealId);

    /**
     * 判断是否存在订单
     * @param id
     * @return
     */
    int findOrderCountBySetmeal(int id);

    /**
     * 根据套餐id删除套餐信息
     * @param id
     */
    void deleById(int id);

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
