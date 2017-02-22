/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author langjimi
 */
public class Alue {
    
    private String nimi;
    private int alue_id;
    private int lukumaara;
    private String viimeisin;
    
    public Alue(int id, String nimi){
        
        this.nimi = nimi;
        this.alue_id = id;
        this.lukumaara = 0;
        this.viimeisin = null;
        
    }

    public int getAlue_id() {
        return alue_id;
    }

    public String getNimi() {
        return nimi;
    }

    public int getLukumaara() {
        return lukumaara;
    }

    public void setLukumaara(int lukumaara) {
        this.lukumaara = lukumaara;
    }

    public String getViimeisin() {
        return viimeisin;
    }

    public void setViimeisin(String viimeisin) {
        
        if(viimeisin.equals("null")){
            this.viimeisin = "null";
            return;
        }
        
        String lyhennetty = "";
        
        for (int i = 0; i < 16; i++) {
            lyhennetty += viimeisin.charAt(i);
        }
        
        this.viimeisin = lyhennetty;
    }

  
    
}
