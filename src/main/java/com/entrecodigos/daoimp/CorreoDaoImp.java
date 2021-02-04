/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.correo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author i7
 */
@Transactional
@Component("objCorreos")

public class CorreoDaoImp {
      @Autowired
    JdbcTemplate jdbcTemplate;
     
     public Object getCorreo(correo p){
    String query="select * from correo where correo like '%?%' limit 10";
         List<Map<String,Object>> resultado= jdbcTemplate.queryForList(query,new Object[]{p.getCorreo()});
         return resultado;
}
     
     public Object InsertaCorreo(correo p){
         String query="Insert into correo values (?)";
         Map<String,Object> resultado= new HashMap<String, Object>();
                 try
                 {
                     jdbcTemplate.update(query,new Object[]{p.getCorreo()});
                 }
                 catch(Exception e)
                 {
                     
                 }
                 return resultado;
     }
    
    
}
