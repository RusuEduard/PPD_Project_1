package services;

import domain.Spectacol;
import repository.RepoException;

import java.time.LocalDateTime;
import java.util.List;

public interface IService {
    public void message(String message);
    public void login(IObserver client, String name);
    public LocalDateTime getTime();
    void logout(IObserver clientConsole, String name);

    List<Spectacol> getNextShows();

    List<Integer> getFreeSeatsForShow(Spectacol spectacol) throws RepoException;

    String buyTickets(Spectacol spectacol, List<Integer> locuriAlese) throws RepoException, MyException;
}