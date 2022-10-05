package com.example.demo;

import com.example.demo.entities.RelojEntity;
import com.example.demo.entities.SolicitudEntity;
import com.example.demo.repositories.SolicitudRepository;
import com.example.demo.services.SolicitudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolicitudServiceTest {

    @Mock
    SolicitudRepository solicitudRepository;

    @InjectMocks
    SolicitudService solicitudService;

    @Test
    void guardarSolicitudTest(){
        SolicitudEntity solicitud=new SolicitudEntity();
        solicitud.setId_solicitud(1l);
        solicitud.setRut("20.671.147-7");
        solicitud.setFechaHorasExtra(LocalDate.parse("2022-08-24"));
        solicitud.setFirma("Recursos Humanos");
        when(solicitudRepository.save(any(SolicitudEntity.class))).thenReturn(solicitud);
        SolicitudEntity solicitudFinal=solicitudService.guardarSolicitud(solicitud);
        Assertions.assertEquals(solicitud,solicitudFinal);
    }

    @Test
    void obtenerPorRutTest(){
        SolicitudEntity solicitud1=new SolicitudEntity(1l,"20.671.147-7", LocalDate.parse("2022-08-25"),"Recursos Humanos");
        SolicitudEntity solicitud2=new SolicitudEntity(2l,"20.671.147-7", LocalDate.parse("2022-08-26"),"Recursos Humanos");
        SolicitudEntity solicitud3=new SolicitudEntity(3l,"20.671.147-7", LocalDate.parse("2022-08-27"),"Recursos Humanos");
        ArrayList<SolicitudEntity> solicitudes=new ArrayList<>();
        String rutEmpleado="20.671.147-7";
        solicitudes.add(solicitud1);
        solicitudes.add(solicitud2);
        solicitudes.add(solicitud3);
        when(solicitudRepository.findAllByRut(rutEmpleado)).thenReturn(solicitudes);
        ArrayList<SolicitudEntity> solicitudesFinal=solicitudService.obtenerPorRut(rutEmpleado);
        Assertions.assertEquals(solicitudes,solicitudesFinal);
    }
}
