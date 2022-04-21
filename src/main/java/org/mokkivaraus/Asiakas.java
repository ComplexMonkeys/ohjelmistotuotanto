package org.mokkivaraus;

public class Asiakas {

private int Asiakas_id;

private String Postinro;

private String Etunimi;

private String Sukunimi;

private String Lahiosoite;

private String Email;

private String Puhelinnro;


//parametriton alustaja
public Asiakas(){
}
//parametrillinen alustaja
Asiakas(int Asiakas_id,String Postinro,String Etunimi,String Sukunimi,
String Lahiosoite, String Email, String Puhelinnro){
this.Asiakas_id = Asiakas_id;
this.Postinro = Postinro;
this.Etunimi = Etunimi;
this.Sukunimi = Sukunimi;
this.Lahiosoite = Lahiosoite;
this.Email = Email;
this.Puhelinnro = Puhelinnro;
}
public int getAsiakas_id() {
    return Asiakas_id;
}
public void setAsiakas_id(int asiakas_id) {
    Asiakas_id = asiakas_id;
}
public String getPostinro() {
    return Postinro;
}
public void setPostinro(String postinro) {
    Postinro = postinro;
}
public String getEtunimi() {
    return Etunimi;
}
public void setEtunimi(String etunimi) {
    Etunimi = etunimi;
}
public String getSukunimi() {
    return Sukunimi;
}
public void setSukunimi(String sukunimi) {
    Sukunimi = sukunimi;
}
public String getLahiosoite() {
    return Lahiosoite;
}
public void setLahiosoite(String lahiosoite) {
    Lahiosoite = lahiosoite;
}
public String getEmail() {
    return Email;
}
public void setEmail(String email) {
    Email = email;
}
public String getPuhelinnro() {
    return Puhelinnro;
}
public void setPuhelinnro(String puhelinnro) {
    Puhelinnro = puhelinnro;
}

}