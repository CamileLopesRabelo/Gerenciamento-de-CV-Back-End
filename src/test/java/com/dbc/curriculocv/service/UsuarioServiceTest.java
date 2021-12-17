package com.dbc.curriculocv.service;

import com.dbc.curriculocv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUmEmailCadastrado() {
        String email = "email@email.com.br";
        usuarioService.findByEmail(email);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

//    @Test
//    void deveCriarUmNovoUsuario() throws RegraDeNegocioException {
//        Usuario usuario = new Usuario();
//        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("sdas", "sadsa@gmail.com", "das*B545da");
//
////        when(objectMapper.convertValue(usuarioCreateDTO, Usuario.class)).thenReturn(usuario);
////        doReturn(usuarioRepository.save(usuario)).when(any());
//
//        usuarioService.create(usuarioCreateDTO);
//
//        verify(usuarioRepository, times(1)).save(usuario);
//    }


}

