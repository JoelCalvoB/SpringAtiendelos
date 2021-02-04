/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.entrecodigos.repositorios.Combos;
import com.entrecodigos.repositorios.Detalle_combo;

/**
 *
 * @author i7
 */
@Transactional
@Component("objCombos")
public class CombosDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getCombos() {
        String query = "select * from combos";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        return resultado;
    }

    public Object EliminaCombo(String id) {
        String query = "delete from combos where id_combo=" + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            //Se elimina el maestro
            jdbcTemplate.update(query);
            //Se elimina igual el detalle
            query = "delete from detalle_combos where id_combo = " + id;
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object InsertarCombo(Combos p) {
        String query = "Insert Into combos (nombre,descripcion,precio) values ('" + p.getNombre() + "','" + p.getDescripcion() + "'," + p.getPrecio() + ");";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);

            query = "select COALESCE(max(id_combo),0)  as id_combo from combos";
            Map<String, Object> maximo = this.jdbcTemplate.queryForMap(query);
            String maximoIdCombo = maximo.get("id_combo").toString();

            int intfolio = Integer.parseInt(maximoIdCombo);

            for (Detalle_combo item : p.getDetalle()) {
                String query2 = "insert into detalle_combos values(" + maximoIdCombo + "," + item.getId_producto() + "," + item.getCantidad() + ");";
                jdbcTemplate.update(query2);
            }
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object ActualizaCombo(Combos p) {
        String query = "Update combos set nombre=? , descripcion=? , precio =? where id_combo=?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getId_combo()});
            
            query = " delete from detalle_combos where id_combo = "+p.getId_combo();
            jdbcTemplate.update(query);
            
             for (Detalle_combo item : p.getDetalle()) {
                String query2 = "insert into detalle_combos values(" + p.getId_combo() + "," + item.getId_producto() + "," + item.getCantidad() + ");";
                jdbcTemplate.update(query2);
            }
            
            
            resultado.put("respuesta", "Registro Actualizado");

        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");
        }
        return resultado;
    }

    public Object getComboDetalle(String id) {
        String query = "select detalle.id_combo,detalle.id_productos as id_producto,detalle.cantidad,p.nombre as nombre_producto,c.nombre as nombre_categoria from detalle_combos detalle "+
                       " inner join productos p on p.id_producto = detalle.id_productos "+
                       " inner join categoria c on c.id = p.id_categoria "+
                       " where detalle.id_combo =  "+id;
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        return resultado;
    }
}
