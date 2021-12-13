package com.dbc.curriculocv.schedule;

import com.dbc.curriculocv.service.VagaService;
import com.dbc.curriculocv.service.VagasCompleoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class VagasSchedulerCron {
    private final VagaService vagaService;

//    @Scheduled(cron = "* * * * * *", zone = "GMT-3")
    @Scheduled(fixedDelay = 600000)
    public void BuscarVagasCompleoScheduler() throws InterruptedException {
        log.info("Schedule vaga rodando");
        vagaService.updateTable();
        log.info("banco postgrees upado");
    }
}
