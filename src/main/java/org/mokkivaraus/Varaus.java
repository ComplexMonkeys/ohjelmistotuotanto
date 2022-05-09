package org.mokkivaraus;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Luokka varauksen tietojen tallentamiselle tietokannasta omaksi oliokseen. 
 * Ottaa kenttiinsä arvot varaus-taulun ominaisuuksista.
 */
public class Varaus {

    /**
     * DateTimeFormatter muuttamaan päivämäärät MySQL:n hyväksymään muotoon
     */
    DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    /**
     * Varauksen ID
     */
    private int varausId;

    /**
     * Varauksen tehneen asiakkaan ID
     */
    private int asiakasId;

    /**
     * Varatun mökin ID
     */
    private int mokkiId;

    /**
     * Päivämäärä jolla varaus ollaan tehty
     */
    private String varattuPvm;

    /**
     * Päivämäärä jolloin varaus vahvistetaan vuokraukseksi
     */
    private String vahvistusPvm;

    /**
     * Varauksen aloituspäivämäärä
     */
    private String varattuAlku;

    /**
     * Varauksen päättymispäivämäärä
     */
    private String varattuLoppu;

    /**
     * Lista varaukseen vuokratuista palveluista
     */
    private ArrayList<Palvelu> palvelut;
    
    /**
     * Varatun mökin nimi
     */
    private String mokinNimi;

    

    /**
     * Parametrillinen muuttuja. Ajaa setPalvelut()- ja setMokinNimi()-metodit muuttujien arvojen asettamista varten.
     * 
     * @param varausId Varauksen ID
     * @param asiakasId Asiakkaan ID
     * @param mokkiId Mökin ID
     * @param varattuPvm Varauksen päivämäärä
     * @param vahvistusPvm Vahvistuksen päivämäärä
     * @param varattuAlku Varauksen aloituspäivämäärä
     * @param varattuLoppu Varauksen päätöspäivämäärä
     */
    public Varaus(int varausId, int asiakasId, int mokkiId, String varattuPvm, String vahvistusPvm, String varattuAlku,
            String varattuLoppu) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.mokkiId = mokkiId;
        this.varattuPvm = varattuPvm;
        this.vahvistusPvm = vahvistusPvm;
        this.varattuAlku = varattuAlku;
        this.varattuLoppu = varattuLoppu;
        try {
            setPalvelut();
            setMokinNimi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Hakumetodi varauksen ID:lle.
     * 
     * @return int VarausID
     */
    public int getVarausId() {
        return varausId;
    }

    
    /** 
     * Asetusmetodi varauksen ID:lle.
     * 
     * @param varausId Varauksen ID
     */
    public void setVarausId(int varausId) {
        this.varausId = varausId;
    }

    
    /** 
     * Hakumetodi asiakkaan ID:lle.
     * 
     * @return int AsiakasID
     */
    public int getAsiakasId() {
        return asiakasId;
    }

    
    /** 
     * Asetusmetodi asiakkaan ID:lle.
     * 
     * @param asiakasId Varauksen tehneen asiakkaan ID
     */
    public void setAsiakasId(int asiakasId) {
        this.asiakasId = asiakasId;
    }

    
    /** 
     * Hakumetodi mökin ID:lle.
     * 
     * @return int MökkiID
     */
    public int getMokkiId() {
        return mokkiId;
    }

    
    /** 
     * Asetusmetodi mökin ID:lle.
     * 
     * @param mokkiId Varatun Mökin ID
     */
    public void setMokkiId(int mokkiId) {
        this.mokkiId = mokkiId;
    }

    
    /** 
     * Hakumetodi varauksen luontipäivämäärälle.
     * 
     * @return String Varauksen luontipäivämäärä
     */
    public String getVarattuPvm() {
        return varattuPvm;
    }

    
    /** 
     * Asetusmetodi varauksen luontipäivämäärälle.
     * 
     * @param varattuPvm Varauksen luontipäivämäärä
     */
    public void setVarattuPvm(String varattuPvm) {
        this.varattuPvm = varattuPvm;
    }

    
    /** 
     * Hakumetodi vahvistuspäivämäärälle.
     * 
     * @return String Päivämäärä jolloin varaus vahvistetaan.
     */
    public String getVahvistusPvm() {
        return vahvistusPvm;
    }

    
    /** 
     * Asetusmetodi vahvistuspäivämäärälle.
     * 
     * @param vahvistusPvm Vahvistuksen päivämäärä
     */
    public void setVahvistusPvm(String vahvistusPvm) {
        this.vahvistusPvm = vahvistusPvm;
    }

    
    /** 
     * Hakumetodi varauksen aloituspäivämäärälle.
     * 
     * @return String Päivämäärä jolloin varaus alkaa
     */
    public String getVarattuAlku() {
        return varattuAlku;
    }

    
    /** 
     * Asetusmetodi varauksen aloituspäivämäärälle.
     * 
     * @param varattuAlku Varauksen aloituspäivämäärä
     */
    public void setVarattuAlku(String varattuAlku) {
        this.varattuAlku = varattuAlku;
    }

    
    /** 
     * Hakumetodi varauksen lopetuspäivämäärälle.
     * 
     * @return String Päivämäärä johon varaus päättyy
     */
    public String getVarattuLoppu() {
        return varattuLoppu;
    }

    
    /** 
     * Asetusmetodi varauksen lopetuspäivämäärälle.
     * 
     * @param varattuLoppu Varauksen päättävä päivämäärä
     */
    public void setVarattuLoppu(String varattuLoppu) {
        this.varattuLoppu = varattuLoppu;
    }

    
    /**
     * Hakumetodi varauksen palveluille. 
     * 
     * @return ArrayList<Palvelu> Lista varaukseen lisätyistä palveluista
     */
    public ArrayList<Palvelu> getPalvelut(){
        return palvelut;
    }

    
    /** 
     * Asetusmetodi varauksen palveluille.
     * Avaa yhteyden tietokantaan ja hakee varauksen_palvelut-listasta kaikki palvelun id:t, 
     * jotka varaukseen ollaan lisätty ja sen jälkeen kaikki palvelut jotka vastaavat näitä id:tä ja
     * lisäävät nämä palvelut listaan, mikä asetetaan palvelut-muuttuujaan.
     * 
     * @throws SQLException Tietokantayhteys on epäonnistunut. Vika on joko osoitteessa, käyttäjänimessä tai salasanassa.
     */
    public void setPalvelut() throws SQLException{
        ArrayList<Palvelu> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM palvelu WHERE palvelu_id IN (SELECT palvelu_id FROM varauksen_palvelut where varaus_id = '" + this.varausId +"');");
            while (rs.next()) {
                Palvelu tempvaraus = new Palvelu(rs.getInt(1), rs.getInt(2), rs.getString(3), 
                rs.getInt(4), rs.getString(5), rs.getDouble(6), rs.getDouble(7));
                lista.add(tempvaraus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        this.palvelut = lista;
    }

    
    /** 
     * Hakumetodi mökin nimelle.
     * 
     * @return String Mökin ID:tä vastaava nimi
     */
    public String getMokinNimi(){
        return mokinNimi;
    }

    
    /** 
     * Asetusmetodi mökin nimelle.
     * Muodostaa tietokantayhteyden ja hakee mokki-taulusta nimen joka vastaa mokkiId-muuttujaa ja asettaa sen mokinNimi-muuttujaan.
     * 
     * @throws SQLException Tietokantayhteys on epäonnistunut. Vika on joko osoitteessa, käyttäjänimessä tai salasanassa.
     */
    public void setMokinNimi() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        String nimi = "Nimeä ei saatavilla.";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT mokkinimi FROM mokki WHERE mokki_id = '" + getMokkiId() + "';");
            while (rs.next()){
                nimi = rs.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        this.mokinNimi = nimi;
    }

    
    /** 
     * Tostring-metodi. Luo oliosta helposti luettavan tulostuksen.
     * 
     * @return String Olion tiedot tekstijonona.
     */
    @Override
    public String toString() {
        ArrayList<Palvelu> temp = getPalvelut();
        String lista = "Varauksen palvelut: ";
        for (int i = 0; i < temp.size(); i++){
            String palvelu = temp.get(i).getPalvelu_id() + ": " + temp.get(i).getNimi() + ": " + temp.get(i).getHinta() + "\n";
            lista = lista + "\n" + palvelu;
        }
        return "Varauksen ID: " + getVarausId() + "\n" +
                "Mökin ID: " + getMokkiId() + "\n" +
                "Mökin nimi: " + getMokinNimi() + "\n" +  
                "Asiakkaan ID: " + getAsiakasId() + "\n" +
                "Varaus tehty: " + getVarattuPvm() + "\n" +
                "Varauksen vahvistus: " + getVahvistusPvm() + "\n" +
                "Vuokraus alkaa: " + getVarattuAlku() + "\n" +
                "Vuokraus päättyy:  " + getVarattuLoppu() + "\n" + lista;
    }

}