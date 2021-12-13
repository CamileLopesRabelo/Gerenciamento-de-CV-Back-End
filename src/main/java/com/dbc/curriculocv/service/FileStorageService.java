package com.dbc.curriculocv.service;

import com.dbc.curriculocv.config.FileStorageProperties;
import com.dbc.curriculocv.exceptions.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível criar o diretório onde os arquivos carregados serão armazenados.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalizar o nome do arquivo
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //TODO setar o id do candidato como string

        try {
            // Verifique se o nome do arquivo contém caracteres inválidos
            if(fileName.contains("..")) {
                throw new FileStorageException("Desculpa! O nome do arquivo contém uma sequência de caminho inválida " + fileName);
            }

            // Copie o arquivo para o local de destino (substituindo o arquivo existente com o mesmo nome)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Não foi possível armazenar o arquivo " + fileName + ". Por favor, tente novamente!", ex);
        }
    }
}

