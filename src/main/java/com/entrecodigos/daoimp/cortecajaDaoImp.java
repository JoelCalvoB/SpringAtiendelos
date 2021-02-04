/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.corteCaja;
import com.entrecodigos.repositorios.gastos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samv
 */
@Transactional
@Component("cortecajaDao")
public class cortecajaDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object total(int idsucursal, Date fecha) {
        Object resultado = null;
        try {

            String query = "select tipo_pago,sum(total) as total from historico_tickets where fecha = ? and id_sucursal = ? group by tipo_pago";
            resultado = this.jdbcTemplate.queryForList(query, new Object[]{fecha, idsucursal});

        } catch (Exception e) {
            Map<String, Object> aux = new HashMap<String, Object>();
            aux.put("resultado", "Error al consultar la tabla");
            resultado = aux;
        }

        return resultado;
    }

    public Object getGastos(int id_sucursal, Date fecha) {
        String query = "select * from gastos where id_sucursal = ? and fecha = ? order by id";
        return this.jdbcTemplate.queryForList(query, new Object[]{id_sucursal, fecha});
    }

    public Object getGastosSinCorte(int id_sucursal, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from gastos where id_sucursal = %1$s and id_cortecaja is null and id_empresa = %2$s";
        query = String.format(query, id_sucursal,idEmpresa);
        return this.jdbcTemplate.queryForList(query);
    }

    public Object getEntradasSinCorte(int id_sucursal,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select * from entradas where id_sucursal = %1$s and id_cortecaja is null and id_empresa = %2$s";
        query = String.format(query, id_sucursal,idEmpresa);
        return this.jdbcTemplate.queryForList(query);
    }

    public Object getGastosConCorte(int idsucursal, int id_corte,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select * from gastos where id_sucursal = %1$s and id_cortecaja = %2$s and id_empresa = %3$s";
        query = String.format(query, idsucursal, id_corte,idEmpresa);
        return this.jdbcTemplate.queryForList(query);
    }

    public Object getEntradasConCorte(int idsucursal, int id_corte,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select * from entradas where id_sucursal = %1$s and id_cortecaja = %2$s and id_empresa = %3$s";
        query = String.format(query, idsucursal, id_corte,idEmpresa);
        return this.jdbcTemplate.queryForList(query);
    }

    public Object setGastos(List<gastos> obj, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into gastos(nombre,total,id_sucursal,id_usuario,id_empresa) values ('%1$s',%2$s,%3$s,%4$s,%5$s);";
        Map<String, Object> resultado = new HashMap<String, Object>();

        int id_sucursal = 0;
        if (!obj.isEmpty()) {
            id_sucursal = obj.get(0).getIdSucursal();
        }

        String auxLocal = "delete from gastos where id_sucursal = %1$s and id_cortecaja is null and id_empresa = %2$s";
        auxLocal = String.format(auxLocal, id_sucursal, idEmpresa);
        jdbcTemplate.update(auxLocal);
        auxLocal = "";
        for (gastos item : obj) {
            String aux = String.format(query, item.getNombre(), item.getTotal(), item.getIdSucursal(), item.getIdUsuario(), idEmpresa);
            auxLocal += aux;
        }

        if (!auxLocal.equalsIgnoreCase("")) {
            jdbcTemplate.update(auxLocal);
        }
        resultado.put("resultado", "Gastos ingresados correctamente");

        return resultado;
    }

    public Object setEntrada(List<gastos> obj, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into entradas(nombre,total,id_sucursal,id_usuario,id_empresa) values ('%1$s',%2$s,%3$s,%4$s,%5$s);";
        Map<String, Object> resultado = new HashMap<String, Object>();

        int id_sucursal = 0;
        if (!obj.isEmpty()) {
            id_sucursal = obj.get(0).getIdSucursal();
        }

        String auxLocal = "delete from entradas where id_sucursal = %1$s and id_cortecaja is null and id_empresa = %2$s";
        auxLocal = String.format(auxLocal, id_sucursal, idEmpresa);
        jdbcTemplate.update(auxLocal);
        auxLocal = "";
        for (gastos item : obj) {
            String aux = String.format(query, item.getNombre(), item.getTotal(), item.getIdSucursal(), item.getIdUsuario(), idEmpresa);
            auxLocal += aux;
        }

        if (!auxLocal.equalsIgnoreCase("")) {
            jdbcTemplate.update(auxLocal);
        }
        resultado.put("resultado", "Gastos ingresados correctamente");

        return resultado;
    }

    public Object insertarCorteCaja(corteCaja corte, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Map<String, Object> resultado = new HashMap<String, Object>();

        String query = "insert into corte_caja(fecha,hora,total,id_sucursal,efectivo_apertura,cerrada,id_empresa)values((now() AT TIME ZONE 'America/Mexico_City'),((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,%1$s,%2$s,false,%3$s)";
        query = String.format(query, corte.getId_sucursal(), corte.getEfectivo_apertura(), idEmpresa);
        jdbcTemplate.update(query);

        resultado.put("respuesta", "Registro insertado con exito");
        return resultado;
    }

    public Object getCortesCaja(int id_sucursal, int offset, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from corte_caja where id_sucursal = %1$s and id_empresa = %3$s order by fecha desc,hora desc OFFSET %2$s limit 10";
        query = String.format(query, id_sucursal, offset, idEmpresa);
        return jdbcTemplate.queryForList(query);
    }

    public Object getCorteCajaSinCorte(int id_sucursal, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select tipo_pago,sum(total) as total,sum(tarjeta) as tarjeta from historico_tickets where id_sucursal = %1$s and id_cortecaja is null and id_empresa = %2$s group by tipo_pago";
        query = String.format(query, id_sucursal, idEmpresa);
        return jdbcTemplate.queryForList(query);
    }

    public Object getCorteCajaConCorte(int id_sucursal, int id_corte,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select tipo_pago,sum(total) as total,sum(tarjeta) as tarjeta from historico_tickets where id_sucursal = %1$s and id_cortecaja = %2$s and id_empresa = %3$s group by tipo_pago";
        query = String.format(query, id_sucursal, id_corte,idEmpresa);
        return jdbcTemplate.queryForList(query);
    }

    public Object realizarCorteCaja(corteCaja corte,String nombreEmpresa) {
        
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "update historico_tickets set id_cortecaja = %1$s where id_sucursal = %2$s and id_cortecaja is null and id_empresa = %3$s";
        query = String.format(query, corte.getId(), corte.getId_sucursal(),idEmpresa);
        jdbcTemplate.update(query);

        query = "update gastos set id_cortecaja = %1$s where id_sucursal = %2$s and id_cortecaja is null and id_empresa = %3$s";
        query = String.format(query, corte.getId(), corte.getId_sucursal(),idEmpresa);
        jdbcTemplate.update(query);

        query = "update entradas set id_cortecaja = %1$s where id_sucursal = %2$s and id_cortecaja is null and id_empresa = %3$s";
        query = String.format(query, corte.getId(), corte.getId_sucursal(),idEmpresa);
        jdbcTemplate.update(query);

        query = "update corte_caja set cerrada = true where id = %1$s and id_sucursal = %2$s and id_empresa = %3$s";
        query = String.format(query, corte.getId(), corte.getId_sucursal(),idEmpresa);
        jdbcTemplate.update(query);

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("respuesta", "Corte de caja realizada con exito");
        return respuesta;
    }

}
