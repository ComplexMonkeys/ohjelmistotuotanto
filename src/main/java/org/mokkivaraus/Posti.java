package org.mokkivaraus;

public class Posti {
    private String postinro;
    private String toimipaikka;

    public Posti(){}
    public Posti(String postinro, String toimipaikka){
        this.postinro = postinro;
        this.toimipaikka = toimipaikka;
    }

    public void setPostinro(String postinro){
        this.postinro = postinro;
    }

    public void setToimipaikka(String toimipaikka){
        this.toimipaikka = toimipaikka;
    }

    public String getPostinro(){
        return this.postinro;
    }

    public String getToimipaikka(){
        return this.toimipaikka;
    }
}
