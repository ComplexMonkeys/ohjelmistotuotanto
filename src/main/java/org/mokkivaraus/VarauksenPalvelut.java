package org.mokkivaraus;

/**
 * Luokka varauksen palveluiden tallentamiselle tietokannasta omaksi oliokseen. 
 * Ottaa kenttiinsä arvot varauksen_palvelut-taulun ominaisuuksista.
 */
public class VarauksenPalvelut {

    /**
     * Varauksen ID
     */
    private int varausId;

    /**
     * Palvelun ID
     */
    private int palveluId;

    /**
     * Montako kertaa kyseiseen varaukseen palvelu ollaan liitetty
     */
    private int lkm;

    /**
     * Ensimmäinen parametrillinen alustaja.
     * 
     * @param varausId Varauksen ID
     * @param palveluId Palvelun ID
     * @param lkm Lukumäärä kuinka monta kertaa palvelu ollaan liitetty varaukselle.
     */
    public VarauksenPalvelut(int varausId, int palveluId, int lkm) {
        this.varausId = varausId;
        this.palveluId = palveluId;
        this.lkm = lkm;
    }

    /**
     * Toinen parametrillinen alustaja. Toisin kuin ensimmäinen, ottaa vain kaksi parametriä.
     * 
     * @param palveluId Palvelun ID
     * @param lkm Lukumäärä kuinka monta kertaa palvelu ollaan liitetty varaukselle.
     */
    public VarauksenPalvelut(int palveluId, int lkm) {
        this.palveluId = palveluId;
        this.lkm = lkm;
    }
    
    /** 
     * Hakumetodi varauksen ID:lle
     * 
     * @return int Varauksen ID
     */
    public int getVarausId() {
        return varausId;
    }
    
    /** 
     * Asetusmetodi varauksen ID:lle
     * 
     * @param varausId Varauksen ID
     */
    public void setVarausId(int varausId) {
        this.varausId = varausId;
    }
    
    /** 
     * Hakusmetodi palvelun ID:lle
     * 
     * @return int Palvelun ID
     */
    public int getPalveluId() {
        return palveluId;
    }
    
    /** 
     * Asetusmetodi palvelun ID:lle
     * 
     * @param palveluId Palvelun ID
     */
    public void setPalveluId(int palveluId) {
        this.palveluId = palveluId;
    }
    
    /** 
     * Hakumetodi lukumäärälle.
     * 
     * @return int Montako kertaa palvelu ollaan liitetty varaukseen.
     */
    public int getLkm() {
        return lkm;
    }
    
    /** 
     * Asetusmetodi lukumäärälle.
     * 
     * @param lkm Montako kertaa palvelu ollaan liitetty varaukseen.
     */
    public void setLkm(int lkm) {
        this.lkm = lkm;
    }

    
    /** 
     * Tostring-metodi olion tulostamista varten.
     * 
     * @return String Olion tiedot
     */
    @Override
    public String toString() {
        return "VarauksenPalvelut [lkm=" + lkm + ", palveluId=" + palveluId + ", varausId=" + varausId + "]";
    }
    

}
