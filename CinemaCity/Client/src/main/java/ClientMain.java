import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import services.MyException;

import java.nio.channels.IllegalSelectorException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {
        System.out.println("Enter your name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        IService service = (IService) factory.getBean("serviceL");
        System.out.println("Client: Client pornit!");
        ClientConsole console = new ClientConsole();
        console.setService(service);
        console.setName(name);
        try {
            console.run();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        System.gc();
        System.exit(0);
    }
}
