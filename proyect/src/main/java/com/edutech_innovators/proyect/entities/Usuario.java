package com.edutech_innovators.proyect.entities;

// Importaciones de JPA (Jakarta Persistence API)

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// Clase Usuario representada en la BD como una tabla llamada "usuario" 
@Entity
@Table(name="usuario")
public class Usuario {

    // Atributos de Usuario
    // Generacion de idUsuario de forma automatica con IDENTITY

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String correo;
    private String contraseña;
    private String telefono;
    private String direccion;

    // Constructor vacio para que JPA pueda instanciar con @Entity al Usuario en la BD automaticamente

    public Usuario() {
    }

    // Constructor con los atributos de Usuario para inicializarlos

    public Usuario(Long id, String nombre, String correo, String contraseña, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Metodos Getter and Setter para obtener o modificar los atributos de Usuario

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    
}
