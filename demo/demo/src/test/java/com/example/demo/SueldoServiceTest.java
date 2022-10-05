package com.example.demo;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.RelojEntity;
import com.example.demo.entities.SolicitudEntity;
import com.example.demo.entities.SueldoEntity;

import com.example.demo.repositories.SueldoRepository;
import com.example.demo.services.JustificativoService;
import com.example.demo.services.RelojService;
import com.example.demo.services.SolicitudService;
import com.example.demo.services.SueldoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SueldoServiceTest {
    @Mock
    SueldoRepository sueldoRepository;
    @InjectMocks
    SueldoService sueldoService;

    @Mock
    RelojService relojService;

    @Mock
    SolicitudService solicitudService;

    @Mock
    JustificativoService justificativoService;

    @Test
    void obtenerSueldosTest(){
        SueldoEntity sueldo1=new SueldoEntity("20.671.147-7","Mathew","Pardo","A",5,1000000,200000,50000,10000,1240000,124000,99200,1016800);
        SueldoEntity sueldo2=new SueldoEntity("20.927.910-3","Marcelo","Sotomayor","B",5,1000000,200000,50000,10000,1240000,124000,99200,1016800);
        SueldoEntity sueldo3=new SueldoEntity("19.231.442-3","Pedro","Jimenez","C",5,1000000,200000,50000,10000,1240000,124000,99200,1016800);
        ArrayList<SueldoEntity> sueldos=new ArrayList<>();
        sueldos.add(sueldo1);
        sueldos.add(sueldo2);
        sueldos.add(sueldo3);
        when(sueldoRepository.findAll()).thenReturn(sueldos);
        ArrayList<SueldoEntity> sueldosFinal=sueldoService.obtenerSueldos();
        Assertions.assertEquals(sueldos,sueldosFinal);
    }

    @Test
    void guardarSueldoTest(){
        SueldoEntity sueldo=new SueldoEntity();
        sueldo.setNombreEmpleado("Mathew");
        sueldo.setRut("20.671.147-7");
        sueldo.setApellidoEmpleado("Pardo");
        sueldo.setCategoriaEmpleado("A");
        sueldo.setSueldoFijo(1000000);
        sueldo.setSueldoBruto(1240000);
        sueldo.setSueldoFinal(1016800);
        sueldo.setAniosEnEmpresa(5);
        sueldo.setCotizacionPrevisional(124000);
        sueldo.setCotizacionSalud(99200);
        sueldo.setMontoBonificacion(200000);
        sueldo.setMontoDescuentos(10000);
        sueldo.setMontoHorasExtras(50000);
        when(sueldoRepository.save(any(SueldoEntity.class))).thenReturn(sueldo);
        SueldoEntity sueldoFinal=sueldoService.guardarSueldo(sueldo);
        Assertions.assertEquals(sueldo,sueldoFinal);
    }

    @Test
    void obtenerHorasExtraTest(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7", LocalDate.parse("2022-06-08"), LocalTime.parse("08:00:00"));
        RelojEntity marca2=new RelojEntity(2l,"20.671.147-7", LocalDate.parse("2022-06-08"), LocalTime.of(20,0,0));
        RelojEntity marca3=new RelojEntity(1l,"20.671.147-7", LocalDate.parse("2022-06-09"), LocalTime.parse("08:00:00"));
        RelojEntity marca4=new RelojEntity(2l,"20.671.147-7", LocalDate.parse("2022-06-09"), LocalTime.of(20,0,0));
        SolicitudEntity solicitud=new SolicitudEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),"Recursos Humanos");
        SolicitudEntity solicitud2=new SolicitudEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-09"),"Recursos Humanos");
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        ArrayList<SolicitudEntity> solicitudes=new ArrayList<>();
        marcas.add(marca);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);
        solicitudes.add(solicitud);
        solicitudes.add(solicitud2);
        int horasExtra= sueldoService.obtenerHorasExtra(marcas,solicitudes);
        Assertions.assertEquals(4,horasExtra);
    }

    @Test
    void sueldoHorasExtraTestA(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        int valorHora=sueldoService.sueldoHorasExtra(empleado);
        Assertions.assertEquals(25000,valorHora);
    }

    @Test
    void sueldoHorasExtraTestB(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"B");
        int valorHora=sueldoService.sueldoHorasExtra(empleado);
        Assertions.assertEquals(20000,valorHora);
    }

    @Test
    void sueldoHorasExtraTestC(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"C");
        int valorHora=sueldoService.sueldoHorasExtra(empleado);
        Assertions.assertEquals(10000,valorHora);
    }

    @Test
    void obtenerSueldoFijoTestA(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        int sueldoFijo= sueldoService.obtenerSueldoFijo(empleado);
        Assertions.assertEquals(1700000,sueldoFijo);
    }

    @Test
    void obtenerSueldoFijoTestB(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"B");
        int sueldoFijo= sueldoService.obtenerSueldoFijo(empleado);
        Assertions.assertEquals(1200000,sueldoFijo);
    }

    @Test
    void obtenerSueldoFijoTestC(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"C");
        int sueldoFijo= sueldoService.obtenerSueldoFijo(empleado);
        Assertions.assertEquals(800000,sueldoFijo);
    }

    @Test
    void obtenerAniosAntiguedadTest(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        int aniosAntiguedad=sueldoService.obtenerAniosAntiguedad(empleado);
        Assertions.assertEquals(2,aniosAntiguedad);
    }


    @Test
    void calcularBonificacionesTest25(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("1986-01-29"), Date.valueOf("1995-01-29"),"A");
        int bonificaciones=(int)sueldoService.calcularBonificaciones(empleado);
        Assertions.assertEquals(289000,bonificaciones);
    }
    @Test
    void calcularBonificacionesTest20(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("1995-01-29"), Date.valueOf("2002-01-29"),"A");
        int bonificaciones=(int)sueldoService.calcularBonificaciones(empleado);
        Assertions.assertEquals(238000,bonificaciones);
    }
    @Test
    void calcularBonificacionesTest15(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2007-01-29"),"A");
        int bonificaciones=(int)sueldoService.calcularBonificaciones(empleado);
        Assertions.assertEquals(187000,bonificaciones);
    }
    @Test
    void calcularBonificacionesTest10(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2010-01-29"),"A");
        int bonificaciones=(int)sueldoService.calcularBonificaciones(empleado);
        Assertions.assertEquals(136000,bonificaciones);
    }
    @Test
    void calcularBonificacionesTest5(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2015-01-29"),"A");
        int bonificaciones=(int)sueldoService.calcularBonificaciones(empleado);
        Assertions.assertEquals(85000,bonificaciones);
    }

    @Test
    void bonoHorasExtraTest(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7", LocalDate.parse("2022-06-08"), LocalTime.parse("08:00:00"));
        RelojEntity marca2=new RelojEntity(2l,"20.671.147-7", LocalDate.parse("2022-06-08"), LocalTime.of(20,0,0));
        SolicitudEntity solicitud=new SolicitudEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),"Recursos Humanos");
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        ArrayList<SolicitudEntity> solicitudes=new ArrayList<>();
        marcas.add(marca);
        marcas.add(marca2);
        solicitudes.add(solicitud);
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        when(relojService.obtenerMarcasEmpleado(empleado.getRut())).thenReturn(marcas);
        when(solicitudService.obtenerPorRut(empleado.getRut())).thenReturn(solicitudes);
        int bonoHorasTotal=(int)sueldoService.bonoHorasExtra(empleado);
        Assertions.assertEquals(50000,bonoHorasTotal);
    }

    @Test
    void calcularDescuentoAtrasoTest70SinJustificativo(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),LocalTime.parse("09:15:00"));
        when(justificativoService.verificarJustificativo(marca)).thenReturn(false);
        double resultado= sueldoService.calcularDescuentosAtraso(marca);
        Assertions.assertEquals(0.15,resultado);
    }

    @Test
    void calcularDescuentoAtrasoTest70ConJustificativo(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),LocalTime.parse("09:15:00"));
        when(justificativoService.verificarJustificativo(marca)).thenReturn(true);
        double resultado= sueldoService.calcularDescuentosAtraso(marca);
        Assertions.assertEquals(0.0,resultado);
    }

    @Test
    void calcularDescuentoAtrasoTest45(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),LocalTime.parse("09:00:00"));
        double resultado= sueldoService.calcularDescuentosAtraso(marca);
        Assertions.assertEquals(0.06,resultado);
    }

    @Test
    void calcularDescuentoAtrasoTest25(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),LocalTime.parse("08:35:00"));
        double resultado= sueldoService.calcularDescuentosAtraso(marca);
        Assertions.assertEquals(0.03,resultado);
    }

    @Test
    void calcularDescuentoAtrasoTest10(){
        RelojEntity marca=new RelojEntity(1l,"20.671.147-7",LocalDate.parse("2022-06-08"),LocalTime.parse("08:15:00"));
        double resultado= sueldoService.calcularDescuentosAtraso(marca);
        Assertions.assertEquals(0.01,resultado);
    }

    @Test
    void obtenerMarcasEntradaAtrasadaTest(){
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:00:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("16:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("16:00:00"));
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);
        ArrayList<RelojEntity> marcasAtrasadas=new ArrayList<>();
        marcasAtrasadas.add(marca3);
        ArrayList<RelojEntity> marcasAtrasadasFinal=sueldoService.obtenerMarcasEntradaAtrasada(marcas);
        Assertions.assertEquals(marcasAtrasadas,marcasAtrasadasFinal);
    }

    @Test
    void calcularDescuentoAtrasosTotalTest(){
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:30:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("16:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("16:00:00"));
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        String rutEmpleado="20.671.147-7";
        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);
        when(relojService.obtenerMarcasEmpleado(rutEmpleado)).thenReturn(marcas);
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        double descuentoAtrasos=sueldoService.calcularDescuentoAtrasosTotal(empleado);
        Assertions.assertEquals(0.04,descuentoAtrasos);
    }

    @Test
    void calcularDescuentoFaltasTest(){
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:30:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("18:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("18:00:00"));
        RelojEntity marcaExtra= new RelojEntity(5l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("08:00:00"));
        RelojEntity marcaExtra2= new RelojEntity(6l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("18:00:00"));
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);

        ArrayList<LocalDate> diasTrabajables=new ArrayList<>();
        diasTrabajables.add(marca1.getFecha());
        diasTrabajables.add(marca3.getFecha());
        diasTrabajables.add(marcaExtra.getFecha());
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        when(relojService.obtenerMarcasEmpleado(empleado.getRut())).thenReturn(marcas);
        when(relojService.obtenerDiasTrabajables()).thenReturn(diasTrabajables);
        when(justificativoService.verificarJustificativo(diasTrabajables.get(diasTrabajables.size()-1), empleado.getRut())).thenReturn(false);

        double descuento=sueldoService.calcularDescuentoFaltas(empleado);

        Assertions.assertEquals(0.15,descuento);
    }

    @Test
    void calcularDescuentosTotalTest(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:30:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("18:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("18:00:00"));
        RelojEntity marcaExtra= new RelojEntity(5l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("08:00:00"));
        RelojEntity marcaExtra2= new RelojEntity(6l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("18:00:00"));
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);

        ArrayList<LocalDate> diasTrabajables=new ArrayList<>();
        diasTrabajables.add(marca1.getFecha());
        diasTrabajables.add(marca3.getFecha());
        diasTrabajables.add(marcaExtra.getFecha());
        when(relojService.obtenerMarcasEmpleado(empleado.getRut())).thenReturn(marcas);
        when(relojService.obtenerDiasTrabajables()).thenReturn(diasTrabajables);
        when(justificativoService.verificarJustificativo(diasTrabajables.get(diasTrabajables.size()-1), empleado.getRut())).thenReturn(false);
        double descuentoTotal= sueldoService.calcularDescuentosTotal(empleado,1700000);
        Assertions.assertEquals(323000.0,descuentoTotal);
    }


    @Test
    void calcularSueldoBrutoTest(){
        // sueldo Base= 1700000 + Horas extra = 2*25000 - (Descuento falta= 0.15 * 1700000 + Descuento atrasos= (0.03+0.01)*1700000 = 1427000
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:30:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("18:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("20:00:00"));
        RelojEntity marcaExtra= new RelojEntity(5l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("08:00:00"));
        RelojEntity marcaExtra2= new RelojEntity(6l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("18:00:00"));
        SolicitudEntity solicitud=new SolicitudEntity(1l,"20.671.147-7",LocalDate.parse("2022-08-15"),"Recursos Humanos");
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        ArrayList<SolicitudEntity> solicitudes=new ArrayList<>();
        ArrayList<LocalDate> diasTrabajables=new ArrayList<>();

        solicitudes.add(solicitud);

        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);


        diasTrabajables.add(marca1.getFecha());
        diasTrabajables.add(marca3.getFecha());
        diasTrabajables.add(marcaExtra.getFecha());

        when(relojService.obtenerMarcasEmpleado(empleado.getRut())).thenReturn(marcas);
        when(solicitudService.obtenerPorRut(empleado.getRut())).thenReturn(solicitudes);
        when(relojService.obtenerDiasTrabajables()).thenReturn(diasTrabajables);
        when(justificativoService.verificarJustificativo(diasTrabajables.get(diasTrabajables.size()-1), empleado.getRut())).thenReturn(false);
        double sueldoBruto=sueldoService.calcularSueldoBruto(empleado);
        Assertions.assertEquals(1427000,sueldoBruto);

    }

    @Test
    void cotizacionPrevisionalTest(){
        int sueldoBruto=1240000;
        double cotPrevisional= sueldoService.cotizacionPrevisional(sueldoBruto);
        Assertions.assertEquals(124000,cotPrevisional);
    }

    @Test
    void cotizacionSaludTest(){
        int sueldoBruto=1240000;
        double cotSalud= sueldoService.cotizacionSalud(sueldoBruto);
        Assertions.assertEquals(99200,cotSalud);
    }

    @Test
    void calcularSueldoFinalTest(){
        EmpleadoEntity empleado=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        RelojEntity marca1 = new RelojEntity(1l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("08:30:00"));
        RelojEntity marca2 = new RelojEntity(2l, "20.671.147-7", LocalDate.parse("2022-08-14"), LocalTime.parse("18:00:00"));
        RelojEntity marca3 = new RelojEntity(3l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("08:15:00"));
        RelojEntity marca4 = new RelojEntity(4l, "20.671.147-7", LocalDate.parse("2022-08-15"), LocalTime.parse("20:00:00"));
        RelojEntity marcaExtra= new RelojEntity(5l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("08:00:00"));
        RelojEntity marcaExtra2= new RelojEntity(6l,"20.547.423-0",LocalDate.parse("2022-08-16"),LocalTime.parse("18:00:00"));
        SolicitudEntity solicitud=new SolicitudEntity(1l,"20.671.147-7",LocalDate.parse("2022-08-15"),"Recursos Humanos");
        ArrayList<RelojEntity> marcas=new ArrayList<>();
        ArrayList<SolicitudEntity> solicitudes=new ArrayList<>();
        ArrayList<LocalDate> diasTrabajables=new ArrayList<>();

        solicitudes.add(solicitud);

        marcas.add(marca1);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);


        diasTrabajables.add(marca1.getFecha());
        diasTrabajables.add(marca3.getFecha());
        diasTrabajables.add(marcaExtra.getFecha());

        when(relojService.obtenerMarcasEmpleado(empleado.getRut())).thenReturn(marcas);
        when(solicitudService.obtenerPorRut(empleado.getRut())).thenReturn(solicitudes);
        when(relojService.obtenerDiasTrabajables()).thenReturn(diasTrabajables);
        when(justificativoService.verificarJustificativo(diasTrabajables.get(diasTrabajables.size()-1), empleado.getRut())).thenReturn(false);

        double sueldoFinal= sueldoService.calcularSueldoFinal(empleado);
        Assertions.assertEquals(1170140,sueldoFinal);
    }

    @Test

    void calcularPlanillaTest(){

    }
}
