package com.edutech_innovators.proyect.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alumno")
public class Alumno {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String correo;
    private int nivel;
    private String curso;


    public Alumno() {
    }


    public Alumno(long id, String correo, int nivel, String curso) {
        this.id = id;
        this.correo = correo;
        this.nivel = nivel;
        this.curso = curso;
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


    public int getNivel() {
        return nivel;
    }


    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    public String getCurso() {
        return curso;
    }


    public void setCurso(String curso) {
        this.curso = curso;
    }


    


    

    
    

}