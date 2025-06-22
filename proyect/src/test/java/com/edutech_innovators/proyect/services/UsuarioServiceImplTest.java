package com.edutech_innovators.proyect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.edutech_innovators.proyect.entities.Usuario;
import com.edutech_innovators.proyect.repository.UsuarioRepository;

public class UsuarioServiceImplTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioserviceImpl;

    @Mock
    private UsuarioRepository usuariorepository;

    List <Usuario> list = new ArrayList<Usuario>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.chargeUsuario();
    }
    
    @Test
    @DisplayName("Debería retornar una lista con todos los usuarios simulados")
    public void findByAllTest(){
        when(usuariorepository.findAll()).thenReturn(list);
        List<Usuario> response = usuarioserviceImpl.findByAll();
        assertEquals(3, response.size());
        verify(usuariorepository, times(1)).findAll();
    }

    public void chargeUsuario(){
        Usuario usu1= new Usuario(1L, "Diego Torres", "diego.torres@example.com", "D13g0T0rr3s", "989012345", "Av. Principal 404, Puerto Montt");
        Usuario usu2 = new Usuario(2L, "Camila Ramírez", "camila.ramirez@example.com", "C4m1l4R!", "900123456", "Pasaje Los Álamos 505, Temuco");
        Usuario usu3 = new Usuario(3L, "Ignacio Morales", "ignacio.morales@example.com", "1gn4c10M", "911234567", "Calle Nueva 606, Talca");

        list.add(usu1);
        list.add(usu2);
        list.add(usu3);
    }

    @Test
    @DisplayName("Validar que se obtenga correctamente un usuario especifico")
    public void findByIdTest(){
        Usuario unUsuario = new Usuario(4L, "Fernanda Rojas", "fernanda.rojas@example.com", "f3rn4Rj", "934567890", "Pasaje El Sol 321, Concepción");
        when(usuariorepository.findById(4L)).thenReturn(Optional.of(unUsuario));
        Optional<Usuario> result = usuarioserviceImpl.findById(4L);
        assertTrue(result.isPresent());
        assertEquals("Fernanda Rojas", result.get().getNombre());
        assertEquals("fernanda.rojas@example.com", result.get().getCorreo());
        assertEquals("f3rn4Rj", result.get().getContraseña());
        assertEquals("934567890", result.get().getTelefono());
        assertEquals("Pasaje El Sol 321, Concepción", result.get().getDireccion());
        verify(usuariorepository, times(1)).findById(4L);
    }

    @Test
    @DisplayName("Validar que guarde correctamente un usuario")
    public void saveUsuarioTest() {
        Usuario usuarioToSave = new Usuario(10L, "Matías Vega", "matias.vega@example.com", "M4t14sV3g4", "933456789", "Calle Los Arrayanes 808, Chillán");
        Usuario usuarioSaved = new Usuario(10L, "Matías Vega", "matias.vega@example.com", "M4t14sV3g4", "933456789", "Calle Los Arrayanes 808, Chillán");
        when(usuariorepository.save(usuarioToSave)).thenReturn(usuarioSaved);
        Usuario result = usuarioserviceImpl.save(usuarioToSave);
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Matías Vega", result.getNombre());
        assertEquals("matias.vega@example.com", result.getCorreo());
        assertEquals("M4t14sV3g4", result.getContraseña());
        assertEquals("933456789", result.getTelefono());
        assertEquals("Calle Los Arrayanes 808, Chillán", result.getDireccion());
        verify(usuariorepository, times(1)).save(usuarioToSave);
    }

    //Test para el metodo delete
    @Test
    @DisplayName("Validar que elimine correctamente un usuario especifico")
    public void deleteUsuarioTest() {
        Usuario delUsuario = new Usuario(15L, "Sofía Navarro", "sofia.navarro@example.com", "S0f1N@v2025", "944567890", "Av. del Mar 909, Arica");
        when(usuariorepository.findById(15L)).thenReturn(Optional.of(delUsuario));
        doNothing().when(usuariorepository).delete(delUsuario);
        Optional<Usuario> result = usuarioserviceImpl.delete(delUsuario);
        assertTrue(result.isPresent());
        assertEquals(15L, result.get().getId());
        verify(usuariorepository, times(1)).findById(15L);
        verify(usuariorepository, times(1)).delete(delUsuario);
    }
}
