package org.mokkivaraus;
/**
 * Lasku luokka joka auto-generoituu varauksen tekemällä
 */
public class Lasku{
    
    /**
    * kokonaisluku lasku_id (lasku-tablen pääavain)
    */
    private int lasku_id;
    
    /**
     * kokonaisluku varaus_id 
     */
    private int varaus_id;
    
    /**
     * laskun kokonaishinta desimaalilukuina
     */
    private double summa;
    
    /**
     * hinnan sisältämä alvi desimaalilukuina
     */
    private double alv;
    
    /**
     * parametrillinen alustaja, luo laskuolion annetuilla parametreillä
     * @param lasku_id
     * @param varaus_id
     * @param summa
     * @param alv
     */
    public  Lasku(int lasku_id, int varaus_id, double summa, double alv){

        this.lasku_id = lasku_id;
        this.varaus_id = varaus_id;
        this.summa = summa;
        this.alv = alv;
    }
    
    /** metodi alvin osuuden hinnasta laskemiseen
     * @return double alvin osuus kokonaissummasta
     */
    public double laskeAlv(){
        return summa - (summa * 0.9);
    }

    
    /** get-metodi lasku_idn hakemiseen
     * @return int lasku_id
     */
    public int getLasku_id() {
        return this.lasku_id;
    }

    
    /** set-metodi lasku_id asettamiselle
     * @param lasku_id haluttu lasku_id kokonaislukuna
     */
    public void setLasku_id(int lasku_id) {
        this.lasku_id = lasku_id;
    }

    
    /** get-metodi varaus_id hakemiselle
     * @return int varaus_id
     */
    public int getVaraus_id() {
        return this.varaus_id;
    }

    
    /** set-metodi varaus_idn asettamiselle
     * @param varaus_id haluttu varaus_id kokonaislukuna
     */
    public void setVaraus_id(int varaus_id) {
        this.varaus_id = varaus_id;
    }

    
    /** get-metodi laskun yhteishinnan palautukselle
     * @return double summa
     */
    public double getSumma() {
        return this.summa;
    }

    
    /** set-metodi laskun yhteishinnan asettamiselle
     * @param summa haluttu summa desimaalilukuna (erota pisteillä)
     */
    public void setSumma(double summa) {
        this.summa = summa;
    }

    
    /** get-metodi alvin palautukselle
     * @return double
     */
    public double getAlv() {
        return this.alv;
    }

    
    /** set-metodi alvin asetukselle
     * @param alv haluttu alv desimaalilukuna (10.0 mökeille)
     */
    public void setAlv(double alv) {
        this.alv = alv;
    }

    
    /** toString-metodi olion tietojen tulostamiseen merkkijonona
     * @return String laskun tiedot 
     */
    @Override
    public String toString() {
        return
            "Laskun ID: " + getLasku_id() + "\n" +
            "Varauksen ID: " + getVaraus_id() + "\n" +
            "Summa: " + getSumma() + "\n" +
            "ALV: " + getAlv();
    }

}