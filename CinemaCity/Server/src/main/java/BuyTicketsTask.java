import domain.Spectacol;
import repository.IRepoSpectacole;
import repository.IRepoVanzari;
import repository.RepoException;
import services.MyException;

import java.util.List;
import java.util.concurrent.Callable;

public class BuyTicketsTask implements Callable<String> {

    private final IRepoSpectacole repoSpectacole;
    private final IRepoVanzari repoVanzari;
    private final Spectacol spectacol;
    private final List<Integer> listaLocuri;

    public BuyTicketsTask(IRepoSpectacole repoSpectacole, IRepoVanzari repoVanzari, Spectacol spectacol, List<Integer> listaLocuri) {
        this.repoSpectacole = repoSpectacole;
        this.repoVanzari = repoVanzari;
        this.spectacol = spectacol;
        this.listaLocuri = listaLocuri;
    }

    @Override
    public String call() throws Exception {
        List<Integer> freeSeats = null;
        try {
            freeSeats = repoSpectacole.getFreeSeatsForShow(spectacol);

        for (var seat : listaLocuri){
            if (!freeSeats.contains(seat))
                return ("Seat " + seat + " is no longer available!");
        }

        repoVanzari.sellTickets(spectacol, listaLocuri);

        return "Success!";
        } catch (RepoException e) {
            return e.getMessage();
        }
    }
}
