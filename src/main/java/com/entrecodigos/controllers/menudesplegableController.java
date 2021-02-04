/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.menuDesplegableDaoImp;
import com.entrecodigos.repositorios.menuDesplegable;
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

/**
 *
 * @author samv
 */

@RestController
@RequestMapping("/catalogos")
public class menudesplegableController {
    
    @Autowired
    @Qualifier("objMenuDesplegableDao")
    private menuDesplegableDaoImp obj;
    
    
    @GetMapping("/{empresa}/menudesplegable")
    public Object getMenu(@PathVariable("empresa")String nombreEmpresa){
      return obj.getMenu(nombreEmpresa);
    }
    
    @PostMapping("/{empresa}/menudesplegable")
    public Object setMenu(@PathVariable("empresa")String nombreEmpresa,@RequestBody menuDesplegable menu){
      return obj.setMenu(menu, nombreEmpresa);
    }
    
    @PutMapping("/{empresa}/menudesplegable")
    public Object updateMenu(@PathVariable("empresa")String nombreEmpresa,@RequestBody menuDesplegable menu){
      return obj.updateMenu(menu, nombreEmpresa);
    }
    
    
    @DeleteMapping("/{empresa}/menudesplegable/{id_menu}")
    public Object deleteMenu(@PathVariable("empresa")String nombreEmpresa,@PathVariable("id_menu")int id_menu){
      return obj.deleteMenu(id_menu, nombreEmpresa);
    }
    
}
