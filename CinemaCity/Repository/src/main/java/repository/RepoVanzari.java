package repository;

import domain.Spectacol;
import domain.Vanzare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class RepoVanzari implements IRepoVanzari{

    private final JdbcUtils dbUtils;

    public RepoVanzari(Properties properties){
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Vanzare save(Vanzare entity) {
        return null;
    }

    @Override
    public Vanzare delete(Long aLong) {
        return null;
    }

    @Override
    public List<Vanzare> getAll() {
        return null;
    }

    @Override
    public Vanzare findOne(Long aLong) {
        return null;
    }

    @Override
    public void sellTickets(Spectacol spectacol, List<Integer> locuriAlese) {
        Connection con = this.dbUtils.getConnection();
        long id_vanzare = -1;
        try (PreparedStatement statement = con.prepareStatement("INSERT INTO vanzari (id_spectacol, data_vanzare)  values (?, ?) returning id_vanzare")){
            statement.setLong(1, spectacol.getId());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            try (var result = statement.executeQuery()){
                if(result.next()){
                    id_vanzare = result.getLong("id_vanzare");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (PreparedStatement statement = con.prepareStatement("insert into bilete (nr_loc, id_vanzare) values (?, ?)")){
            statement.setLong(2, id_vanzare);
            for (var loc : locuriAlese){
                statement.setInt(1, loc);
                statement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
