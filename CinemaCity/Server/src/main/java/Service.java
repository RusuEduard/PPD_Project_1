import services.IObserver;
import services.IService;
import services.MyException;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {

    public Map<Long, IObserver> clientsList;

    public Service() {
        this.clientsList = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void message(String message) {
        System.out.printf("Server: %s%n", message);
    }

    @Override
    public void login(IObserver client, String name) {
        clientsList.put((long) 1, client);
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
