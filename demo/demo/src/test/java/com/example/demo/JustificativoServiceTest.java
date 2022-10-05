package com.example.demo;

import com.example.demo.entities.JustificativoEntity;
import com.example.demo.entities.RelojEntity;

import com.example.demo.repositories.JustificativoRepository;
import com.example.demo.services.JustificativoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JustificativoServiceTest {

    RelojEntity marca=new RelojEntity();

    JustificativoEntity justificativo;

    @Mock
    JustificativoRepository justificativoRepository;

    @InjectMocks
    JustificativoService justificativoService;



    @Test
    void verificarJustificativoTest(){
        marca.setFecha(LocalDate.parse("2022-08-25"));
        marca.setRut("20.671.147-7");
        JustificativoEntity justificativo=new JustificativoEntity((long)1,"20.671.147-7",LocalDate.parse("2022-08-25"),"Recursos Humanos");
        ArrayList<JustificativoEntity> arrayJustificativo=new ArrayList<>();
        arrayJustificativo.add(justificativo);
        when(justificativoRepository.findAllByRut(marca.getRut())).thenReturn(arrayJustificativo);
        boolean resultado =this.justificativoService.verificarJustificativo(marca);
        Assertions.assertEquals(true,resultado);
    }

    @Test
    void verificarJustificativoTestFalse(){
        marca.setFecha(LocalDate.parse("2022-08-25"));
        marca.setRut("20.671.147-7");
        JustificativoEntity justificativo=new JustificativoEntity((long)1,"20.671.147-7",LocalDate.parse("2022-08-24"),"Recursos Humanos");
        ArrayList<JustificativoEntity> arrayJustificativo=new ArrayList<>();
        arrayJustificativo.add(justificativo);
        when(justificativoRepository.findAllByRut(marca.getRut())).thenReturn(arrayJustificativo);
        boolean resultado =this.justificativoService.verificarJustificativo(marca);
        Assertions.assertEquals(false,resultado);
    }

    @Test
    void verificarJustificativoFechaTest(){
        LocalDate fecha=LocalDate.parse("2022-08-24");
        String rutEmpleado="20.671.147-7";
        JustificativoEntity justificativo=new JustificativoEntity((long)1,"20.671.147-7",LocalDate.parse("2022-08-24"),"Recursos Humanos");
        ArrayList<JustificativoEntity> arrayJustificativo=new ArrayList<>();
        arrayJustificativo.add(justificativo);
        when(justificativoRepository.findAllByRut(rutEmpleado)).thenReturn(arrayJustificativo);
        boolean resultado =this.justificativoService.verificarJustificativo(fecha,rutEmpleado);
        Assertions.assertEquals(true,resultado);
    }

    @Test
    void verificarJustificativoFechaTestFalse(){
        LocalDate fecha=LocalDate.parse("2022-08-25");
        String rutEmpleado="20.671.147-7";
        JustificativoEntity justificativo=new JustificativoEntity((long)1,"20.671.147-7",LocalDate.parse("2022-08-24"),"Recursos Humanos");
        ArrayList<JustificativoEntity> arrayJustificativo=new ArrayList<>();
        arrayJustificativo.add(justificativo);
        when(justificativoRepository.findAllByRut(rutEmpleado)).thenReturn(arrayJustificativo);
        boolean resultado =this.justificativoService.verificarJustificativo(fecha,rutEmpleado);
        Assertions.assertEquals(false,resultado);
    }

    @Test
    void guardarJustificativoTest(){
        JustificativoEntity justificativoEmployee = new JustificativoEntity();
        justificativoEmployee.setRut("20.671.147-7");
        justificativoEmployee.setId_justificativo(1l);
        justificativoEmployee.setFecha(LocalDate.parse("2020-09-09"));
        justificativoEmployee.setFirma("firma");
        when(justificativoRepository.save(any(JustificativoEntity.class))).thenReturn(justificativoEmployee);
        JustificativoEntity justificativoFinal = justificativoService.guardarJustificativo(justificativoEmployee);
        assertThat(justificativoFinal).isEqualTo(justificativoEmployee);
    }
}
