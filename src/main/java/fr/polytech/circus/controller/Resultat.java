package fr.polytech.circus.controller;

import java.util.Date;

public class Resultat {

    private  String id;
    private  int age ;
    private  String sexe ;
    private String Méthode;
    private String lieu;
    private Date DateDeNaissance;
    private String particien;
    private String PréférenceManuelle;
    private String DominanceOculaire;
    //positive ou negative
    private String resultatTest ;


    public String getResultatTest() {
        return resultatTest;
    }

    public void setResultatTest(String resultatTest) {
        this.resultatTest = resultatTest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getMéthode() {
        return Méthode;
    }

    public void setMéthode(String méthode) {
        Méthode = méthode;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateDeNaissance() {
        return DateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        DateDeNaissance = dateDeNaissance;
    }

    public String getParticien() {
        return particien;
    }

    public void setParticien(String particien) {
        this.particien = particien;
    }

    public String getPréférenceManuelle() {
        return PréférenceManuelle;
    }

    public void setPréférenceManuelle(String préférenceManuelle) {
        PréférenceManuelle = préférenceManuelle;
    }

    public String getDominanceOculaire() {
        return DominanceOculaire;
    }

    public void setDominanceOculaire(String dominanceOculaire) {
        DominanceOculaire = dominanceOculaire;
    }
}
