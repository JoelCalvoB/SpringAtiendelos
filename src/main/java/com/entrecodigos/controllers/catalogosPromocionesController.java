/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.PromocionesDaoImp;
import com.entrecodigos.repositorios.Promociones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogos")
public class catalogosPromocionesController {
    
    @Autowired
    @Qualifier("objPromosDao")
    private PromocionesDaoImp obj;

  
   @GetMapping("/promociones")
    public Object getPromociones() {
        return obj.getPromociones();
    }
    
     @DeleteMapping("/promociones/{identificador}")
    public Object eliminarPromos(@PathVariable("identificador") String id) {
        return obj.eliminarPromociones(id);
    }
    
    @PutMapping("/promociones")
    public Object ActualizaPromociones(@RequestBody Promociones p){
        return obj.ActualizaPromociones(p);
    }
    
     @PostMapping("/promociones")
    public Object InsertarPromocion(@RequestBody Promociones p) {
        return obj.InsertarPromocion(p);

    }
}
