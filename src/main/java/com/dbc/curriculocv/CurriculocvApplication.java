package com.dbc.curriculocv;

import com.dbc.curriculocv.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class CurriculocvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculocvApplication.class, args);
	}

}
