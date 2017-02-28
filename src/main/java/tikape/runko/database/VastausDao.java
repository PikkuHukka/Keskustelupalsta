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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        int vastaus_id = rs.getInt("vastaus_id");
        int avausviittaus = rs.getInt("avausviittaus"); 
        int alueviittaus = rs.getInt("alueviittaus");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp aikaleima = rs.getTimestamp("aikaleima");

        Vastaus v = new Vastaus(vastaus_id, avausviittaus, alueviittaus, sisalto, nimimerkki, aikaleima);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            int vastaus_id = rs.getInt("vastaus_id");
        int avausviittaus = rs.getInt("avausviittaus"); 
        int alueviittaus = rs.getInt("alueviittaus");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp aikaleima = rs.getTimestamp("aikaleima");       

            vastaukset.add(new Vastaus(vastaus_id, avausviittaus, alueviittaus, sisalto, nimimerkki, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public List<Vastaus> findAvaus(int avaus_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement
        ("SELECT * FROM Vastaus WHERE avausviittaus = ?");
        stmt.setObject(1, avaus_id);
        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            int vastaus_id = rs.getInt("vastaus_id");
        int avausviittaus = rs.getInt("avausviittaus"); 
        int alueviittaus = rs.getInt("alueviittaus");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        Timestamp aikaleima = rs.getTimestamp("aikaleima");       

            vastaukset.add(new Vastaus(vastaus_id, avausviittaus, alueviittaus, sisalto, nimimerkki, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public void add(Integer vastaus_id, String sisalto, String nimimerkki, Timestamp aikaleima, Integer alueviittaus, Integer avausviittaus) throws SQLException {

        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement
                ("INSERT INTO Vastaus "
                        + "(vastaus_id, sisalto, nimimerkki, aikaleima, alueviittaus, avausviittaus)"
                        + " VALUES (vastaus_id, sisalto, nimimerkki, aikaleima, alueviittaus, avausviittaus)");
            
            
            stmt.close();
        }

        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
