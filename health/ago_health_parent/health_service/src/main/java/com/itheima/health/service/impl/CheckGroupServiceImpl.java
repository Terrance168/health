package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-21 21:17
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 查询所有的检查组
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    //事务注解只能写在需要事务的方法上，不要写在类上
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        int checkGroupId = checkGroup.getId();
        //再添加检查组与检查项的关系，遍历循环检查项的id
        if (checkitemIds != null){
            for (Integer checkItemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkItemId);
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //有查询条件
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //分页查询，将会被分页
        Page<CheckGroup> groupPage = checkGroupDao.selectByCodition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(groupPage.getTotal(), groupPage.getResult());
    }

    /**
     * 通过id查询检查组信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 查询选中的检查项id集合
     *
     * @param checkGroupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    /**
     * 编辑检查组并提交
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组信息
        checkGroupDao.update(checkGroup);
        //删除旧的关系
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.removeCheckGroupCheckItem(checkGroupId);
        //添加新的关系
        if (checkitemIds != null){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 删除检查组(根据id删除)
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        //先判断是否被订单使用
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count > 0){
            //已经被使用了，抛出异常
            throw new HealthException("该检查组已经被使用了，不能删除");
        }
        //没被使用，先删除关系
        checkGroupDao.removeCheckGroupCheckItem(id);
        //再根据id删除检查组
        checkGroupDao.deleteById(id);
    }
}
