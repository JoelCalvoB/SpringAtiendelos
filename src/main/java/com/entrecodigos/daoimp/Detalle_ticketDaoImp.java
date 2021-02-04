/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.entrecodigos.repositorios.Detalle_Tickets;


@Transactional
@Component("objDetalle_ticket")
public class Detalle_ticketDaoImp {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public Object InsertarDetalle(Detalle_Tickets p){
        String query="insert into detalle_ticket (id_ticket,id_producto,cantidad,servido,tipo_pago) values (?,?,?,?,?)";
      boolean A1 = false;
         Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getId_ticket(), p.getId_producto(),p.getCantidad(),A1,p.getTipo_producto()});
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }
}
