/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.CombosDaoImp;
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
import com.entrecodigos.repositorios.Combos;

/**
 *
 * @author i7
 */
@RestController
@RequestMapping("/catalogos")
public class catalogosCombosController {

    @Autowired
    @Qualifier("objCombos")

    private CombosDaoImp obj;

    @GetMapping("/combos")
    public Object getCombos() {
        return obj.getCombos();
    }

    @GetMapping("/combos/detalle/{id}")
    public Object getCombosDetalle(@PathVariable("id")String id) {
        return obj.getComboDetalle(id);
    }

    @DeleteMapping("/combos/{identificador}")
    public Object eliminarProducto(@PathVariable("identificador") String id) {
        return obj.EliminaCombo(id);
    }

    @PutMapping("/combos")
    public Object Actualiza(@RequestBody Combos p) {
        return obj.ActualizaCombo(p);
    }

    @PostMapping("/combos")
    public Object Inserta(@RequestBody Combos p) {
        return obj.InsertarCombo(p);
    }
}
