package com.example.demo.services;

import com.example.demo.entities.RelojEntity;
import com.example.demo.entities.SolicitudEntity;
import com.example.demo.repositories.RelojRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class RelojService {
    @Autowired
    RelojRepository relojRepository;

    public RelojEntity guardarMarca(RelojEntity reloj) {
        return relojRepository.save(reloj);
    }

    public ArrayList<RelojEntity> obtenerMarcasEmpleado(String rut) {
        return relojRepository.findAllByRut(rut);
    }

    public ArrayList<LocalDate> obtenerDiasTrabajables(){
            ArrayList<Date> fechasTrabajables=relojRepository.findAllDates();
            ArrayList<LocalDate> fechasTrabajablesLocalDate=new ArrayList<>();
            LocalDate fechaActual;
            for(int i=0;i<fechasTrabajables.size();i++){
                fechaActual=fechasTrabajables.get(i).toLocalDate();
                fechasTrabajablesLocalDate.add(fechaActual);
            }
            return fechasTrabajablesLocalDate;
    }


    public int read(MultipartFile file) {
        String linea, rut;
        LocalTime hora;
        LocalDate fecha;
        RelojEntity marcaActual;
        int id = 1;
        try {
            String carpeta = "uploads//";
            Path path = Paths.get(carpeta + file.getOriginalFilename());
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            linea = bufferedReader.readLine();
            while (linea != null) {
                String[] lista = linea.split(";");
                fecha = LocalDate.parse(lista[0], DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.US));
                hora = LocalTime.parse(lista[1], DateTimeFormatter.ofPattern("HH:mm", Locale.US));
                rut = lista[2];
                marcaActual = relojRepository.save(new RelojEntity((long) id, rut, fecha, hora));
                guardarMarca(marcaActual);
                linea = bufferedReader.readLine();
                id = id + 1;
            }
            return 1;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }


}
