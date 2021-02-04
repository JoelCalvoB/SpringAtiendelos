/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.TipoProducto;
import com.entrecodigos.repositorios.sucursales;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author i7
 */
@Transactional
@Component("TipoProductoDaoImp")
public class TipoProductoDaoImp {
    @Autowired
        JdbcTemplate jdbcTemplate;

    
    public Object getTipoProducto(){
    String query="select * from tipo_producto";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
            return resultado;
    }
    public Object eliminarTipoProducto(String id){
         String query = "delete from tipo_producto where id_tipoproducto = " + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);   
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }
    
public Object insertarTipoProducto(TipoProducto p) {
        String query = "insert into tipo_producto (descripcion,nombre) values (?,?)";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getDescripcion(), p.getNombre()});
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

  public Object actualizaTipoProducto(TipoProducto p) {
        String query = "update tipo_producto set descripcion=?,nombre=? where id_tipoproducto=?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getDescripcion(), p.getNombre(), p.getId_tipoproducto()});
            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
  }
}
