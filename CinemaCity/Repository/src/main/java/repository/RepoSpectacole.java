package repository;

import domain.Spectacol;
import net.bytebuddy.asm.Advice;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoSpectacole implements IRepoSpectacole{

    private final JdbcUtils dbUtils;

    public RepoSpectacole(Properties properties){
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public List<Spectacol> getNextShows(LocalDateTime date) {
        List<Spectacol> spectacole = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("select * from spectacole where spectacole.data_spectacol > ?")){
            statement.setTimestamp(1, Timestamp.valueOf(date));
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    long id = set.getLong("id_spectacol");
                    LocalDateTime data = set.getTimestamp("data_spectacol").toLocalDateTime();
                    String titlu = set.getString("titlu");
                    float pretBilet = set.getFloat("pret_bilet");
                    Spectacol spectacol = new Spectacol();
                    spectacol.setData(data);
                    spectacol.setPretBilet(pretBilet);
                    spectacol.setTitlu(titlu);
                    spectacol.setId(id);
                    spectacole.add(spectacol);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(spectacole.size());
        return spectacole;
    }

    @Override
    public List<Integer> getFreeSeatsForShow(Spectacol spectacol) throws RepoException {
        int numberOfSeats = 0;
        List<Integer> freeSeats = new ArrayList<>();
        List<Integer> boughtSeats = new ArrayList<>();

        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("select sali.nr_locuri from sali inner join spectacole s on sali.id_sala = s.id_sala where s.id_spectacol = ?;")) {
            statement.setLong(1, spectacol.getId());
            try (ResultSet set = statement.executeQuery()){
                if(set.next()){
                    numberOfSeats = set.getInt("nr_locuri");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (numberOfSeats == 0){
            throw new RepoException("Number of seats for this show was not found!");
        }

        try (PreparedStatement statement = con.prepareStatement("select nr_loc from bilete inner join vanzari v on v.id_vanzare = bilete.id_vanzare inner join spectacole s on s.id_spectacol = v.id_spectacol where s.id_spectacol = ?")){
            statement.setLong(1, spectacol.getId());
            try (ResultSet set = statement.executeQuery()){
                while (set.next()){
                    int seatNr = set.getInt("nr_loc");
                    boughtSeats.add(seatNr);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(boughtSeats.size() == numberOfSeats)
            return freeSeats;

        for (int i = 1; i < numberOfSeats; i++) {
            if (!boughtSeats.contains(i)){
                freeSeats.add(i);
            }
        }

        return freeSeats;
    }

    @Override
    public Spectacol save(Spectacol entity) {
        return null;
    }

    @Override
    public Spectacol delete(Long aLong) {
        return null;
    }

    @Override
    public List<Spectacol> getAll() {
        return null;
    }

    @Override
    public Spectacol findOne(Long aLong) {
        return null;
    }
}
