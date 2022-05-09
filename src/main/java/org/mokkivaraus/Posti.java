package org.mokkivaraus;

/**
 * Luokka postinumeron tallentamiselle tietokannasta omaksi oliokseen. 
 * Ottaa kenttiinsä arvot posti-taulun ominaisuuksista.
 */
public class Posti {

    /**
     * Postinumero, huomaa merkkijono koska ominaisuus tallennetaan tietokantaan max. viiden merkin merkkijonona
     */
    private String postinro;

    /**
     * Postinumeroa vastaava toimipaikka
     */
    private String toimipaikka;

    /**
     * Parametritön alustaja
     */
    public Posti(){}

    /**
     * Parametrillinen alustaja
     * 
     * @param postinro Postinumero
     * @param toimipaikka Postinumero vastaava toimipaikka
     */
    public Posti(String postinro, String toimipaikka){
        this.postinro = postinro;
        this.toimipaikka = toimipaikka;
    }

    
    /** 
     * Asetusmetodi postinumerolle
     * 
     * @param postinro Postinumero
     */
    public void setPostinro(String postinro){
        this.postinro = postinro;
    }

    
    /** 
     * Asetusmetodi toimipaikan nimelle
     * 
     * @param toimipaikka Toimipaikan nimi
     */
    public void setToimipaikka(String toimipaikka){
        this.toimipaikka = toimipaikka;
    }

    
    /** 
     * Hakumetodi postinumerolle
     * 
     * @return String Postinumero
     */
    public String getPostinro(){
        return this.postinro;
    }

    
    /** 
     * Hakumetodi toimipaikalle
     * 
     * @return String Toimipaikka
     */
    public String getToimipaikka(){
        return this.toimipaikka;
    }
}
