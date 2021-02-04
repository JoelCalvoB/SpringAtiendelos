/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("objEstadisticaDao")
public class EstadisticaDaoImp {
    
    
     @Autowired
    JdbcTemplate jdbcTemplate;
     
     public Object PorductoMasVendido( String FechaI,String FechaF,String id_sucursal, String nombreEmpresa){
       String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
         
         String query="SELECT a2.nombre,  COUNT (a1.id_producto) as conteo FROM historico_detalletickets a1 LEFT JOIN productos a2 ON a1.id_producto = a2.id_producto LEFt Join historico_tickets  a3 ON a1.id_ticket=a3.id_ticket  where a3.id_empresa="+idEmpresa+" and a3.fecha between '"+FechaI+"' and '"+FechaF+"' GROUP BY a1.id_producto,a2.nombre order by conteo desc limit 5 ;";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
     }
     public Object VentasPorMesero ( String FechaI,String FechaF,String id_sucursal,String nombreEmpresa){
           String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
         
         String query="SELECT max(a3.nombre) as nombre, sum(a1.total) as total, count(a1.id_user) as n_notal FROM historico_tickets a1 LEFT JOIN users a3 ON a1.id_user = a3. ID WHERE a1.id_empresa ="+idEmpresa+" AND a3.id_empresa="+idEmpresa+" AND a1.cancelado <>'t' AND a1.fecha between '"+FechaI+"' and '"+FechaF+"' GROUP BY a1.id_user";
         List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
     }
     
     public Object VentasPorMes(String nombreEmpresa){
                  String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
         
         String query="SELECT COUNT (*) AS contador, sum(total), EXTRACT(YEAR FROM fecha) AS anno, EXTRACT(MONTH FROM fecha) AS mes FROM historico_tickets WHERE EXTRACT (YEAR FROM fecha) = extract(YEAR from current_date) and cancelado<>'t' and id_empresa="+idEmpresa+" GROUP BY EXTRACT(YEAR FROM fecha),  EXTRACT(MONTH FROM fecha ) ORDER BY EXTRACT(YEAR FROM fecha),  EXTRACT(MONTH FROM fecha )";
          List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
     }
}
