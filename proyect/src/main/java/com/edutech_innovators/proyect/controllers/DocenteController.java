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
import com.edutech_innovators.proyect.services.DocenteService;
import com.edutech_innovators.proyect.entities.Docente;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/docentes")
public class DocenteController {


    @Autowired
    private DocenteService service;


    @GetMapping
    public List<Docente> List(){

        return service.findByAll();

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){

        Optional<Docente> docenteOptional = service.findById(id);
        if(docenteOptional.isPresent()){
            return ResponseEntity.ok(docenteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }



    @PostMapping
    public ResponseEntity<Docente> crear (@RequestBody Docente unDocente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unDocente));
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Docente unDocente){
        Optional <Docente> docenteOptional = service.findById(id);
        if (docenteOptional.isPresent()){
            Docente docenteexistente = docenteOptional.get();
            docenteexistente.setCorreo(unDocente.getCorreo());
            docenteexistente.setExperiencia(unDocente.getExperiencia());
            docenteexistente.setCatedra(unDocente.getCatedra());
            Docente docentemodificado = service.save(docenteexistente);
            return ResponseEntity.ok(docentemodificado);

        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Docente unDocente = new Docente();
        unDocente.setId(id);
        Optional<Docente> docenteOptional = service.delete(unDocente);
        if(docenteOptional.isPresent()){
            return ResponseEntity.ok(docenteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }




    

}