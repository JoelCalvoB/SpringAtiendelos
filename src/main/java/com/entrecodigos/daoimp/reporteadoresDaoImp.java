/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.controllers.reporteadorController;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author samv
 */
@Component("reporteadoresDao")
public class reporteadoresDaoImp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    public Object reporteHitoricoTickets(int idSucursal,String nombreEmpresa) throws IOException, InterruptedException {
        
           String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("idsucursal", idSucursal);
            
            InputStream is = resourceLoader.getResource("classpath:historico_tickets.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
    
    public Object reporteHitoricoTicketsPorFecha(int idSucursal,Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
        
           String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("idsucursal", idSucursal);
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa",idEmpresa);
            
            InputStream is = resourceLoader.getResource("classpath:ventas_porfecha.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
    
     public Object reporteHitoricoTicketsPorFechaMesas(int idSucursal,Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
           String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
         
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("idsucursal", idSucursal);
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa", idEmpresa);
            
            
            InputStream is = resourceLoader.getResource("classpath:ventas_pormesa.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
     
      public Object reporteHitoricoTicketsPorFechaMeseros(int idSucursal,Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
          String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
          
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("idsucursal", idSucursal);
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa", idEmpresa);
            
            InputStream is = resourceLoader.getResource("classpath:ventas_pormesero.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
      
       public Object reporteHitoricoSucursales(Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
                  String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
           
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa",idEmpresa);
            
            InputStream is = resourceLoader.getResource("classpath:ventas_sucursal.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
       
       
       public Object reporteHitoricoTicketsPorFechaBarraCocina(int idSucursal,Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
                String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
           
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("idsucursal", idSucursal);
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa",idEmpresa);
            
            InputStream is = resourceLoader.getResource("classpath:ventas_barracocina.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
       
       
         public Object reporteHitoricoTicketsDetalle(int idSucursal,Date f1,Date f2,String nombreEmpresa) throws IOException, InterruptedException {
                String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
           
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("id_sucursal", idSucursal);
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idempresa",idEmpresa);
            
            InputStream is = resourceLoader.getResource("classpath:detalle_venta.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
         
         
         
         
     
         
         
         
         
         
       
       public Object reporteinventariosporproducto(int idSucursal,Date f1,Date f2) throws IOException, InterruptedException {
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idsucursal", idSucursal);
            
            InputStream is = resourceLoader.getResource("classpath:inventarios_productos.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
       
       public Object reporteinventariosporinsumos(int idSucursal,Date f1,Date f2) throws IOException, InterruptedException {
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("fecha1", f1);
            parametros.put("fecha2", f2);
            parametros.put("idsucursal", idSucursal);
            
            InputStream is = resourceLoader.getResource("classpath:inventarios_porinsumos.jasper").getInputStream();
            

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
    }
       
       
       public Object reporteInventarioCorte(int idCorte,String nombreEmpresa) throws IOException, InterruptedException{
             String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            System.out.println(conn.isClosed());
            parametros.put("id_corte", idCorte);
            
            InputStream is = resourceLoader.getResource("classpath:reporte_corte.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
        
       }
       
       
       
       public Object enseñando1(int idSucursal,String nombreEmpresa) throws InterruptedException, IOException{
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
           
        
        
         
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            
            Map<String,Object> parametros = new HashMap<String,Object>();
            parametros.put("idSucursal",idSucursal);
            
            
            InputStream is = resourceLoader.getResource("classpath:enseñando2.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper,parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        return obj;
           
            
       }

    
       
           public Object ReporteEspecialCorte(int id_sucursal,Date f1,String nombreEmpresa) throws IOException, InterruptedException {
        
           String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        String ruta = "";
        Connection conn = null;
        try {
            conn = this.jdbcTemplate.getDataSource().getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("id_sucursal", id_sucursal);
            parametros.put("fecha1", f1);
            
            InputStream is = resourceLoader.getResource("classpath:corte_especial.jasper").getInputStream();

            JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

            
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
            

            conn.close();
            Thread.sleep(3000);
            
               String query ="update historico_corte_especial set generado=true where generado is null;";
        jdbcTemplate.update(query);
                
            return print;
        } catch (JRException ex) {
            Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("respuesta", "/reportes/reporteador.pdf");
        
     
        return obj;
    }
           
           
           public Object reportecortecaja(int id_sucursal,String nombreEmpresa,Date f1,Date f2) throws IOException, InterruptedException {
               
               String queryempresa = "select id from empresas where usuario = '%1$s'";
            queryempresa = String.format(queryempresa, nombreEmpresa);
            Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
            String ruta = "";
            Connection conn = null;
            try {
                conn = this.jdbcTemplate.getDataSource().getConnection();
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("id_empresa", idEmpresa);
                parametros.put("id_sucursal", id_sucursal);
                parametros.put("fecha1", f1);
                parametros.put("fecha2", f2);
                
                InputStream is = resourceLoader.getResource("classpath:reporte_cortecaja.jasper").getInputStream();

                JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

                
                JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
                

                conn.close();                    
                return print;
            } catch (JRException ex) {
                Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            }

            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("respuesta", "/reportes/reporteador.pdf");
            
         
            return obj;
        }








public Object reporteproducto(int id_sucursal,String nombreEmpresa,Date f1,Date f2) throws IOException, InterruptedException {
    
    String queryempresa = "select id from empresas where usuario = '%1$s'";
 queryempresa = String.format(queryempresa, nombreEmpresa);
 Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
 int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
 String ruta = "";
 Connection conn = null;
 try {
     conn = this.jdbcTemplate.getDataSource().getConnection();
     Map<String, Object> parametros = new HashMap<String, Object>();
     parametros.put("id_empresa", idEmpresa);
     parametros.put("id_sucursal", id_sucursal);
     parametros.put("fecha1", f1);
     parametros.put("fecha2", f2);
     
     InputStream is = resourceLoader.getResource("classpath:venta_producto.jasper").getInputStream();

     JasperReport jasper = (JasperReport) JRLoader.loadObject(is);

     
     JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conn);
     

     conn.close();                    
     return print;
 } catch (JRException ex) {
     Logger.getLogger(reporteadorController.class.getName()).log(Level.SEVERE, null, ex);
 } catch (SQLException ex) {
     Logger.getLogger(reporteadoresDaoImp.class.getName()).log(Level.SEVERE, null, ex);
 }

 Map<String, Object> obj = new HashMap<String, Object>();
 obj.put("respuesta", "/reportes/reporteador.pdf");
 

 return obj;
}
}
