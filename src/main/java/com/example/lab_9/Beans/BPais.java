package com.example.lab_9.Beans;

public class BPais {

    private int idPais;
    private String nombre;
    private long poblacion;
    private double tamanho;
    private BContinente continente;

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(long poblacion) {
        this.poblacion = poblacion;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public BContinente getContinente() {
        return continente;
    }

    public void setContinente(BContinente continente) {
        this.continente = continente;
    }
}
