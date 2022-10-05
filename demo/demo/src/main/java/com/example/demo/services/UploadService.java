package com.example.demo.services;

import com.example.demo.repositories.RelojRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UploadService {
    // Autowired
    // RelojRepository relojRepository;
    private String folder="uploads//";
    private final Logger logg = LoggerFactory.getLogger(UploadService.class);

    public String save(MultipartFile file) {
        if (!file.isEmpty()){
            try {
                byte [] bytes = file.getBytes();
                Path path = Paths.get(folder+file.getOriginalFilename());
                Files.write(path, bytes);
                logg.info("Archivo guardado");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return "Archivo guardado correctamente";
    }

}
