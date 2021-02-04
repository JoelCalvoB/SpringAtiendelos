/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.sucursales;
import com.entrecodigos.repositorios.users;
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
@Component("objSucursalesDao")
public class sucursalesImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getSucursal(String nombre) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombre);

        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);

        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from sucursales where id_empresa = " + idEmpresa;
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getSucursalEspecifico(String id, String nombre) {
        Object resultado;
        try {
            String query = "select id from empresas where usuario = '%1$s'";
            query = String.format(query, nombre);

            Map<String, Object> empresa = jdbcTemplate.queryForMap(query);

            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

            int idInt = Integer.parseInt(id);
            query = "select * from sucursales where id=?  and id_empresa = ?";
            resultado = jdbcTemplate.queryForMap(query, new Object[]{idInt, idEmpresa});
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object eliminarSucursal(String id, String nombre) {
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            String query = "select id from empresas where usuario = '%1$s'";
            query = String.format(query, nombre);

            Map<String, Object> empresa = jdbcTemplate.queryForMap(query);

            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
            query = "delete from sucursales where id = " + id + " and id_empresa=" + idEmpresa;
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object insertarSucursal(sucursales p, String nombre) {
        Map<String, Object> resultado = new HashMap<String, Object>();

        try {
            String query = "select id from empresas where usuario = '%1$s'";
            query = String.format(query, nombre);

            Map<String, Object> empresa = jdbcTemplate.queryForMap(query);

            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

            query = "insert into sucursales (direccion,nombre,id_empresa) values (upper(?),upper(?),?)";
            resultado = new HashMap<String, Object>();

            jdbcTemplate.update(query, new Object[]{p.getDireccion(), p.getNombre(), idEmpresa});
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object actualizarSucursal(sucursales p, String nombre) {

        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            String query = "select id from empresas where usuario = '%1$s'";
            query = String.format(query, nombre);

            Map<String, Object> empresa = jdbcTemplate.queryForMap(query);

            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

            query = "update sucursales set direccion=?,nombre=? where id=? and id_empresa = ?";
            jdbcTemplate.update(query, new Object[]{p.getDireccion(), p.getNombre(), p.getId(),idEmpresa});
            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
    }
}
