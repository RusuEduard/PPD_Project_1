package repository;

import domain.Entity;

import java.util.List;

public interface Repository<ID,E extends Entity<ID>>{

    E save(E entity);

    E delete(ID id);

    List<E> getAll();

    E findOne(ID id);
}
