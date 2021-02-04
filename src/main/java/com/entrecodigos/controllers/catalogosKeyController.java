/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.KeyDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.entrecodigos.repositorios.Key;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


/**
 *
 * @author i7
 */
@RestController
@RequestMapping("/catalogos")
public class catalogosKeyController {
     @Autowired
    @Qualifier("objkey")
     
     private KeyDaoImp obj;
     
     @GetMapping("/{empresa}/key/key")
     public Object getKey(@PathVariable("empresa")String nombreEmpresa){
         return  obj.getKey(nombreEmpresa);
     }
     
     
     @PostMapping("/{empresa}/key")
     public Object setKey(@PathVariable("empresa")String nombreEmpresa){
     return  obj.insertarLlave(nombreEmpresa);
     }
     
     
     @PutMapping("/{empresa}/key/keyactualiza")
     public Object ActualizaKey(@PathVariable("empresa")String nombreEmpresa) throws InterruptedException{
     return obj.ActualizaKey(nombreEmpresa);
     }
}