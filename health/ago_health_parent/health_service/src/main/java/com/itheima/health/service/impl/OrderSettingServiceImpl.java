package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Terrance
 * @Date: 2020-09-26 18:39
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置
     *
     * @param list
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> list) throws HealthException {
        //遍历
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //通过日期查询预约设置信息
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //存在
                if (osInDB != null){
                    //判断要设置的最大预约数量是否<已预约的数量
                    if (orderSetting.getNumber() < osInDB.getReservations()){
                        //是则抛出异常
                        throw new HealthException("最大预约数量不能小于已预约数量");
                    }
                    //否，则通过日期更新最大预约数
                    orderSettingDao.updateNumber(orderSetting);
                }else {
                    //不存在
                    //插入预约设置
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 通过月份获取预约设置数据
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        //拼接开始日期与结束日期
        String startDate = month + "-01";
        String endDate = month + "-31";
        List<Map<String, Integer>> monthData = orderSettingDao.getOrderSettingBetween(startDate, endDate);
        return monthData;
    }

    /**
     * 基于日期的预约设置
     *
     * @param os
     */
    @Override
    public void editNumberByDate(OrderSetting os) throws HealthException {
        //通过日期查询预约设置信息
        OrderSetting osInDB = orderSettingDao.findByOrderDate(os.getOrderDate());
        if (osInDB != null){
            //判断要设置的最大预约数量是否<已预约的数量
            if (os.getNumber() < osInDB.getReservations()){
                //是则抛出异常
                throw new HealthException("最大预约数量不能小于已预约数量");
            }
            //否，则通过日期更新最大预约数
            orderSettingDao.updateNumber(os);
        }else {
            //不存在
            //插入预约设置
            orderSettingDao.add(os);
        }
    }
}
