import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

import java.rmi.RemoteException;
import java.util.Scanner;

public class RandomClient {
    public static void main(String[] args) throws RemoteException {
        System.out.println("Enter your name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        IService service = (IService) factory.getBean("serviceL");

    }
}
