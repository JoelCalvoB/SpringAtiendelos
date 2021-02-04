/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.Detalle_promociones;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.entrecodigos.repositorios.Promociones;

@Transactional
@Component("objPromosDao")

public class PromocionesDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getPromociones() {
        String query = "select * from promociones order by id";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        
        for(Map<String,Object> item : resultado){
            
            String dias = String.valueOf(item.get("dias"));
            String[] split = dias.split(":");
            item.remove("dias");
            item.put("dias", split);
        }
        
        return resultado;
    }

    public Object eliminarPromociones(String id_promo) {
        String query = "delete from promociones where id=" + id_promo;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object ActualizaPromociones(Promociones p) {
        String query = "update promociones set nombre=? , descripcion=?,dias = ?, inicia = ?, termina = ?, precio=?,productos=?,productoscodificado = ? where id=?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            
              String dias = "";
            for(String item : p.getDias())
                dias += item + ":";
            dias = dias.substring(0,dias.length() -1 );
            
            String productoscodificado = "";
            for(String item : p.getProductoscodificado()){
                productoscodificado += item + "-";
            }
            productoscodificado = productoscodificado.substring(0,productoscodificado.length() - 1);            
            
            jdbcTemplate.update(query, new Object[]{p.getNombre(), p.getDescripcion(), dias, p.getInicia(), p.getTermina(), p.getPrecio(),p.getProductos(),productoscodificado,p.getId()});
            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
    }

    public Object InsertarPromocion(Promociones p) {
        String query = "Insert into promociones (nombre,descripcion,dias,inicia,termina,precio,productos,productoscodificado) values (?,?,?,?,?,?,?,?) returning id";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            
            String dias = "";
            for(String item : p.getDias())
                dias += item + ":";
            dias = dias.substring(0,dias.length() -1 );
            
            String productoscodificado = "";
            for(String item : p.getProductoscodificado()){
                productoscodificado += item + "-";
            }
            productoscodificado = productoscodificado.substring(0,productoscodificado.length() - 1);
            
            
            
            Map<String, Object> mapa = jdbcTemplate.queryForMap(query, new Object[]{p.getNombre(), p.getDescripcion(), dias, p.getInicia(), p.getTermina(), p.getPrecio(),p.getProductos(),productoscodificado});
            int identificador = Integer.parseInt(String.valueOf(mapa.get("id")));

            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }
}
