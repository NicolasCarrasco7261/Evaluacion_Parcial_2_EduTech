package com.edutech_innovators.proyect.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
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
    public void verUsuariosTest() throws Exception{
        when(usuarioserviceimpl.findByAll()).thenReturn(usuarioLista);
        mockmvc.perform(get("/api/usuarios")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
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
    public void usuarioNoExisteTest() throws Exception{
        when(usuarioserviceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/usuarios/10")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    public void crearUsuarioTest() throws Exception{
        Usuario unUsuario = new Usuario(null, "Carlos Díaz", "carlos.diaz@example.com", "c4rl0sD", "923456789", "Av. Libertad 789, Valparaíso");
        Usuario otroUsuario = new Usuario(4L, "Fernanda Rojas", "fernanda.rojas@example.com", "f3rn4Rj", "934567890", "Pasaje El Sol 321, Concepción");
        when(usuarioserviceimpl.save(any(Usuario.class))).thenReturn(otroUsuario);
        mockmvc.perform(post("/api/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(unUsuario)))
        .andExpect(status().isCreated());
    }
}
