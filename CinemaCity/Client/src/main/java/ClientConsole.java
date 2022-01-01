import domain.Spectacol;
import services.IObserver;
import services.IService;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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

    public void run() throws MyException {
        Scanner in = new Scanner(System.in);
        this.service.login(this, this.name);
        this.running = true;
        while (running){
            displayMenu();
            int cmd = in.nextInt();
            switch (cmd) {
                case 1 -> {
                    this.buyTicket();
                }
                case 0 -> {
                    this.running = false;
                    System.out.println("Client finished!\n");
                }
                default -> System.out.println("Unknown command!\n");
            }
        }
    }

    private void buyTicket() throws MyException {
        if (this.service == null)
            throw new MyException("Server is down!");

        List<Spectacol> spectacole = service.getNextShows();
        System.out.println("Alege spectacol: ");
        for (int i = 1; i <= spectacole.size(); i++) {
            var spectacol = spectacole.get(i-1);
            System.out.printf("------------------------%n%d. Title: %s%n   Date: %s%n   Price: %f%n", i, spectacol.getTitlu(), spectacol.getData().toString(), spectacol.getPretBilet());
        }
    }

    private void displayMenu() {
        System.out.print("Choose an option:\n\t1: Buy ticket!\n\t0: Exit!\n");
    }

    @Override
    public void serverShutdown() {
        System.out.println("Server is shutting down!");
        this.service.logout(this, this.name);
        this.running = false;
        this.service = null;
    }
}
