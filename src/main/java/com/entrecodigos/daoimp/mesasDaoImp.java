/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.mesas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samv
 */
@Transactional
@Component("objMesasDao")
public class mesasDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object gets(String id,String nombreEmpresa) {
        
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        int id_sucursal = Integer.parseInt(id);
        String query = "select * from mesas where id_sucursal = ? and id_empresa = ? order by id_mesa";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query, new Object[]{id_sucursal,idEmpresa});
        return resultado;
    }
    public Object gets(String id,boolean activo,String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        int id_sucursal = Integer.parseInt(id);
        String query = "select * from mesas where id_sucursal = ? and ocupada = ? and id_empresa = ? order by id_mesa";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query, new Object[]{id_sucursal,activo,idEmpresa});
        return resultado;
    }
    
        public Object gets(String id,boolean activo,int id_usuario,String nombreEmpresa) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
            
            
        int id_sucursal = Integer.parseInt(id);
        String query = "select mesas.* from tickets inner join mesas on mesas.id_mesa = tickets.id_mesa "+
                " where mesas.ocupada = ? and mesas.id_sucursal = ? and tickets.id_user = ? and id_empresa = ? order by mesas.id_mesa ";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query, new Object[]{activo,id_sucursal,id_usuario,idEmpresa});
        return resultado;
    }

    public Object getEspecifico(String id_sucursal, String id_mesa, String nombreEmpresa) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        Object resultado;
        try {
            int idInt = Integer.parseInt(id_mesa);
            int idSucursal = Integer.parseInt(id_sucursal);
            String query = "SELECT * FROM mesas where id_mesa=? and id_sucursal = ? and id_empresa = ?";
            resultado = jdbcTemplate.queryForMap(query, new Object[]{idInt, idSucursal,idEmpresa});
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object insertar(mesas p,String nombreEmpresa) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "insert into mesas (id_mesa,id_tipo,id_sucursal,ocupada,id_empresa) values (?,?,?,false,?)";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getId_mesa(), p.getId_tipo(), p.getId_sucursal(),idEmpresa});
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object actualizar(mesas p,String nombreEmpresa) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "update mesas set id_tipo=?,ocupada = ? where id_mesa=? and id_sucursal=? and id_empresa = ?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getId_tipo(),p.isOcupada(), p.getId_mesa(), p.getId_sucursal(),idEmpresa});
            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
    }

    public Object eliminar(String id_sucursal, String id_mesa,String nombreEmpresa ) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "delete from mesas where id_mesa = " + id_sucursal + " and id_sucursal = " + id_mesa+" and id_empresa = "+idEmpresa;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object getsResumidos(String id,String siguiente,String nombreEmpresa) {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select id_mesa,id_tipo from mesas where id_sucursal = %1$s and ocupada = false and id_empresa = %2$s order by id_mesa limit 20 offset "+siguiente;
        query = String.format(query,id,idEmpresa);
        return jdbcTemplate.queryForList(query);
    }

}
