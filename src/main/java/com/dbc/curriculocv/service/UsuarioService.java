package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.UsuarioCreateDTO;
import com.dbc.curriculocv.dto.UsuarioDTO;
import com.dbc.curriculocv.entity.Regra;
import com.dbc.curriculocv.entity.Usuario;
import com.dbc.curriculocv.repository.RegraRepository;
import com.dbc.curriculocv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final RegraRepository regraRepository;

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario entity = objectMapper.convertValue(usuarioCreateDTO, Usuario.class);
        entity.setNome(usuarioCreateDTO.getNome());
        entity.setEmail(usuarioCreateDTO.getEmail());
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        List<Regra> regras = new ArrayList<>();
        Regra regra = regraRepository.findById(1).orElseThrow();
        regras.add(regra);
        entity.setRegras(regras);
        Usuario save = usuarioRepository.save(entity);
        return new UsuarioDTO(save.getIdUsuario(), save.getUsername(), save.getEmail());
    }
}
