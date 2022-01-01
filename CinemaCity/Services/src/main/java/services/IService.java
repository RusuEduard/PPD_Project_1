package services;

import java.time.LocalDateTime;

public interface IService {
    public void message(String message);
    public void login(IObserver client, String name);
    public LocalDateTime getTime();
    void logout(IObserver clientConsole, String name);
}