package com.dbc.curriculocv.scheduler;

import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.VagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Rotinas {
    private final VagaService vagaService;

    // Executa de hora em hora
    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateTableVaga() throws RegraDeNegocioException {
        vagaService.updateTable();
    }
}
