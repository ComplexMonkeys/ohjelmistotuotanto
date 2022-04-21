package org.mokkivaraus;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Varaus {
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter mysqlFormat = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS");

    private int varausId;
    private int asiakasId;
    private int mokkiId;
    private String varattuPvm;
    private String vahvistusPvm;
    private String varattuAlku;
    private String varattuLoppu;

    public Varaus(int varausId, int asiakasId, int mokkiId, String vahvistusPvm, String varattuAlku,
            String varattuLoppu) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.mokkiId = mokkiId;
        this.varattuPvm = dateTime.format(mysqlFormat);
        this.vahvistusPvm = vahvistusPvm;
        this.varattuAlku = varattuAlku;
        this.varattuLoppu = varattuLoppu;
    }


    public static void main(String[] args) {
        Varaus varaus = new Varaus(1, 1, 1, "1212-12-12 12:12:12", "1212-12-12 12:12:12", "1212-12-12 12:12:12");
        System.out.println(varaus);
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

    @Override
    public String toString() {
        return "Varaus [asiakasId=" + asiakasId + ", mokkiId=" + mokkiId + ", vahvistusPvm=" + vahvistusPvm
                + ", varattuAlku=" + varattuAlku + ", varattuLoppu=" + varattuLoppu + ", varattuPvm=" + varattuPvm
                + ", varausId=" + varausId + "]";
    }

}