package com.edutech_innovators.proyect.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import com.edutech_innovators.proyect.entities.Usuario;
import com.edutech_innovators.proyect.services.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioServiceImpl usuarioserviceimpl;

    private List<Usuario>usuarioLista;

    @Test
    @DisplayName("Debería retornar código 200 y la lista de usuarios")
    public void verUsuariosTest() throws Exception{
        when(usuarioserviceimpl.findByAll()).thenReturn(usuarioLista);
        mockmvc.perform(get("/api/usuarios")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar código 200 y los datos del usuario existente")
    public void verunUsuarioTest() {
        Usuario unUsuario = new Usuario(1L, "María López", "maria.lopez@example.com", "m4r1aL0p3z", "912345678", "Calle Los Pinos 456, Santiago");
        try{
            when(usuarioserviceimpl.findById(1l)).thenReturn(Optional.of(unUsuario));
            mockmvc.perform(get("/api/usuarios/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("El testing lanzo un error " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("Debería retornar 404 si el usuario no existe")
    public void usuarioNoExisteTest() throws Exception{
        when(usuarioserviceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/usuarios/10")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería crear un usuario correctamente y retornar código 201")
    public void crearUsuarioTest() throws Exception{
        Usuario unUsuario = new Usuario(null, "Carlos Díaz", "carlos.diaz@example.com", "c4rl0sD", "923456789", "Av. Libertad 789, Valparaíso");
        Usuario otroUsuario = new Usuario(4L, "Fernanda Rojas", "fernanda.rojas@example.com", "f3rn4Rj", "934567890", "Pasaje El Sol 321, Concepción");
        when(usuarioserviceimpl.save(any(Usuario.class))).thenReturn(otroUsuario);
        mockmvc.perform(post("/api/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(unUsuario)))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debería modificar un usuario existente y retornar código 200")
    public void modificarUsuarioTest() throws Exception {
        Usuario unUsuario = new Usuario(1L, "Ana Castillo", "ana.castillo@example.com", "an4C@st", "956789012", "Av. Los Leones 101, Viña del Mar");
        Usuario otroUsuario = new Usuario(1L, "Tomás Herrera", "tomas.herrera@example.com", "t0m4sH3rr", "967890123", "Calle Las Palmas 202, Antofagasta");
        when(usuarioserviceimpl.findById(1L)).thenReturn(Optional.of(unUsuario));
        when(usuarioserviceimpl.save(any(Usuario.class))).thenReturn(otroUsuario);
        mockmvc.perform(put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(otroUsuario)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar 404 al intentar modificar un usuario inexistente")
    public void modificarUsuarioNoExisteTest() throws Exception {
        Usuario otroUsuario = new Usuario(1L, "Tomás Herrera", "tomas.herrera@example.com", "t0m4sH3rr", "967890123", "Calle Las Palmas 202, Antofagasta");
        when(usuarioserviceimpl.findById(1L)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(otroUsuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería eliminar un usuario existente y retornar código 200")
    public void eliminarUsuarioExistenteTest() throws Exception {
        Usuario unUsuario = new Usuario(1L, "Valentina Silva", "valentina.silva@example.com", "V4l3S1lv4", "978901234", "Calle OHiggins 303, Rancagua");
        when(usuarioserviceimpl.delete(any(Usuario.class))).thenReturn(Optional.of(unUsuario));

        mockmvc.perform(delete("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Debería retornar 404 al intentar eliminar un usuario que no existe")
    public void eliminarUsuarioNoExisteTest() throws Exception {
        when(usuarioserviceimpl.delete(any(Usuario.class))).thenReturn(Optional.empty());
        mockmvc.perform(delete("/api/usuario/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
