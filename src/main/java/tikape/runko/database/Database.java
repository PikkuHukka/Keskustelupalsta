package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;

    }

    public Connection getConnection() throws SQLException {

        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (alue_id integer PRIMARY KEY, aihe varchar(50));");
        lista.add("CREATE TABLE Avaus (avaus_id integer PRIMARY KEY,otsikko varchar(50),sisalto varchar(500),alueviittaus integer, aikaleima DATETIME, nimimerkki varchar(50), FOREIGN KEY(alueviittaus) REFERENCES Alue(alue_id));");
        lista.add("CREATE TABLE Vastaus (vastaus_id integer PRIMARY KEY, sisalto varchar(500), nimimerkki varchar(50), aikaleima DATETIME,avausviittaus integer, alueviittaus integer, FOREIGN KEY(avausviittaus) REFERENCES Avaus(avaus_id), FOREIGN KEY(alueviittaus) REFERENCES Alue(alue_id));");
        return lista;
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Alue");
        lista.add("DROP TABLE Avaus");
        lista.add("DROP TABLE Vastaus"); // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue (alue_id SERIAL PRIMARY KEY, aihe varchar(50));");
        lista.add("CREATE TABLE Avaus (avaus_id SERIAL PRIMARY KEY,otsikko varchar(50),sisalto varchar(500),alueviittaus integer, aikaleima DATETIME, nimimerkki varchar(50), FOREIGN KEY(alueviittaus) REFERENCES Alue(alue_id));");
        lista.add("CREATE TABLE Vastaus (vastaus_id SERIAL PRIMARY KEY, sisalto varchar(500), nimimerkki varchar(50), aikaleima DATETIME,avausviittaus integer, alueviittaus integer, FOREIGN KEY(avausviittaus) REFERENCES Avaus(avaus_id), FOREIGN KEY(alueviittaus) REFERENCES Alue(alue_id));");

        return lista;
    }
}
