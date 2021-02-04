/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.Detalle_ticketDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.entrecodigos.repositorios.Detalle_Tickets;


@RestController
@RequestMapping("/catalogos")

public class catalogosControllerDetalle_ticket {
    
    @Autowired
    @Qualifier("objDetalle_ticket")
   
    
   private Detalle_ticketDaoImp obj;
    
       @PostMapping("/detalle_ticket")
       public Object insertadetalleticket(@RequestBody Detalle_Tickets p){
        return obj.InsertarDetalle(p);
    }
}