package com.entrecodigos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entrecodigos.daoimp.configuracionesDaoImp;
import com.entrecodigos.repositorios.configuracion;

@RestController
@RequestMapping("/api")
public class configuracionController {
   
	
	@Autowired
	private configuracionesDaoImp obj;
   
	@GetMapping("/{nombreEmpresa}/configuracion")
   public configuracion getConfiguracion(@PathVariable("nombreEmpresa") String nombreEmpresa,@RequestParam("idsucursal") Long id_sucursal) {
	   return obj.getConfiguracionRestaurante(nombreEmpresa, id_sucursal);
   }
	
	@PostMapping("/{nombreEmpresa}/configuracion")
   public configuracion insertarConfiguracion(@PathVariable("nombreEmpresa")String nombreEmpresa,@RequestBody configuracion confi) {
	   return this.obj.setConfiguracion(nombreEmpresa,confi);
   }
}
