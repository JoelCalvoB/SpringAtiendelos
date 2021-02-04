package com.entrecodigos.daoimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrecodigos.funciones.empresaPropiedades;
import com.entrecodigos.repositorios.configuracion;
import com.entrecodigos.repositorys.configuracionRepository;

@Service
public class configuracionesDaoImp {

	@Autowired
	private configuracionRepository repositorio;
	
	@Autowired
	private empresaPropiedades empresaPropiedades;
	
	public configuracion getConfiguracionRestaurante(String nombreEmpresa, Long id_sucursal) {
		Long idEmpresa = Long.parseLong(String.valueOf(empresaPropiedades.obteniendoEmpresa(nombreEmpresa)));
		configuracion configuracion = repositorio.getConfiguracion(idEmpresa,id_sucursal);
		return configuracion;
	}
	
	public configuracion setConfiguracion(String nombreEmpresa,configuracion config) {
		Long idEmpresa = Long.parseLong(String.valueOf(empresaPropiedades.obteniendoEmpresa(nombreEmpresa)));
		config.setId_empresa(idEmpresa);
		return this.repositorio.save(config);
	}
	
}
