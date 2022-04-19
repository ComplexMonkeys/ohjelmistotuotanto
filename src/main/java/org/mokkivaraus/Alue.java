package org.mokkivaraus;

public class Alue{
    private int alue_id;
    private String nimi;


    public void setAlueID(int alue_id){
        this.alue_id = alue_id;
    }
    public int getAlueID(){
        return alue_id;
    }
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    public String getNimi(){
        return nimi;
    }
    public String toString(){
        return "Alue [alue_id =" + alue_id + ", nimi=" + nimi + "]";
    }

    public Alue(){

    }

// parametrillinen ALUSTAJA
    public Alue(int alue_id,String nimi){
    this.alue_id = alue_id;
    this.nimi = nimi;
    }


}