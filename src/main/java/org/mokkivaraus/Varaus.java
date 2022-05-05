package org.mokkivaraus;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Varaus {
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    LocalDateTime dateTimeEnd = LocalDateTime.now().plusDays(7);

    private int varausId;
    private int asiakasId;
    private int mokkiId;
    private String varattuPvm;
    private String vahvistusPvm;
    private String varattuAlku;
    private String varattuLoppu;
    private ArrayList<Palvelu> palvelut;

    

    public Varaus(int varausId, int asiakasId, int mokkiId, String varattuPvm, String vahvistusPvm, String varattuAlku,
            String varattuLoppu) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.mokkiId = mokkiId;
        this.varattuPvm = varattuPvm;
        this.vahvistusPvm = vahvistusPvm;
        this.varattuAlku = varattuAlku;
        this.varattuLoppu = varattuLoppu;
    }

    public int getVarausId() {
        return varausId;
    }

    public void setVarausId(int varausId) {
        this.varausId = varausId;
    }

    public int getAsiakasId() {
        return asiakasId;
    }

    public void setAsiakasId(int asiakasId) {
        this.asiakasId = asiakasId;
    }

    public int getMokkiId() {
        return mokkiId;
    }

    public void setMokkiId(int mokkiId) {
        this.mokkiId = mokkiId;
    }

    public String getVarattuPvm() {
        return varattuPvm;
    }

    public void setVarattuPvm(String varattuPvm) {
        this.varattuPvm = varattuPvm;
    }

    public String getVahvistusPvm() {
        return vahvistusPvm;
    }

    public void setVahvistusPvm(String vahvistusPvm) {
        this.vahvistusPvm = vahvistusPvm;
    }

    public String getVarattuAlku() {
        return varattuAlku;
    }

    public void setVarattuAlku(String varattuAlku) {
        this.varattuAlku = varattuAlku;
    }

    public String getVarattuLoppu() {
        return varattuLoppu;
    }

    public void setVarattuLoppu(String varattuLoppu) {
        this.varattuLoppu = varattuLoppu;
    }

    public void getPalvelut(){}

    @Override
    public String toString() {
        return "Varauksen ID: " + getVarausId() + "\n" +
                "Mökin ID: " + getMokkiId() + "\n" + 
                "Asiakkan ID: " + getAsiakasId() + "\n" +
                "Varaus tehty: " + getVarattuPvm() + "\n" +
                "Varauksen vahvistus: " + getVahvistusPvm() + "\n" +
                "Vuokraus alkaa: " + getVarattuAlku() + "\n" +
                "Vuokraus päättyy:  " + getVarattuLoppu();
    }

}