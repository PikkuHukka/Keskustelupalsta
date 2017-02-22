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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Avaus;
import tikape.runko.domain.Opiskelija;

/**
 *
 * @author oolli
 */
public class AvausDao implements Dao<Avaus, Integer> {

    private Database database;

    /**
     *
     * @param key
     * @return
     * @throws SQLException
     */
    @Override
    public Avaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Opiskelija WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer alueviittaus = rs.getInt("alueviittaus");
        String otsikko = rs.getString("nimi");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("nimimerkki");
        String aikaleima = rs.getString("aikaleima");
        

//(int avaus_id, int alueviittaus, String otsikko, String sisalto, String nimimerkki, Calendar aikaleima
        Avaus o = new Avaus(id, alueviittaus, otsikko, sisalto, nimimerkki, aikaleima);

        rs.close();
        stmt.close();
        connection.close();
        return o;
    }

    @Override

    public List<Avaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Opiskelija");

        ResultSet rs = stmt.executeQuery();
        List<Avaus> avaukset = new ArrayList<>();
        while (rs.next()) {

            Integer id = rs.getInt("id");
            Integer alueviittaus = rs.getInt("alueviittaus");
            String otsikko = rs.getString("nimi");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            String aikaleima = rs.getString("aikaleima");

            avaukset.add(new Avaus(id, alueviittaus, otsikko, sisalto, nimimerkki, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
