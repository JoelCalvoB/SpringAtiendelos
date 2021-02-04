/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.EstadisticaDaoImp;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogos")
public class catalogoEstadisticaController {
    
     @Autowired
    @Qualifier("objEstadisticaDao")
    private EstadisticaDaoImp obj;
     
    @GetMapping("/{empresa}/estadistica/productomasvendido/{FechaI}/{FechaF}/{id_sucursal}")
    public Object PorductoMasVendido(@PathVariable("FechaI") String FechaI,@PathVariable("FechaF") String FechaF,@PathVariable("id_sucursal")String id_sucursal , @PathVariable ("empresa") String empresa) {
      return   obj.PorductoMasVendido(FechaI,FechaF,id_sucursal,empresa);
    }
    
    @GetMapping("/{empresa}/estadistica/vendido/{FechaI}/{FechaF}/{id_sucursal}")
    public Object VentasPorMesero (@PathVariable("FechaI") String FechaI,@PathVariable("FechaF") String FechaF,@PathVariable("id_sucursal")String id_sucursal ,  @PathVariable ("empresa") String empresa) {
      return obj.VentasPorMesero(FechaI, FechaF, id_sucursal,empresa);

}
    
    @GetMapping("/{empresa}/estadistica/vendidopormes")
    public Object VentasPorMes ( @PathVariable ("empresa") String empresa) {
      return obj.VentasPorMes(empresa);
    }
    
}