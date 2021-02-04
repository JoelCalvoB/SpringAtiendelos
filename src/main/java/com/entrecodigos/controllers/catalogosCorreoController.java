/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.CorreoDaoImp;
import com.entrecodigos.repositorios.correo;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author i7
 */
@RestController
@RequestMapping("/catalogos")
public class catalogosCorreoController {

    @Autowired
    @Qualifier("objCorreos")

    private CorreoDaoImp obj;

    @GetMapping("/correos")
   public Object getCorreo(@RequestBody correo p){  
          return obj.getCorreo(p);
       
    }
   
   
   @PostMapping("/correos")
    public Object InsertaCorreo(@RequestBody correo p){
        return obj.InsertaCorreo(p);
}
}
