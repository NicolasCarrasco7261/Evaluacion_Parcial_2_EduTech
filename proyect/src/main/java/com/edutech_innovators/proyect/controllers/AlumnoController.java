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

// Swagger OpenAPI annotations
import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.media.Content; 
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse; 
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Alumnos", description = "Operaciones relacionadas con alumnos")
@RestController
@RequestMapping("api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    // Documentación swagger Método findByAll
    @Operation(summary = "Obtener lista de alumnos",
    description = "Devuelve todos los alumnos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos obtenida correctamente",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Alumno.class)))
    @GetMapping
    public List<Alumno> List() {
        return service.findByAll();
    }

    //Documentación swagger Método findById
    @Operation(summary = "Obtener alumno por ID", 
             description = "Obtiene el detalle de un alumno específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Alumno.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Alumno> alumnoOptional = service.findById(id);
        if (alumnoOptional.isPresent()) {
            return ResponseEntity.ok(alumnoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // Documentación swagger Método save
    @Operation(summary = "Crear nuevo alumno", 
    description = "Crear un alumno con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Alumno creado correctamente",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Alumno.class)))
    @PostMapping
    public ResponseEntity<Alumno> crear (@RequestBody Alumno unAlumno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unAlumno));
    }

    // Documentación swagger Método update
    @Operation(
    summary = "Modificar un alumno existente",
    description = "Permite actualizar los datos de un alumno a partir de su ID. Si el alumno no existe, se devuelve un error 404."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno modificado correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Alumno.class))),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
            content = @Content)
    })
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

    // Documentación swagger Método delete
    @Operation(
    summary = "Eliminar un alumno",
    description = "Elimina un alumno existente por su ID. Si el alumno no se encuentra, devuelve un error 404."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno eliminado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Alumno.class))),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
            content = @Content)
    })
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