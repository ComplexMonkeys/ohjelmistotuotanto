package org.mokkivaraus;

import java.sql.*;
import java.util.ArrayList;

/**
 *  Mokki-luokka tallettaa tietokannasta jokaisen mökin omaksi oliokseen, tallentaen sen ominaisuudet omiin kenttiinsä. 
 *  Näiden kenttien lisäksi olio hakee mökkiin tehdyt varaukset ja alueen nimen, jolla mökki sijaitsee.
 */
public class Mokki {

    /**
     * Mökin id
     */
    private int mokki_id;

    /**
     * Sen alueen id, jolla mökki sijaitsee
     */
    private int alue_id;

    /**
     * Postinumero, huomioi merkkijono koska alkuperäisessä tietokannassa tallenettuna viiden merkin jonona
     */
    private String postinro;

    /**
     * Mökin nimi
     */
    private String mokkinimi;

    /**
     * Mökin osoite
     */
    private String katuosoite;

    /**
     * Mökin hinta
     */
    private double hinta;

    /**
     * Mökin kuvaus, max. 150 merkkiä
     */
    private String kuvaus;

    /**
     * Mökin suurin sallittu määrä majoittujia
     */
    private int henkilomaara;

    /**
     * Listaus mökin varustelusta, max. 100 merkkiä
     */
    private String varustelu;

    /**
     * Alueen nimi jolla mökki on
     */
    private String alueNimi;

    /**
     * Lista mökkiin tehdyistä varauksista.
     */
    private ArrayList<Varaus> varaukset;

    // lisätietoa kentistä saa määrittelydokumentista

    /**
     * Parametritön alustaja.
     */
    public Mokki() {
    }

    /**
     * Parametrillinen alustaja. Kutsuu setAlueNimi()- ja setVaraukset()-metodeja hakeakseen kenttien tiedot tietokannasta.
     * 
     * @param mokki_id Mökin id
     * @param alue_id Alueen id
     * @param postinro Postinumero
     * @param mokkinimi Mökin nimi
     * @param katuosoite Mökin osoite
     * @param hinta Mökin hinta
     * @param kuvaus Mökin kuvaus
     * @param henkilomaara Max. henkilömäärä
     * @param varustelu Varustelun listaus
     */
    public Mokki(int mokki_id, int alue_id, String postinro, String mokkinimi, String katuosoite, double hinta,
            String kuvaus, int henkilomaara, String varustelu) {
        this.mokki_id = mokki_id;
        this.alue_id = alue_id;
        this.postinro = postinro;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.hinta = hinta;
        this.kuvaus = kuvaus;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
        
        try {
            setAlueNimi();
            setVaraukset();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Hakee mökin ID:n.
     * 
     * @return int mokki_id Mökin ID-numero, "mokki"-taulun pääavain tietokannassa.
     */
    public int getMokki_id() {
        return mokki_id;
    }

    
    /** 
     * Asettaa mökin ID:n.
     * 
     * @param mokki_id Mökin ID-numero, "mokki"-taulun pääavain tietokannassa.
     */
    public void setMokki_id(int mokki_id) {
        this.mokki_id = mokki_id;
    }

    
    /** 
     * Hakee mökin alueen ID:n.
     * 
     * @return int alue_id Alueen, jolla mökki sijaitsee, ID-numero.
     */
    public int getAlue_id() {
        return alue_id;
    }

    
    /** 
     * Asettaa mökin alueen ID:n.
     * 
     * @param alue_id Alueen, jolla mökki sijaitsee, ID-numero.
     */
    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    
    /** 
     * Hakee mökin postinumeron.
     * 
     * @return String postinro Postinumero.
     */
    public String getPostinro() {
        return postinro;
    }

    
    /** 
     * Asettaa mökin postinumeron.
     * 
     * @param postinro Postinumero.
     */
    public void setPostinro(String postinro) {
        this.postinro = postinro;
    }

    
    /** 
     * Hakee mökin nimen.
     * 
     * @return String mokkiNimi Mökin nimi.
     */
    public String getMokkinimi() {
        return mokkinimi;
    }

    
    /** 
     * Asettaa mökin nimen.
     * 
     * @param mokkinimi Mökin nimi.
     */
    public void setMokkinimi(String mokkinimi) {
        this.mokkinimi = mokkinimi;
    }

    
    /** 
     * Hakee mökin osoitteen.
     * 
     * @return String katuosoite Mökin osoite.
     */
    public String getKatuosoite() {
        return katuosoite;
    }

    
    /** 
     * Asettaa mökin osoitteen.
     * 
     * @param katuosoite Mökin osoite
     */
    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    
    /** 
     * Hakee mökin hinnan.
     * 
     * @return double hinta Mökin vuokrauksen hinta.
     */
    public double getHinta() {
        return hinta;
    }

    
    /** 
     * Asettaa mökin hinnan.
     * 
     * @param hinta Mökin vuokrauksen hinta.
     */
    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    
    /** 
     * Hakee kuvauksen mökistä.
     * 
     * @return String kuvaus Kuvaus Mökistä.
     */
    public String getKuvaus() {
        return kuvaus;
    }

    
    /** 
     * Asettaa kuvauksen mökistä.
     * 
     * @param kuvaus Kuvaus Mökistä.
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    
    /** 
     * Hakee henkilömäärän.
     * 
     * @return int henkilomaara Maksimimäärä henkilötä mitä mökissä voi majoittaa.
     */
    public int getHenkilomaara() {
        return henkilomaara;
    }

    
    /** 
     * Asettaa henkilömäärän.
     * 
     * @param henkilomaara Maksimimäärä henkilötä mitä mökissä voi majoittaa.
     */
    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    
    /** 
     * Hakee mökin varustelun.
     * 
     * @return String varustelu Listaus mökin varustelusta.
     */
    public String getVarustelu() {
        return varustelu;
    }

    
    /** 
     * Asettaa mökin varustelun
     * 
     * @param varustelu Listaus mökin varustelusta.
     */
    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    
    /** 
     * Hakee mökin alueen nimen.
     * 
     * @return String alueNimi Alueen, jolla mökki sijaitsee, nimi.
     */
    public String getAlueNimi(){
        return alueNimi;
    }

    
    /** 
     *  Hakee alue-taulusta alue_id:n perusteella mökin alueen nimen ja tallettaa sen muuttujaan. Ajetaan parametrillisessä muuttujassa olionluonnin yhteydessä.
     * 
     * @throws SQLException Yhteyttä relaatiotietokantaan ei saada muodostettua. Tarkasta tietokannan osoite, käyttäjänimi ja salasana.
     */
    public void setAlueNimi() throws SQLException{
        String nimi = "";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nimi FROM alue WHERE alue_id = '" + this.alue_id + "';");
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
     * Palauttaa listan mökkiin tehdyistä tulevista varauksista.
     * 
     * @return ArrayList<Varaus> varaukset Lista varauksista.
     */
    public ArrayList<Varaus> getVaraukset(){
        return varaukset;
    }

    
    /** 
     * Hakee tietokannan varaus-taulusta listan kaikista tulevista varauksista jotka ollaan tehty mökin id:llä.
     * Tulevat varaukset ovat kaikki ne joiden lopetuspäivämäärä on suurempi kuin MySQL:n curdate()-funktion palautus.
     * 
     * @throws SQLException Tietokantaan ei saa yhteyttä. Tarkista tietokannan osoite, käyttäjänimi ja salasana.
     */
    public void setVaraukset() throws SQLException{
        ArrayList<Varaus> lista = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM varaus WHERE mokki_mokki_id = '" + this.mokki_id + "' AND varattu_loppupvm >= curdate()");
            while (rs.next()) {
                Varaus tempalue = new Varaus(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                lista.add(tempalue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        this.varaukset = lista;
    }

    
    /** 
     * Tostring - metodi. Palauttaa mökin tiedot, jokainen kenttä omalla rivillään. 
     * Käy varauksien listan läpi ja luo niistä varauslistan, jonka se asettaa mökin tietojen loppuun.
     * 
     * @return String Mökin tiedot.
     */
    @Override
    public String toString() {
        ArrayList<Varaus> tempvaraukset = getVaraukset();
        String lista = "Varaukset: \n";
        for (int i = 0; i < tempvaraukset.size(); i++){
            String varaus = tempvaraukset.get(i).getVarausId() + ": " + tempvaraukset.get(i).getVarattuAlku() + " - " + tempvaraukset.get(i).getVarattuLoppu() + "\n";
            lista = lista + varaus;
        }

        return
            "Mökin ID: " + getMokki_id() + "\n" +
            "Alueen ID: " + getAlue_id() + "\n" +
            "Alueen Nimi: " + getAlueNimi() + "\n" +
            "Postinumero: " + getPostinro() + "\n" +
            "Mökin Nimi: " + getMokkinimi() + "\n" +
            "Osoite: " + getKatuosoite() + "\n" +
            "Hinta: " + getHinta() + "\n" +
            "Kuvaus: " + getKuvaus() + "\n" +
            "Max. Henkilömäärä: " + getHenkilomaara() + "\n" +
            "Varustelu: " + getVarustelu() + "\n" + lista;
    }

}