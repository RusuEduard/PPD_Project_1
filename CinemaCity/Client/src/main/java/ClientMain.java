import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

import java.nio.channels.IllegalSelectorException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        IService service = (IService) factory.getBean("serviceL");
        System.out.println("Client: Client pornit!");
        service.message("Client connected!");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        ClientConsole console = new ClientConsole();
        console.setService(service);
        console.setName(name);
        console.run();
    }
}
