package repository;

import domain.Spectacol;
import domain.Vanzare;

import java.util.List;

public interface IRepoVanzari extends Repository<Long, Vanzare>{

    void sellTickets(Spectacol spectacol, List<Integer> locuriAlese);
}
