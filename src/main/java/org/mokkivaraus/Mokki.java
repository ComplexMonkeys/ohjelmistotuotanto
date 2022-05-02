package org.mokkivaraus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mokki {
    // mökin id
    private int mokki_id;
    // alueen id
    private int alue_id;
    // postinumero, huomioi merkkijono koska alkuperäisessä tietokannassa
    // tallenettuna viiden merkin jonona
    private String postinro;
    // mökin nimi
    private String mokkinimi;
    // katuosoite
    private String katuosoite;
    // hinta
    private double hinta;
    // kuvaus, max 150 merkkiä
    private String kuvaus;
    // henkilömäärä
    private int henkilomaara;
    // varustelu, max. 100 merkkiä
    private String varustelu;

    private String alueNimi;
    private ArrayList<Palvelu> palvelut;

    // lisätietoa kentistä saa määrittelydokumentista

    // Muuttujaton alustaja
    public Mokki() {
    }

    // Alustaja, joka asettaa jokaiselle kentälle muuttujan
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
            setPalvelut();
            setAlueNimi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMokki_id() {
        return mokki_id;
    }

    public void setMokki_id(int mokki_id) {
        this.mokki_id = mokki_id;
    }

    public int getAlue_id() {
        return alue_id;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    public String getPostinro() {
        return postinro;
    }

    public void setPostinro(String postinro) {
        this.postinro = postinro;
    }

    public String getMokkinimi() {
        return mokkinimi;
    }

    public void setMokkinimi(String mokkinimi) {
        this.mokkinimi = mokkinimi;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    public String getVarustelu() {
        return varustelu;
    }

    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    public String getAlueNimi(){
        return alueNimi;
    }

    public void setAlueNimi() throws SQLException{
        String nimi = "";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            ResultSet rd = stmt.executeQuery("SELECT nimi FROM alue WHERE alue_id = '" + this.alue_id + "';");
            while (rd.next()){
                nimi = rd.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.alueNimi = nimi;
    }

    public List<Palvelu> getPalvelut(){
        return palvelut;
    }

    public void setPalvelut() throws SQLException{
        ArrayList<Palvelu> lista = new ArrayList<>();
        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "employee", "password");
        try {
            Statement stmt = con.createStatement();
            // Määrittää SQL komennon ja lähettää sen tietokannalle.
            ResultSet rs = stmt.executeQuery("select * from palvelu");
            // Lisää kaikki taulukossa olevien alkioiden tiedot listaan.
            while (rs.next()) {
                Palvelu tempPalvelu = new Palvelu(rs.getInt(1),rs.getInt(2),rs.getString(3),
                rs.getInt(4),rs.getString(5),rs.getDouble(6),rs.getDouble(7));
                lista.add(tempPalvelu);
            }
            // Nappaa poikkeukset ja tulostaa ne.
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            // Yhteys tietokantaan suljetaan.
            con.close();
        }
        
        this.palvelut = lista;
    }

    @Override
    public String toString() {
        return "{" +
            " mokki_id='" + getMokki_id() + "'" +
            ", alue_id='" + getAlue_id() + "'" +
            ", postinro='" + getPostinro() + "'" +
            ", mokkinimi='" + getMokkinimi() + "'" +
            ", katuosoite='" + getKatuosoite() + "'" +
            ", hinta='" + getHinta() + "'" +
            ", kuvaus='" + getKuvaus() + "'" +
            ", henkilomaara='" + getHenkilomaara() + "'" +
            ", varustelu='" + getVarustelu() + "'" +
            ", alueNimi='" + getAlueNimi() + "'" +
            ", palvelut='" + getPalvelut() + "'" +
            "}";
    }

    
}