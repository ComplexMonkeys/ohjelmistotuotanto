package org.mokkivaraus;

public class Lasku{

    private int lasku_id;
    
    private int varaus_id;

    private double summa;

    private double alv;

    public  Lasku(int lasku_id, int varaus_id, double summa, double alv){

        this.lasku_id = lasku_id;
        this.varaus_id = varaus_id;
        this.summa = summa;
        this.alv = alv;
    }


    public int getLasku_id() {
        return this.lasku_id;
    }

    public void setLasku_id(int lasku_id) {
        this.lasku_id = lasku_id;
    }

    public int getVaraus_id() {
        return this.varaus_id;
    }

    public void setVaraus_id(int varaus_id) {
        this.varaus_id = varaus_id;
    }

    public double getSumma() {
        return this.summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public double getAlv() {
        return this.alv;
    }

    public void setAlv(double alv) {
        this.alv = alv;
    }

    @Override
    public String toString() {
        return "{" +
            " lasku_id='" + getLasku_id() + "'" +
            ", varaus_id='" + getVaraus_id() + "'" +
            ", summa='" + getSumma() + "'" +
            ", alv='" + getAlv() + "'" +
            "}";
    }

}