import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author: Terrance
 * @Date: 2020-09-18 20:54
 *
 * 启动类
 */
public class HealthServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        //阻塞
        System.in.read();
    }
}
