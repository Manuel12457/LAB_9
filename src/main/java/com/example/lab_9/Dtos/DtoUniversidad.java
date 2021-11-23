package com.example.lab_9.Dtos;

import com.example.lab_9.Beans.BUniversidad;

public class DtoUniversidad {

    private BUniversidad universidad;
    private int alumnos;


    public BUniversidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(BUniversidad universidad) {
        this.universidad = universidad;
    }

    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }
}
