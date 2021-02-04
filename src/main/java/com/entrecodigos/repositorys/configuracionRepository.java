package com.entrecodigos.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.entrecodigos.repositorios.configuracion;

public interface configuracionRepository extends CrudRepository<configuracion, Long>{

	@Query("select u from configuracion u where u.id_empresa = ?1 and u.id_sucursal = ?2")
	public configuracion getConfiguracion(Long idEmpresa, Long idsucursal);
}
