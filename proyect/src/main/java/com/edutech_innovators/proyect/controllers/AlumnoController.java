package com.edutech_innovators.proyect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edutech_innovators.proyect.services.AlumnoService;
import com.edutech_innovators.proyect.entities.Alumno;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/alumnos")
public class AlumnoController {


    @Autowired
    private AlumnoService service;


    @GetMapping
    public List<Alumno> List(){

        return service.findByAll();

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){

        Optional<Alumno> alumnoOptional = service.findById(id);
        if(alumnoOptional.isPresent()){
            return ResponseEntity.ok(alumnoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<Alumno> crear (@RequestBody Alumno unAlumno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unAlumno));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Alumno unAlumno){
        Optional <Alumno> alumnoOptional = service.findById(id);
        if (alumnoOptional.isPresent()){
            Alumno alumnoexistente = alumnoOptional.get();
            alumnoexistente.setCorreo(unAlumno.getCorreo());
            alumnoexistente.setNivel(unAlumno.getNivel());
            alumnoexistente.setCurso(unAlumno.getCurso());
            Alumno alumnomodificado = service.save(alumnoexistente);
            return ResponseEntity.ok(alumnomodificado);

        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Alumno unAlumno = new Alumno();
        unAlumno.setId(id);
        Optional<Alumno> alumnoOptional = service.delete(unAlumno);
        if(alumnoOptional.isPresent()){
            return ResponseEntity.ok(alumnoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }








}