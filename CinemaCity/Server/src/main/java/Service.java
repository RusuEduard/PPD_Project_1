import domain.Spectacol;
import repository.IRepoSpectacole;
import services.IObserver;
import services.IService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Service implements IService {

    private final Map<String, IObserver> clientsList;
    private final IRepoSpectacole repoSpectacole;

    private LocalDateTime creationTime;

    private Timer timer;

    public Service(IRepoSpectacole repoSpectacole) {
        this.repoSpectacole = repoSpectacole;
        this.clientsList = new ConcurrentHashMap<>();
        this.creationTime = LocalDateTime.now();
        timer = new Timer();
        initTimer();
    }

    private void initTimer() {
        this.timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if(notifyClients())
                            System.exit(0);
                            //System.out.println("finished");
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
        System.out.println("In service");
        return repoSpectacole.getNextShows(LocalDateTime.now());
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
