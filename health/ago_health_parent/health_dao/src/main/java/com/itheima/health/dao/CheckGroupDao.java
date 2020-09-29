package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-21 21:30
 */
public interface CheckGroupDao {

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组：添加检查组与检查项的关系t_checkgroup_checkitem
     *      * 注意，对参数类型相同时，要给参数取别名
     *
     * @param checkGroupId
     * @param checkItemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") int checkGroupId, @Param("checkItemId") int checkItemId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> selectByCodition(@Param("queryString") String queryString);

    /**
     * 通过id查询检查组信息
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 查询选中的检查项id集合
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    /**
     * 编辑检查组信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);


    /**
     *  通过检查组id删除与检查项的关系
     * @param checkGroupId
     */
    void removeCheckGroupCheckItem(Integer checkGroupId);

    /**
     * 删除检查组(根据id删除)
     * @param id
     */
    void deleteById(int id);

    /**
     * 判断是否被套餐使用了
     * @param id
     * @return
     */
    int findSetmealCountByCheckGroupId(int id);
}
