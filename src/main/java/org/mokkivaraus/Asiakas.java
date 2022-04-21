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



}