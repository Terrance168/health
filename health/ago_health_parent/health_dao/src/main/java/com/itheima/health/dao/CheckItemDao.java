package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-18 20:04
 */
public interface CheckItemDao {
    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 判断检查项是否被使用，是否被CheckGroup引用了
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 编辑检查项：根据id查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 编辑检查项：更新
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
