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
        System.out.println("In repo!");
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
