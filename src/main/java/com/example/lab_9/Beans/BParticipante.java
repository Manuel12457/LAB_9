package com.example.lab_9.Beans;

public class BParticipante {

    private int idParticipante;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private BAlumno alumno;
    private BPais pais;

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public BAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(BAlumno alumno) {
        this.alumno = alumno;
    }

    public BPais getPais() {
        return pais;
    }

    public void setPais(BPais pais) {
        this.pais = pais;
    }
}
