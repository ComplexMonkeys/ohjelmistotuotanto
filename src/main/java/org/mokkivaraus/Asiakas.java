package org.mokkivaraus;

/**
 * Luokka asiakkaan tietojen olioon tallettamiseen tietokannasta.
 * Ottaa muuttujikseen tietokannan asiakas-taulun vastaavat ominaisuudet.
 */
public class Asiakas {

    /**
     * Asiakkaan ID
     */
    private int asiakas_id;

    /**
     * Asiakkaan postinumero
     */
    private String postinro;

    /**
     * Asiakkaan etunimi
     */
    private String etunimi;

    /**
     * Asiakkaan sukunimi
     */
    private String sukunimi;

    /**
     * Asiakkaan lähiosoite
     */
    private String lahiosoite;

    /**
     * Asiakkaan sähköpostiosoite
     */
    private String email;

    /**
     * Asiakkaan puhelinnumero
     */
    private String puhelinnro;

    /**
     * Asiakkaan koko nimi merkkijonona, muodostuu etunimi + sukunimi
     */
    private String nimi;

    /**
     * Parametritön alustaja
     */
    public Asiakas() {
    }

    /**
     * Parametrillinen alustaja. 
     * Asettaa asiakkaan nimen yhdistämällä etunimen ja sukunimen yhdeksi merkkijonokseen.
     * 
     * @param asiakas_id Asiakkaan ID
     * @param postinro Asiakkaan postinumero
     * @param etunimi Asiakkaan etunimi
     * @param sukunimi Asiakkaan sukunimi
     * @param lahiosoite Asiakkaan osoite
     * @param email Asiakkaan sähköpostiosoite
     * @param puhelinnro Asiakkaan puhelinnumero
     */
    public Asiakas(int asiakas_id, String postinro, String etunimi, String sukunimi,
            String lahiosoite, String email, String puhelinnro) {
        this.asiakas_id = asiakas_id;
        this.postinro = postinro;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.lahiosoite = lahiosoite;
        this.email = email;
        this.puhelinnro = puhelinnro;
        this.nimi = etunimi + " " + sukunimi;
    }

    /**
     * Hakumetodi asiakkaan ID:lle.
     * 
     * @return int Asiakkaan ID
     */
    public int getAsiakas_id() {
        return asiakas_id;
    }

    /**
     * Asetusmetodi asiakkaan ID:lle.
     * 
     * @param asiakas_id Asiakkaan ID
     */
    public void setAsiakas_id(int asiakas_id) {
        this.asiakas_id = asiakas_id;
    }

    /**
     * Hakumetodi postinumerolle.
     * 
     * @return String Asiakkaan postinumero
     */
    public String getPostinro() {
        return postinro;
    }

    /**
     * Asetusmetodi postinumerolle.
     * 
     * @param postinro Asiakkaan postinumero
     */
    public void setPostinro(String postinro) {
        this.postinro = postinro;
    }

    /**
     * Hakumetodi asiakkaan etunimelle.
     * 
     * @return String Asiakkaan etunimi
     */
    public String getEtunimi() {
        return etunimi;
    }

    /**
     * Asetusmetodi asiakkaan etunimelle.
     * 
     * @param etunimi Asiakkaan etunimi
     */
    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    /**
     * Hakumetodi asiakkaan sukunimelle.
     * 
     * @return String Asiakkaan sukunimi
     */
    public String getSukunimi() {
        return sukunimi;
    }

    /**
     * Asetusmetodi asiakkaan sukunimelle.
     * 
     * @param sukunimi Asiakkaan sukunimi
     */
    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    /**
     * Hakumetodi asiakkaan osoitteelle.
     * 
     * @return String Asiakkaan posti-/laskutusosoite
     */
    public String getLahiosoite() {
        return lahiosoite;
    }

    /**
     * Asetusmetodi asiakkaan osoitteelle.
     * 
     * @param lahiosoite Asiakkaan osoite
     */
    public void setLahiosoite(String lahiosoite) {
        this.lahiosoite = lahiosoite;
    }

    /**
     * Hakumetodi asiakkaan sähköpostiosoitteelle.
     * 
     * @return String Asiakkaan sähköpostiosoite
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asetusmetodi asiakkaan sähköpostiosoitteelle.
     * 
     * @param email Asiakkaan sähköpostiosoite
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Hakumetodi asiakkaan puhelinnumerolle.
     * 
     * @return String Asiakkaan puhelinnumero
     */
    public String getPuhelinnro() {
        return puhelinnro;
    }

    /**
     * Asetusmetodi asiakkaan puhelinnumerolle.
     * 
     * @param puhelinnro Asiakkaan puhelinnumero
     */
    public void setPuhelinnro(String puhelinnro) {
        this.puhelinnro = puhelinnro;
    }

    /**
     * Hakumetodi asiakkaan nimelle.
     * 
     * @return String Asiakkaan nimi muodossa Etunimi + sukunimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Tostring-metodi olion tietojen tulostamiseen.
     * @return String Olion tiedot listattuna merkkijonona
     */
    @Override
    public String toString() {
        return "Asiakkaan ID: " + getAsiakas_id() + "\n" +
                "Nimi: " + getNimi() + "\n" +
                "Lähiosoite: " + getLahiosoite() + "\n" +
                "Postinumero: " + getPostinro() + "\n" +
                "Sähköpostiosoite: " + getEmail() + "\n" +
                "Puh. numero: " + getPuhelinnro();
    }
}