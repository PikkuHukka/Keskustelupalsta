/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author langjimi
 */
public class Alue {
    
    private String nimi;
    private int alue_id;
    
    public Alue(int id, String nimi){
        
        this.nimi = nimi;
        this.alue_id = id;
        
    }

    public int getAlue_id() {
        return alue_id;
    }

    public String getNimi() {
        return nimi;
    }
    
    
}
