package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Terrance
 * @Date: 2020-09-18 20:01
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有的检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 新增检查项
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //第一个参数为页码，第二个每页大小，默认查询总数count
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //紧跟着的第一个select方法会被分页
        // 判断是否有查询条件，
        String queryString = queryPageBean.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            // 如果有则要执行模糊查询
            queryString = "%" + queryString + "%";
        }
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        // 封装分页结果
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) throws HealthException {
        //校验这个检查项是否已经被使用
        //    t_checkgroup_checkitem表中有没有记录 select count(1) from t_checkgroup_checkitem where checkitem_id=id
        // 如果有的话，则不能删除
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0){
            //controller就得要捕获
            throw new HandlerException("这个检查项已经被使用了，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    /**
     * 编辑检查项：根据id查询
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 编辑检查项：更新
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
