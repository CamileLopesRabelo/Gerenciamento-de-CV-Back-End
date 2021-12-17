package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.UsuarioCreateDTO;
import com.dbc.curriculocv.dto.UsuarioDTO;
import com.dbc.curriculocv.entity.Usuario;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        if (usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("Email já cadastrado");
        }
        Usuario entity = objectMapper.convertValue(usuarioCreateDTO, Usuario.class);
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        Usuario save = usuarioRepository.save(entity);
        return new UsuarioDTO(save.getIdUsuario(), save.getNome(), save.getEmail());
    }

    public UsuarioDTO retrieveUser() {
        int idUsuario = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }
}
