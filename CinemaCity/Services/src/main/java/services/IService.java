package services;

public interface IService {
    public void message(String message);
    public void login(IObserver client, String name);
}