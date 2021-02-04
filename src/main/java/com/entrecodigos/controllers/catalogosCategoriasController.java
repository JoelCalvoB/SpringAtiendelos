/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.categoriasDaoImp;
import com.entrecodigos.repositorios.categorias;
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
public class catalogosCategoriasController {
    @Autowired
    @Qualifier("objCategoriasDao")
    private categoriasDaoImp obj;
    
    @GetMapping("/{empresa}/categorias")
    public Object getCategorias(@PathVariable("empresa")String nombreEmpresa) {
        return obj.getCategorias(nombreEmpresa);
    }

    @GetMapping("/{empresa}/categorias/{identificador}")
    public Object getCarritoUnico(@PathVariable("identificador") String id,@PathVariable("empresa")String nombreEmpresa) {
        return obj.getCategoriaEspecifico(id,nombreEmpresa);
    }

    @DeleteMapping("/{empresa}/categorias/{identificador}")
    public Object eliminar(@PathVariable("identificador") String id,@PathVariable("empresa")String nombreEmpresa) {
        return obj.eliminar(id,nombreEmpresa);
    }

    @PutMapping("/{empresa}/categorias")
    public Object Actualiza(@RequestBody categorias p,@PathVariable("empresa")String nombreEmpresa) {
        return obj.actualiza(p,nombreEmpresa);

    }

    @PostMapping("/{empresa}/categorias")
    public Object insertarNuevo(@RequestBody categorias p,@PathVariable("empresa")String nombreEmpresa) {
        return obj.insertar(p,nombreEmpresa);

    }

}
