import domain.Spectacol;
import repository.IRepoSpectacole;
import repository.IRepoVanzari;
import repository.RepoException;
import services.IObserver;
import services.IService;
import services.MyException;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class Service implements IService {

    private final Map<String, IObserver> clientsList;
    private final IRepoSpectacole repoSpectacole;
    private final IRepoVanzari repoVanzari;

    private List<Spectacol> nextShows;

    private final LocalDateTime creationTime;

    private final Timer timer;

    private final ExecutorService pool;

    public Service(IRepoSpectacole repoSpectacole, IRepoVanzari repoVanzari) {
        this.repoSpectacole = repoSpectacole;
        this.repoVanzari = repoVanzari;
        this.clientsList = new ConcurrentHashMap<>();
        this.creationTime = LocalDateTime.now();
        this.nextShows = new ArrayList<>();

        timer = new Timer();
        pool = Executors.newFixedThreadPool(8);
        initTimer();

        initNextShows();
    }

    private void initNextShows() {
        this.nextShows = this.repoSpectacole.getNextShows(LocalDateTime.now());
    }

    private void initTimer() {
        this.timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if(notifyClients())
                            System.exit(0);
                    }
                },
                120000
        );
    }

    private boolean notifyClients() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (var client : clientsList.entrySet()){
            if(client.getValue() != null){
                executor.execute(()->{
                    System.out.println("Notify");
                    try {
                        client.getValue().serverShutdown();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    public LocalDateTime getTime() {
        return creationTime;
    }

    @Override
    public void logout(IObserver clientConsole, String name) {
        IObserver client = clientsList.remove(name);
    }

    @Override
    public List<Spectacol> getNextShows() {
        return this.nextShows;
    }

    @Override
    public List<Integer> getFreeSeatsForShow(Spectacol spectacol) throws RepoException {
        return repoSpectacole.getFreeSeatsForShow(spectacol);
    }

    @Override
    public synchronized String buyTickets(Spectacol spectacol, List<Integer> locuriAlese) throws RepoException, MyException {
        var task = new BuyTicketsTask(repoSpectacole, repoVanzari, spectacol, locuriAlese);
        Future<String> result =  pool.submit(task);
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MyException("An error was cought!");
        }
    }

    @Override
    public synchronized void message(String message) {
        System.out.printf("Server: %s%n", message);
    }

    @Override
    public void login(IObserver client, String name) {
        clientsList.put(name, client);
        System.out.printf("Client %s connected!%n", name);
    }


//
//    @Override
//    public void LogIn(Juriu user, IObserver client) throws MyException {
//        if(list_users.get(user.getId())!=null)
//            throw  new MyException("user deja autentidicat!");
//        list_users.put(user.getId(),client);
//    }
//
//    @Override
//    public void LogOut(Juriu user, IObserver client) {
//        list_users.remove(user.getId());
//    }
//


}

//    private final int defaultThreadNo=5;
//    private void notifica(Participant vechi,Participant nou){
//        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadNo);
//
//        Iterable<Juriu> users = repo_juriu.getAll();
//
//        for (Juriu us:users){
//            IObserver x = list_users.get(us.getId());
//            if(x!=null){
//                executor.execute(()->{
//                    try {
//                        System.out.println("Notify");
//                        x.nota_modificata(vechi,nou);
//                    } catch (MyException eexception) {
//                        eexception.printStackTrace();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        }
//    }
