package com.example.lab_9.Beans;

public class BContinente {

    private int idContinente;
    private String continente;

    public BContinente(int idContinente, String continente) {
        this.continente = continente;
        this.idContinente = idContinente;
    }

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }
}
