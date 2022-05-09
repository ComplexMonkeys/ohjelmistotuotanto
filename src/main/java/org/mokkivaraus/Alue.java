package org.mokkivaraus;

/**
 * Luokka alueen tietojen tallentamiselle omaksi oliokseen.
 * Ottaa kenttien arvoiksi tietokannan alue-taulun ominaisuudet.
 */
public class Alue{

    /**
     * Alueen ID
     */
    private int alue_id;

    /**
     * ID:tä vastaavan alueen nimi
     */
    private String nimi;

    /**
     * Asetusmetodi alueen ID:lle.
     * 
     * @param alue_id Alueen ID
     */
    public void setAlue_id(int alue_id){
        this.alue_id = alue_id;
    }

    /**
     * Hakumetodi alueen ID:lle.
     * @return Alueen ID kokonaislukuna
     */
    public int getAlue_id(){
        return alue_id;
    }

    /**
     * Asetusmetodi alueen nimelle.
     * @param nimi Alueen nimi
     */
    public void setNimi(String nimi){
        this.nimi = nimi;
    }

    /**
     * Hakumetodi alueen nimelle.
     * 
     * @return Alueen nimi merkkijonona
     */
    public String getNimi(){
        return nimi;
    }

    /**
     * Tostring-metodi olion tietojen tulostamista varten.
     * 
     * @return Olion tiedot merkkijonona
     */
    @Override
    public String toString(){
        return "Alue [alue_id =" + alue_id + ", nimi=" + nimi + "]";
    }

    /**
     * Parametritön alustaja.
     */
    public Alue(){

    }


    /**
     * Parametrillinen alustaja.
     * 
     * @param alue_id Alueen ID
     * @param nimi ID:tä vastaava alueen nimi
     */
    public Alue(int alue_id,String nimi){
    this.alue_id = alue_id;
    this.nimi = nimi;
    }


}