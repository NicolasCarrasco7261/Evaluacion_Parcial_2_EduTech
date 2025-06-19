package com.edutech_innovators.proyect.restcontrollers;

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
import com.edutech_innovators.proyect.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.edutech_innovators.proyect.entities.Usuario;
import java.util.List;
import java.util.Optional;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

    // Instanciar UsuarioService 
    @Autowired
    private UsuarioService service;

    // Obtener documentacion de la lista de los usuarios registrados

    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve todos los usuarios disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada correctamente", 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public List<Usuario> List(){

        return service.findByAll();

    }

    // Obtener documentacion de la lista de los usuarios registrados filtrados por id

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene el detalle de un usuario en especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o inexistente")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){

        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    // Metodo para crear un usuario nuevo y ver la documentacion

    @Operation(summary = "Crear un nuevo Usuario", description = "Crea un nuevo Usuario con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente", 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class)))
    @PostMapping
    public ResponseEntity<Usuario> crear (@RequestBody Usuario unUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unUsuario));
    }

    // Metodo para modificar un usuario en especifico y ver la documentacion (pendiente swagger)

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuario unUsuario){
        Optional <Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()){
            Usuario usuarioexistente = usuarioOptional.get();
            usuarioexistente.setNombre(unUsuario.getNombre());
            usuarioexistente.setCorreo(unUsuario.getCorreo());
            usuarioexistente.setContraseña(unUsuario.getContraseña());
            usuarioexistente.setTelefono(unUsuario.getTelefono());
            usuarioexistente.setDireccion(unUsuario.getDireccion());
            Usuario usuariomodificado = service.save(usuarioexistente);
            return ResponseEntity.ok(usuariomodificado);

        }
        return ResponseEntity.notFound().build();
    }

    // Metodo para eliminar un usuario especifico y ver documentacion (pendiente swagger)

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Usuario unUsuario = new Usuario();
        unUsuario.setId(id);
        Optional<Usuario> usuarioOptional = service.delete(unUsuario);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }




}