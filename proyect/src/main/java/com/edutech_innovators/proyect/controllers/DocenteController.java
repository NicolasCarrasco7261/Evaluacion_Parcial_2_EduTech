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
import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.media.Content; 
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse; 
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Docentes", description = "Operaciones relacionadas con docentes") 
@RestController
@RequestMapping("api/docentes")
public class DocenteController {




    @Autowired
    private DocenteService service;

    // Documentación swagger Método findByAll
    @Operation(summary = "Obtener lista de docentes", 
    description = "Devuelve todos los docentes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de docentes correcta",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Docente.class)))
    // Método findByAll
    @GetMapping
    public List<Docente> List(){

        return service.findByAll();

    }




    // Documentación swagger Método findById
    @Operation(summary = "Obtener docentes por id", 
               description = "Obtiene el detaller de un docente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Docente encontrado",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = Docente.class))),
    })
    // Método findById
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){

        Optional<Docente> docenteOptional = service.findById(id);
        if(docenteOptional.isPresent()){
            return ResponseEntity.ok(docenteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }





    // Documentación swagger Método save
    @Operation(summary = "Crear nuevo docente", 
    description = "Crear un docente con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Docente creado correctamente",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Docente.class)))
    // Método save
    @PostMapping
    public ResponseEntity<Docente> crear (@RequestBody Docente unDocente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unDocente));
    }



    // Documentación swagger Método update
    @Operation(
    summary = "Modificar un docente existente",
    description = "Permite actualizar los datos de un docente a partir de su ID. Si el docente no existe, se devuelve un error 404."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Docente modificado correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Docente.class))),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado",
            content = @Content)
    })
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




    @Operation(
    summary = "Eliminar un docente",
    description = "Elimina un docente existente por su ID. Si el docente no se encuentra, devuelve un error 404."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Docente eliminado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Docente.class))),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado",
            content = @Content)
    })
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