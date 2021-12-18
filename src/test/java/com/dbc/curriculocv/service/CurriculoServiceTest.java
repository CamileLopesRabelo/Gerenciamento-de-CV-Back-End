package com.dbc.curriculocv.service;

import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.CurriculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("CurricloServiceTest")
public class CurriculoServiceTest {
    @InjectMocks
    private CurriculoService curriculoService;

    @Mock
    private CurriculoRepository curriculoRepository;
    @Mock
    private CandidatoRepository candidatoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(curriculoService, "objectMapper", objectMapper);
    }

    @Test
    public void test() {

    }
}
