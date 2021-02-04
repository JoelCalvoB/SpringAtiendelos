/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.reporteadoresDaoImp;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author samv
 */
@RestController
@RequestMapping("/reportes")
public class reporteadorController {

    @Autowired
    @Qualifier("reporteadoresDao")
    private reporteadoresDaoImp obj;

    @GetMapping("/{empresa}/ticket/{id_sucursal}")
    public Object tickets(@PathVariable("id_sucursal") int idsucursal,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException {
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTickets(idsucursal,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);
//        return ResponseEntity
//      .ok()
//      // Specify content type as PDF
//      .header("Content-Type", "application/pdf; charset=UTF-8")
//      // Tell browser to display PDF if it can
//      .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
//      .body(bytes); 

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/{empresa}/ticket/{id_sucursal}/pdf")
    public Object ticketsPdf(@PathVariable("id_sucursal") int idsucursal,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException {
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTickets(idsucursal,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);

//        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
//        Map<String, Object> respuesta = new HashMap<String, Object>();
//        respuesta.put("respuesta", cadenaCodificada);
//        return respuesta;
    }
    
     @GetMapping("/{empresa}/pdf/ticket/{id_sucursal}/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasPDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFecha(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }


    @GetMapping("/{empresa}/ticket/{id_sucursal}/fechas")
    public Object ticketsEntreFechas(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFecha(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

   
    @GetMapping("/{empresa}/ticket/mesas/{id_sucursal}/fechas")
    public Object ticketsEntreFechasMesa(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaMesas(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/{empresa}/pdf/ticket/mesas/{id_sucursal}/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasMesaPDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaMesas(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }

    @GetMapping("/{empresa}/ticket/meseros/{id_sucursal}/fechas")
    public Object ticketsEntreFechasMeseros(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaMeseros(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/{empresa}/pdf/ticket/meseros/{id_sucursal}/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasMeserosPDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaMeseros(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }

    @GetMapping("/{empresa}/ticket/sucursales/fechas")
    public Object ticketsEntreFechasSucursales(@RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoSucursales(date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/{empresa}/pdf/ticket/sucursales/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasSucursalesPDF(@PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoSucursales(date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + "SUCURSALES" + ".pdf\"")
                .body(bytes);
    }

    @GetMapping("/{empresa}/ticket/barracocina/{id_sucursal}/fechas")
    public Object ticketsEntreFechasBarraCocina(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaBarraCocina(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/{empresa}/pdf/ticket/barracocina/{id_sucursal}/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasBarraCocinaPDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsPorFechaBarraCocina(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }
    
    
     @GetMapping("/{empresa}/pdf/inventario/corte/{id_corte}")
    public Object invetarioCorte(@PathVariable("id_corte") int idcorte ,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {

        JasperPrint print = (JasperPrint) obj.reporteInventarioCorte(idcorte,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idcorte + ".pdf\"")
                .body(bytes);
    }
    
      @GetMapping("/{empresa}/ticket/detalle/{id_sucursal}/fechas")
    public Object ticketsEntreFechasDetalle(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsDetalle(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }
      
      
      

      
      
      

    @GetMapping("/{empresa}/pdf/ticket/detalle/{id_sucursal}/fechas/{fecha1}/{fecha2}")
    public Object ticketsEntreFechasDetallePDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteHitoricoTicketsDetalle(idsucursal, date1, date2,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }

    @GetMapping("/inventarios/productos/{id_sucursal}/fechas")
    public Object inventariosporproductos(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteinventariosporproducto(idsucursal, date1, date2);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/pdf/inventarios/productos/{id_sucursal}/fechas")
    public Object inventariosporproductosPDF(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteinventariosporproducto(idsucursal, date1, date2);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }

    @GetMapping("/inventarios/insumos/{id_sucursal}/fechas")
    public Object inventariosporinsumos(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteinventariosporinsumos(idsucursal, date1, date2);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", cadenaCodificada);
        return respuesta;
    }

    @GetMapping("/pdf/inventarios/insumos/{id_sucursal}/fechas")
    public Object inventariosporinsumosPDF(@PathVariable("id_sucursal") int idsucursal, @RequestParam String fecha1, @RequestParam String fecha2) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
        JasperPrint print = (JasperPrint) obj.reporteinventariosporinsumos(idsucursal, date1, date2);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                .body(bytes);
    }
    
    
    
      @GetMapping("/{empresa}/pdf/corteespecial/{id_sucursal}/fechas/{fecha}")
    public Object ReporteEspecialCorte(@PathVariable("id_sucursal") int id_sucursal, @PathVariable("fecha") String fecha,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        JasperPrint print = (JasperPrint) obj.ReporteEspecialCorte(id_sucursal, date1,nombreEmpresa);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + id_sucursal + ".pdf\"")
                .body(bytes);
    }
      
      
      
      @GetMapping("/{empresa}/pdf/cortecaja/{id_sucursal}/fechas/{fecha1}/{fecha2}")
      public Object cortedecajaPDF(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
          Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
          Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
          JasperPrint print = (JasperPrint) obj.reportecortecaja(idsucursal,nombreEmpresa, date1, date2);
          byte[] bytes = JasperExportManager.exportReportToPdf(print);

          return ResponseEntity
                  .ok()
                  .header("Content-Type", "application/pdf; charset=UTF-8")
                  .header("Content-Disposition", "inline; filename=\"" + idsucursal + ".pdf\"")
                  .body(bytes);
      }
      
      
      



      @GetMapping("/{empresa}/cortecaja/{id_sucursal}/fechas/{fecha1}/{fecha2}")
      public Object cortedecaja(@PathVariable("id_sucursal") int idsucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
    	  Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
          Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
          JasperPrint print = (JasperPrint) obj.reportecortecaja(idsucursal,nombreEmpresa, date1, date2);
          byte[] bytes = JasperExportManager.exportReportToPdf(print);

          String cadenaCodificada = Base64.getEncoder().encodeToString(bytes);
          Map<String, Object> respuesta = new HashMap<String, Object>();
          respuesta.put("respuesta", cadenaCodificada);
          return respuesta;
      }
      
      
      

      @GetMapping("/{empresa}/pdf/ventap/{id_sucursal}/fechas/{fecha1}/{fecha2}")
      public Object reporteproducto(@PathVariable("id_sucursal") int id_sucursal, @PathVariable("fecha1") String fecha1, @PathVariable("fecha2") String fecha2,@PathVariable("empresa")String nombreEmpresa) throws IOException, InterruptedException, JRException, ParseException {
    	  Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
          Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);
          JasperPrint print = (JasperPrint) obj.reporteproducto(id_sucursal, nombreEmpresa, date1, date2);
          byte[] bytes = JasperExportManager.exportReportToPdf(print);

          return ResponseEntity
                  .ok()
                  .header("Content-Type", "application/pdf; charset=UTF-8")
                  .header("Content-Disposition", "inline; filename=\"" + id_sucursal + ".pdf\"")
                  .body(bytes);
      }
      
}
