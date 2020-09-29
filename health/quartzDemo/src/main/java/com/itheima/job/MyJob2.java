package com.itheima.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Terrance
 * @Date: 2020-09-25 17:33
 *
 * 注解
 */
@Component
public class MyJob2 {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * simpleTrigger
     * initialDelay: 启动时延迟多少毫秒后才执行
     * fixedDelay: 每间隔多长时间执行
     */
    //@Scheduled(initialDelay = 1000,fixedDelay = 2000)
    // 七子表达式 crontrigger
    @Scheduled(cron = "0/2 * * * * ?")
    public void tt(){
        System.out.println("job2:" + sdf.format(new Date()));
    }
}
