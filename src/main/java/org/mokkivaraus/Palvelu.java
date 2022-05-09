package org.mokkivaraus;

import java.sql.*;

/**
 * Luokka palvelun tallentamiselle tietokannasta omaksi oliokseen. 
 * Ottaa kenttiinsä arvot palvelu-taulun ominaisuuksista.
 */
public class Palvelu {
    /**
     * Palvelun id, pääavain
     */
    private int palvelu_id;
    /**
     * Alueen id, millä palvelua tarjotaan
     */
    private int alue_id;
    /**
     * Palvelun nimi
     */
    private String nimi;
    /**
     * Palvelun tyyppi, kokonaisluku
     */
    private int tyyppi;
    /**
     * Palvelun kuvaus, max. 150 merkkiä
     */
    private String kuvaus;
    /**
     * Palvelun hinta
     */
    private double hinta;
    /**
     * ALV:n osuus hinnasta
     */
    private double alv;
    /**
     * Alueen nimi, jolla palvelua tarjotaan
     */
    private String alueNimi;

    /**
     * Parametritön alustaja
     */
    public Palvelu(){    
    }
    
    /**
     * Parametrillinen alustaja. Kutsuu setAlueNimi()-metodia, joka hakee alueen nimen tietokannasta.
     * 
     * @param palvelu_id Palvelun id
     * @param alue_id Alueen id
     * @param nimi Palvelun nimi
     * @param tyyppi Palvelun tyyppi
     * @param kuvaus Palvelun kuvaus
     * @param hinta Palvelun hinta
     * @param alv ALV:n osuus hinnasta
     */
    public Palvelu(int palvelu_id,int alue_id,String nimi,int tyyppi,String kuvaus,double hinta,double alv){
        this.palvelu_id = palvelu_id;
        this.alue_id = alue_id;
        this.nimi = nimi;
        this.tyyppi = tyyppi;
        this.kuvaus = kuvaus;
        this.hinta = hinta;
        this.alv = alv;
        try {
            setAlueNimi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Hakee Palvelun id:n
     * 
     * @return int Palvelun id
     */
    public int getPalvelu_id() {
        return palvelu_id;
    }
    
    /** 
     * Asettaa palvelun id:n
     * 
     * @param palvelu_id Palvelun yksilöidä id-numero
     */
    public void setPalvelu_id(int palvelu_id) {
        this.palvelu_id = palvelu_id;
    }
    
    /**
     * Hakee Alueen id:n
     * 
     * @return int Alueen id, jolla palvelua tarjotaan
     */
    public int getAlue_id() {
        return alue_id;
    }
    
    /** 
     * Asettaa alueen id:n
     * 
     * @param alue_id Sen alueen yksilöivä id-tunnus, jolla palvelua tarjotaan.
     */
    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }
    
    /** 
     * Hakee palvelun nimen
     * 
     * @return String Palvelun nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    /** 
     * Asettaa palvelun nimen
     * @param nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    /** 
     * Hakee palvelun tyypin
     * 
     * @return int Palvelun tyyppi
     */
    public int getTyyppi() {
        return tyyppi;
    }
    
    /** 
     * Asettaa palvelun tyypin
     * 
     * @param tyyppi Palvelun tyyppiä kuvaavaa kokonaisluku
     */
    public void setTyyppi(int tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    /** 
     * Hakee palvelun kuvauksen
     * 
     * @return String Palvelun kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }
    
    /** 
     * Asettaa palvelun kuvauksen 
     * 
     * @param kuvaus Palvelua kuvaava kuvaus kuvaavaan palvelua kuvauksen mukaisesti
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    
    /** 
     * Hakee hinnan
     * 
     * @return double Palvelun hinta
     */
    public double getHinta() {
        return hinta;
    }
    
    /**
     * Asettaa hinnan 
     * 
     * @param hinta Palvelun hinta
     */
    public void setHinta(double hinta) {
        this.hinta = hinta;
    }
    
    /** 
     * Hakee ALV:n osuuden hinnasta
     * 
     * @return double ALV:n osuus hinnasta
     */
    public double getAlv() {
        return alv;
    }
    
    /** 
     * Asettaa ALV:n osuuden hinnasta
     * Metodi veronkiertoa varten.
     * 
     * @param alv ALV:n osuus hinnasta
     */
    public void setAlv(double alv) {
        this.alv = alv;
    }

    
    /** 
     * Hakee alueen nimen jolla palvelua tarjotaan.
     * 
     * @return String Alueen nimi jolla palvelua tarjotaan
     */
    public String getAlueNimi(){
        return alueNimi;
    }

    
    /** 
     * Hakee alueen nimen alue_id:n perusteella tietokannasta ja asettaa sen muuttujaan.
     * 
     * @throws SQLException Tietokantaan ei saa yhteyttä. Vika löytyy tietokannan osoitteesta, käyttäjänimestä tai salasanasta.
     */
    public void setAlueNimi() throws SQLException{
        String nimi = "";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nimi FROM alue WHERE alue_id = '" + getAlue_id() + "';");
            while (rs.next()){
                nimi = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        this.alueNimi = nimi;
    }

    
    /** 
     * Tostring-metodi.
     * Jostakin syystä palauttaa vain nimen.
     * 
     * @return String merkkijono, joka sisältää palvelun nimen.
     */
    @Override
    public String toString() {
        return nimi;
    }

    
    /** 
     * Tulostaa palvelu-olion tiedot jäsennettyinä.
     * Vähän kuin tostring, mutta kenelläkään ei ole hajuakaan mikä ero on.
     * 
     * @return String merkkijono, joka sisältää olion tiedot.
     */
    public String tulostus(){
        return "Palvelun ID: " + getPalvelu_id() + "\n" +
        "Alue: " + getAlue_id() + " - " + getAlueNimi() + "\n" +
        "Palvelun nimi: " + getNimi() + "\n" +
        "Tyyppi: " + getTyyppi() + "\n" +
        "Kuvaus: " + getKuvaus() + "\n" +
        "Hinta: " + getHinta() + "\n" +
        "Alv: " + getAlv();
    }
}
