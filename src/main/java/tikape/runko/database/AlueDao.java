package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Opiskelija;
import tikape.runko.domain.Vastaus;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database d) {
        this.database = d;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE alue_id = ?");
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue ORDER BY nimi");

        ResultSet rs = stmt.executeQuery();
        List<Alue> l = new ArrayList();

        while (rs.next()) {
            Integer id = rs.getInt("alue_id");
            String nimi = rs.getString("nimi");

            l.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return l;
    }

    public int viestienLukumaara(int alue_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT alue_id, COUNT(vastaus.alueviittaus) AS lukumaara FROM Alue, Vastaus WHERE alueviittaus = alue_id AND alueviittaus = " + alue_id);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return 0;
        }

        int lukumaara = rs.getInt("lukumaara");

        rs.close();
        stmt.close();
        connection.close();

        return lukumaara;
    }

    public String viimeisinViesti(int alue_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE alueviittaus = " + alue_id + " ORDER BY aikaleima DESC LIMIT 1");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return avauksenLeima(alue_id) + "";
        }

        String aikaleima = rs.getTimestamp("aikaleima") + "";

        rs.close();
        stmt.close();
        connection.close();

        return aikaleima;
    }

    public String avauksenLeima(int alue_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus WHERE alueviittaus = " + alue_id + " ORDER BY aikaleima DESC LIMIT 1");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String aikaleima = rs.getTimestamp("aikaleima") + "";

        rs.close();
        stmt.close();
        connection.close();

        return aikaleima;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        String sql = "DELETE FROM Alue WHERE alue_id = " + key;

        updateDatabase(sql);
    }

    public void lisaaAlue(Alue alue) throws SQLException {

        String sql = "INSERT INTO Alue(alue_id, nimi) VALUES( " + alue.getAlue_id() + ", '" + alue.getNimi() + "')";

        updateDatabase(sql);
    }

    public void updateDatabase(String sql) throws SQLException {
        Connection connection = database.getConnection();
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        connection.commit();
        connection.close();
    }

    public int onkoAvausta(int alue_id) throws SQLException {

        if (avauksenLeima(alue_id) == null) {
            return 0;
        }
        return 1;
    }

}
