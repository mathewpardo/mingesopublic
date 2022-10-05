package com.example.demo;

import com.example.demo.entities.RelojEntity;
import com.example.demo.repositories.RelojRepository;
import com.example.demo.services.RelojService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RelojServiceTest {

    @Mock
    RelojRepository relojRepository;

    @InjectMocks
    RelojService relojService;

    @Test
    void guardarMarcaTest() {
        RelojEntity marca = new RelojEntity();
        marca.setRut("20.671.147-7");
        marca.setFecha(LocalDate.parse("2022-08-23"));
        marca.setId_reloj(1l);
        marca.setHora(LocalTime.parse("08:00:00"));
        when(relojRepository.save(any(RelojEntity.class))).thenReturn(marca);
        RelojEntity marcaFinal = relojService.guardarMarca(marca);
        assertThat(marcaFinal).isEqualTo(marca);
    }

    @Test
    void obtenerMarcasEmpleadosTest() {
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:00:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("16:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:00:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("16:00:00"));
        ArrayList<RelojEntity> arrayMarcas = new ArrayList<>();
        String rutEmpleado = "20.671.147-7";
        arrayMarcas.add(marca1);
        arrayMarcas.add(marca2);
        arrayMarcas.add(marca3);
        arrayMarcas.add(marca4);
        when(relojRepository.findAllByRut(rutEmpleado)).thenReturn(arrayMarcas);
        ArrayList<RelojEntity> arrayMarcasFinal;
        arrayMarcasFinal = relojService.obtenerMarcasEmpleado(rutEmpleado);
        assertEquals(arrayMarcas, arrayMarcasFinal);
    }

    @Test
    void obtenerDiasTrabajablesTest() {
        LocalDate fecha1 = LocalDate.parse("2022-08-13");
        LocalDate fecha2 = LocalDate.parse("2022-08-14");
        LocalDate fecha3 = LocalDate.parse("2022-08-15");
        LocalDate fecha4 = LocalDate.parse("2022-08-16");
        LocalDate fecha5 = LocalDate.parse("2022-08-17");
        ArrayList<LocalDate> fechas = new ArrayList<>();
        fechas.add(fecha1);
        fechas.add(fecha2);
        fechas.add(fecha3);
        fechas.add(fecha4);
        fechas.add(fecha5);

        Date date1 = Date.valueOf(fecha1);
        Date date2 = Date.valueOf(fecha2);
        Date date3 = Date.valueOf(fecha3);
        Date date4 = Date.valueOf(fecha4);
        Date date5 = Date.valueOf(fecha5);

        ArrayList<Date> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        dates.add(date3);
        dates.add(date4);
        dates.add(date5);
        when(relojRepository.findAllDates()).thenReturn(dates);
        ArrayList<LocalDate> fechasFinal = relojService.obtenerDiasTrabajables();
        assertEquals(fechas, fechasFinal);
    }

    @Test
    void readTrue() {
        MockMultipartFile file = new MockMultipartFile("DATA", "DATOS.TXT", "text/plain", "2022/10/03;08:00;20.580.291-6".getBytes());
        int comprobador = this.relojService.read(file);
        assertEquals(1, comprobador, 0);
    }

    @Test
    void readFalse(){
        MockMultipartFile file = new MockMultipartFile("DATA", "Test.txt", "text/plain", "2022/10/03;08:00;20.580.291-6".getBytes());
        int comprobador = relojService.read(file);
        assertEquals(0,comprobador);
    }
}
