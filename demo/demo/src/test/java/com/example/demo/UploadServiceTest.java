package com.example.demo;

import com.example.demo.services.UploadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {

    @InjectMocks
    UploadService uploadService;
    @Test
    void importarArchivoConContenidodeNombreDATOStxt() {
        String contenido = "2022/10/03;08:00;12.345.678-2";
        MockMultipartFile file = new MockMultipartFile("DATOS", "DATOS.TXT", "text/plain", "2022/10/03;08:00;20.580.291-6".getBytes());
        //MultipartFile filefinal=
        uploadService.save(file);
        //Assertions.assertEquals(file, filefinal);
    }
}
