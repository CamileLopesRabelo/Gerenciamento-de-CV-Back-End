package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.CurriculoDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Curriculo;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.CurriculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("CandidatoServiceTest")
public class CurriculoServiceTest {

    @InjectMocks
    private CurriculoService curriculoService;

    @Mock
    private CurriculoRepository curriculoRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private MockHttpServletRequest mockRequest;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(curriculoService, "objectMapper", objectMapper);
        mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath("/download-curriculo/");

    }

    @Test
    @DisplayName("deve fazer o upload do curriculo")
    public void uploadCurriculo() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        Curriculo curriculo = new Curriculo();
        curriculo.setIdCurriculo(1);
        curriculo.setFileType("application/pdf");
        String fileName = StringUtils.cleanPath(new BCryptPasswordEncoder().encode(candidato.getIdCandidato()+ "_" + candidato.getNome()) + ".pdf");
        curriculo.setFileName(fileName);
        curriculo.setSize(200L);
        byte[] data = new byte[0];
        curriculo.setData(data);
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return curriculo.getFileName();
            }

            @Override
            public String getOriginalFilename() {
                return curriculo.getFileName();
            }

            @Override
            public String getContentType() {
                return curriculo.getFileType();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return curriculo.getSize();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };

        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);


        when(candidatoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(candidato));
        when(curriculoRepository.getCurriculoByIdCandidato(ArgumentMatchers.any())).thenReturn(curriculo);
        when(curriculoRepository.save(ArgumentMatchers.any())).thenReturn(curriculo);

        CurriculoDTO curriculoDTO = curriculoService.uploadCurriculo(file, candidato.getIdCandidato());

        assertNotNull(curriculoDTO);
        assertEquals(file.getName(),curriculoDTO.getFileName());
    }
}
