package org.mokkivaraus;

import java.sql.*;
import java.util.ArrayList;

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
    // mökin alueen nimi merkkijonona
    private String alueNimi;

    private ArrayList<Varaus> varaukset;

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
            setAlueNimi();
            setVaraukset();
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

    public ArrayList<Varaus> getVaraukset(){
        return varaukset;
    }

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