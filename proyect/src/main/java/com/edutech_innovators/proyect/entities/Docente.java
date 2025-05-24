package com.edutech_innovators.proyect.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="docente")
public class Docente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String correo;
    private int experiencia;
    private String catedra;


    public Docente() {
    }


    public Docente(long id, String correo, int experiencia, String catedra) {
        this.id = id;
        this.correo = correo;
        this.experiencia = experiencia;
        this.catedra = catedra;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public int getExperiencia() {
        return experiencia;
    }


    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }


    public String getCatedra() {
        return catedra;
    }


    public void setCatedra(String catedra) {
        this.catedra = catedra;
    }

    

    

}

