/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;
import java.util.Calendar;


public class Vastaus {

    private final int vastaus_id;
    private final int avausviittaus;   
    private final int alueviittaus;
    private final String sisalto;
    private final String nimimerkki;
    private final Timestamp aikaleima;

    public Vastaus(int vastaus_id, int avausviittaus, int alueviittaus, String sisalto, String nimimerkki, Timestamp aikaleima) {
        this.vastaus_id = vastaus_id;
        this.avausviittaus = avausviittaus;
        this.alueviittaus = alueviittaus;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;
        this.aikaleima = aikaleima;
    }

    public Timestamp getAikaleima() {
        return aikaleima;
    }

    public int getAvausviittaus() {
        return avausviittaus;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getSisalto() {
        return sisalto;
    }

    public int getVastaus_id() {
        return vastaus_id;
    }

    public int getAlueviittaus() {
        return alueviittaus;
    }

    

}