/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Calendar;

/**
 *
 * @author oolli
 */
public class Avaus {

    private int avaus_id;
    private int alueviittaus;
    private String otsikko;
    private String sisalto;
    private String nimimerkki;
    private String aikaleima;

    public Avaus(int avaus_id, int alueviittaus, String otsikko, String sisalto, String nimimerkki, String aikaleima) {
        this.avaus_id = avaus_id;
        this.alueviittaus = alueviittaus;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.otsikko = otsikko;
        this.aikaleima = aikaleima;
    }

    public int getAvaus_id() {
        return this.avaus_id;
    }

    public int getAlueviittaus() {
        return this.avaus_id;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }

    public String getSisalto() {
        return this.otsikko;
    }

    public String getAikaleima() {
        return this.aikaleima;
    }
    

}