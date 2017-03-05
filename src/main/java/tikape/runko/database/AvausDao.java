/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Avaus;

/**
 *
 * @author oolli
 */
public class AvausDao implements Dao<Avaus, Integer> {

    private Database database;

    public AvausDao(Database database) {
        this.database = database;
    }

    /**
     *
     * @param key
     * @return
     * @throws SQLException
     */
    @Override
    public Avaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus WHERE avaus_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer avaus_id = rs.getInt("avaus_id");
        Integer alueviittaus = rs.getInt("alueviittaus");
        String otsikko = rs.getString("otsikko");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp aikaleima = rs.getTimestamp("aikaleima");

//(int avaus_id, int alueviittaus, String otsikko, String sisalto, String nimimerkki, Calendar aikaleima
        Avaus o = new Avaus(avaus_id, alueviittaus, otsikko, sisalto, nimimerkki, aikaleima);

        rs.close();
        stmt.close();
        connection.close();
        return o;
    }

    @Override

    public List<Avaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus ORDER BY aikaleima");

        ResultSet rs = stmt.executeQuery();
        List<Avaus> avaukset = new ArrayList<>();
        while (rs.next()) {

            Integer avaus_id = rs.getInt("avaus_id");
            Integer alueviittaus = rs.getInt("alueviittaus");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Timestamp aikaleima = rs.getTimestamp("aikaleima");

            avaukset.add(new Avaus(avaus_id, alueviittaus, otsikko, sisalto, nimimerkki, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    public List<Avaus> findAlue(int alue_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Avaus WHERE alueviittaus = " + alue_id + " ORDER BY aikaleima ASC");
//        stmt.setObject(1, alue_id);
        ResultSet rs = stmt.executeQuery();
        List<Avaus> avaukset = new ArrayList<>();
        while (rs.next()) {

            Integer avaus_id = rs.getInt("avaus_id");
            Integer alueviittaus = rs.getInt("alueviittaus");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            Timestamp aikaleima = rs.getTimestamp("aikaleima");

            avaukset.add(new Avaus(avaus_id, alueviittaus, otsikko, sisalto, nimimerkki, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        return avaukset;
    }

    public void lisaaAvaus(Avaus avaus) throws SQLException {
        int avaus_id = avaus.getAvaus_id();
        String otsikko = avaus.getOtsikko();
        String sisalto = avaus.getSisalto();
        int alueviittaus = avaus.getAlueviittaus();
        Timestamp aikaleima = avaus.getAikaleima();
        String nimimerkki = avaus.getNimimerkki();
        
        try (Connection connection = database.getConnection()) {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement
                                ("INSERT INTO Avaus(avaus_id, otsikko, sisalto, alueviittaus, aikaleima, nimimerkki)"
                                        + " VALUES(?,?,?,?,?,?)");
            
            
            stmt.setObject(1, avaus_id);
            stmt.setObject(2, otsikko);
            stmt.setObject(3, sisalto);
            stmt.setObject(4, alueviittaus);
            stmt.setObject(5, aikaleima);
            stmt.setObject(6, nimimerkki);
            stmt.executeUpdate();
            stmt.close();
            
            
            connection.commit();
        } catch(Exception e) {}
    }

    public int viestienLukumaara(int avaus_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT avaus_id, COUNT(vastaus.avausviittaus) AS lukumaara FROM Avaus, Vastaus WHERE avausviittaus = avaus_id AND avausviittaus = " + avaus_id);

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

    public String viimeisinViesti(int avaus_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE avausviittaus = " + avaus_id + " ORDER BY aikaleima ASC LIMIT 1");

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
