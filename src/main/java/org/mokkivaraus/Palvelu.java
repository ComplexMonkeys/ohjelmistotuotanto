package org.mokkivaraus;

public class Palvelu {
    private int palvelu_id;
    private int alue_id;
    private String nimi;
    private int tyyppi;
    private String kuvaus;
    private double hinta;
    private double alv;

    // PA = ((P)ARAMETRITÖN (A)LUSTAJA)ASKA
    public Palvelu(){    
    }
    
    public Palvelu(int palvelu_id,int alue_id,String nimi,int tyyppi,String kuvaus,double hinta,double alv){
        this.palvelu_id = palvelu_id;
        this.alue_id = alue_id;
        this.nimi = nimi;
        this.tyyppi = tyyppi;
        this.kuvaus = kuvaus;
        this.hinta = hinta;
        this.alv = alv;
    }

    public int getPalvelu_id() {
        return palvelu_id;
    }
    public void setPalvelu_id(int palvelu_id) {
        this.palvelu_id = palvelu_id;
    }
    public int getAlue_id() {
        return alue_id;
    }
    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }
    public String getNimi() {
        return nimi;
    }
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public int getTyyppi() {
        return tyyppi;
    }
    public void setTyyppi(int tyyppi) {
        this.tyyppi = tyyppi;
    }
    public String getKuvaus() {
        return kuvaus;
    }
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    public double getHinta() {
        return hinta;
    }
    public void setHinta(double hinta) {
        this.hinta = hinta;
    }
    public double getAlv() {
        return alv;
    }
    public void setAlv(double alv) {
        this.alv = alv;
    } 
}
