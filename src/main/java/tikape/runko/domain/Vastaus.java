/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Calendar;


public class Vastaus {

    private final int vastaus_id;
    private final int avausviittaus;    
    private final String sisalto;
    private final String nimimerkki;
    private final Calendar aikaleima;

    public Vastaus(int vastaus_id, int avausviittaus, String sisalto, String nimimerkki, Calendar aikaleima) {
        this.vastaus_id = vastaus_id;
        this.avausviittaus = avausviittaus;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;
        this.aikaleima = aikaleima;
    }

    public Calendar getAikaleima() {
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

    

}