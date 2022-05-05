package org.mokkivaraus;

public class Asiakas {

private int asiakas_id;

private String postinro;

private String etunimi;

private String sukunimi;

private String lahiosoite;

private String email;

private String puhelinnro;

private String nimi;


//parametriton alustaja
public Asiakas(){
}
//parametrillinen alustaja
public Asiakas(int asiakas_id,String postinro,String etunimi,String sukunimi,
String lahiosoite, String email, String puhelinnro){
this.asiakas_id = asiakas_id;
this.postinro = postinro;
this.etunimi = etunimi;
this.sukunimi = sukunimi;
this.lahiosoite = lahiosoite;
this.email = email;
this.puhelinnro = puhelinnro;
this.nimi = etunimi + " " + sukunimi;
}
public int getAsiakas_id() {
    return asiakas_id;
}
public void setAsiakas_id(int asiakas_id) {
    this.asiakas_id = asiakas_id;
}
public String getPostinro() {
    return postinro;
}
public void setPostinro(String postinro) {
    this.postinro = postinro;
}
public String getEtunimi() {
    return etunimi;
}
public void setEtunimi(String etunimi) {
    this.etunimi = etunimi;
}
public String getSukunimi() {
    return sukunimi;
}
public void setSukunimi(String sukunimi) {
    this.sukunimi = sukunimi;
}
public String getLahiosoite() {
    return lahiosoite;
}
public void setLahiosoite(String lahiosoite) {
    this.lahiosoite = lahiosoite;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPuhelinnro() {
    return puhelinnro;
}
public void setPuhelinnro(String puhelinnro) {
    this.puhelinnro = puhelinnro;
}
public String getNimi(){
    return nimi;
}
@Override
public String toString(){
    return "Asiakkaan ID: " + getAsiakas_id() + "\n" +
    "Nimi: " + getNimi() + "\n" +
    "Lähiosoite: " + getLahiosoite() + "\n" +
    "Postinumero: " + getPostinro() + "\n" +
    "Sähköpostiosoite: " + getEmail() + "\n" +
    "Puh. numero: " + getPuhelinnro();
}
}