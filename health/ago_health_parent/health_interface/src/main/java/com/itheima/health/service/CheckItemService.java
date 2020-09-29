package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-18 19:59
 */
public interface CheckItemService {
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
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

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
