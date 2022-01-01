package repository;

import domain.Spectacol;

import java.time.LocalDateTime;
import java.util.List;

public interface iRepoSpectacole extends Repository<Long, Spectacol>{

    List<Spectacol> getNextShows(LocalDateTime date);

    List<Long> getFreeSeats(Long idSpectacol);

}
