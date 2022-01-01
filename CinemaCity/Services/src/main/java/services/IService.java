package services;

import domain.Spectacol;

import java.time.LocalDateTime;
import java.util.List;

public interface IService {
    public void message(String message);
    public void login(IObserver client, String name);
    public LocalDateTime getTime();
    void logout(IObserver clientConsole, String name);

    List<Spectacol> getNextShows();
}