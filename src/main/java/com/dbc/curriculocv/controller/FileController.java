package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.payload.UploadFileResponse;
import com.dbc.curriculocv.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileStorageService fileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestPart("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        log.info("enviando documento");
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        log.info("documento enviado com sucesso!");
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

}
