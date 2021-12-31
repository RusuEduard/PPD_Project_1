import services.IObserver;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientConsole extends UnicastRemoteObject implements IObserver {
    private IService service;
    private boolean running = false;
    private String name;

    public ClientConsole() throws RemoteException{
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(){
        Scanner in = new Scanner(System.in);
        this.service.login(this, this.name);
        this.running = true;
        while (running){
            displayMenu();
            int cmd = in.nextInt();
            switch (cmd) {
                case 1 -> System.out.println("Ticket bought!\n");
                case 0 -> {
                    this.running = false;
                    System.out.println("Client finished!\n");
                }
                default -> System.out.println("Unknown command!\n");
            }
        }
    }

    private void displayMenu() {
        System.out.print("Choose an option:\n\t1: Buy ticket!\n\t0: Exit!\n");
    }
}
