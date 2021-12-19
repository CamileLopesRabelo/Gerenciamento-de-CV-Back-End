package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Usuario;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("Usuário Service Testes")
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    public void deveCriarUmNovoUsuario() throws RegraDeNegocioException {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setEmail("teste@teste.com.br");
        usuarioCreateDTO.setSenha("teste");
        Usuario usuarioSave = new Usuario();
        usuarioSave.setIdUsuario(1);
        usuarioSave.setEmail("teste@teste.com.br");
        usuarioSave.setSenha(crypt.encode(usuarioCreateDTO.getSenha()));


        when(usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any())).thenReturn(usuarioSave);

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO);
        assertNotNull(usuarioDTO);
        assertEquals(1, usuarioDTO.getIdUsuario());
        assertEquals("teste@teste.com.br", usuarioDTO.getEmail());
    }

    @Test(expected = RegraDeNegocioException.class)
    @DisplayName("Deve cair na exceção ao criar um novo usuário")
    public void deveCriarUmNovoUsuarioSemSucesso() throws RegraDeNegocioException {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setEmail("teste@teste.com.br");
        usuarioCreateDTO.setSenha("teste");
        Usuario usuarioSave = new Usuario();
        usuarioSave.setIdUsuario(1);
        usuarioSave.setEmail("teste@teste.com.br");
        usuarioSave.setSenha(crypt.encode(usuarioCreateDTO.getSenha()));


        when(usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())).thenReturn(true);

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO);
        assertNotNull(usuarioDTO);
        assertEquals(1, usuarioDTO.getIdUsuario());
        assertEquals("teste@teste.com.br", usuarioDTO.getEmail());
    }
}

