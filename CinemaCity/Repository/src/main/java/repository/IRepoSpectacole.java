package repository;

import domain.Spectacol;

import java.time.LocalDateTime;
import java.util.List;

public interface IRepoSpectacole extends Repository<Long, Spectacol>{

    List<Spectacol> getNextShows(LocalDateTime date);

}
