import domain.Spectacol;
import repository.RepoException;
import services.IObserver;
import services.IService;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

    public void run() {
        Scanner in = new Scanner(System.in);
        this.service.login(this, this.name);
        this.running = true;
        while (running){
            try {
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
            catch (MyException | RepoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void buyTicket() throws MyException, RepoException {
        if (this.service == null)
            throw new MyException("Server is down!\n");

        Scanner in = new Scanner(System.in);

        if (this.service == null)
            throw new MyException("Server is down!\n");
        List<Spectacol> spectacole = service.getNextShows();
        System.out.println("Alege spectacol: ");
        for (int i = 1; i <= spectacole.size(); i++) {
            var spectacol = spectacole.get(i-1);
            System.out.printf("------------------------%n%d. Title: %s%n   Date: %s%n   Price: %f%n", i, spectacol.getTitlu(), spectacol.getData().toString(), spectacol.getPretBilet());
        }

        int index = in.nextInt();
        in.nextLine();

        if (index < 1 || index > spectacole.size())
            throw new MyException("Spectacolul ales nu exista!\n");

        var spectacol = spectacole.get(index - 1);

        if (this.service == null)
            throw new MyException("Server is down!\n");
        List<Integer> locuriLibere = service.getFreeSeatsForShow(spectacol);
        System.out.println("Alege locuri, separate prin spatiu: ");
        for (var loc : locuriLibere){
            System.out.print(loc + " ");
        }

        String locuri = in.nextLine();

        List<Integer> locuriAlese = parseLocuri(locuri);

        if(locuriAlese.isEmpty() || locuriAlese.size() > locuriLibere.size()){
            throw new MyException("Numar de locuri alese invalid!\n");
        }

        for (var loc : locuriAlese){
            if (!locuriLibere.contains(loc))
                throw new MyException("Locul " + loc + " nu este valabil!\n");
        }

        if (this.service == null)
            throw new MyException("Server is down!\n");

        System.out.println(service.buyTickets(spectacol, locuriAlese));
    }

    private List<Integer> parseLocuri(String locuri) throws MyException {
        List<Integer> locuriAlese = new ArrayList<>();

        locuri = locuri.strip();
        var listaLocuri = locuri.split(" ");
        for (var loc : listaLocuri){
            try {
            locuriAlese.add(Integer.parseInt(loc));
            }
            catch (NumberFormatException ex){
                throw new MyException("Invalid number: " + loc + "\n");
            }
        }

        return locuriAlese;
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
