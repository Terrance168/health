package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author: Terrance
 * @Date: 2020-09-26 18:37
 */
public interface OrderSettingService {
    /**
     * 批量导入预约设置
     * @param list
     */
    void add(List<OrderSetting> list) throws HealthException;

    /**
     * 通过月份获取预约设置数据
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 基于日期的预约设置
     * @param os
     */
    void editNumberByDate(OrderSetting os) throws HealthException;
}
