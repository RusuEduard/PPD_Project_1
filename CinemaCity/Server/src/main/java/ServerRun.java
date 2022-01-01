import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

import java.time.LocalDateTime;

public class ServerRun {
    public static void main(String[] args){
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:service_spring.xml");
        System.out.println("Server pornit!");
    }
}
