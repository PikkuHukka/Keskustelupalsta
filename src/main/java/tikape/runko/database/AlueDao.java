package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Opiskelija;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database d) {
        this.database = d;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("alue_id");
        String nimi = rs.getString("nimi");

        Alue a = new Alue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> l = new ArrayList();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            l.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return l;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
    }

}
