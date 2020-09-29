package com.itheima.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Terrance
 * @Date: 2020-09-28 15:27
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        //通过版本号创建freemarker配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板所在目录
        File templateDir = new File("d:/ftl");
        configuration.setDirectoryForTemplateLoading(templateDir);
        //设置使用的默认编码
        configuration.setDefaultEncoding("utf-8");
        //创建数据模型map
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("name","Jacky Chen");
        dataMap.put("message","Nice to meet you!");

        //获取模板。模板名即为文件名
        Template template = configuration.getTemplate("ftl.ftl");
        //创建writer
        FileWriter writer = new FileWriter(new File(templateDir, "test.html"));
        //填充数据到模板，保存到文件里
        template.process(dataMap, writer);
        //关闭writer
        writer.close();
    }
}
