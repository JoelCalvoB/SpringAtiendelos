package com.entrecodigos.funciones;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class empresaPropiedades {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int obteniendoEmpresa(String nombreEmpresa) {
		String queryempresa = "select id from empresas where usuario = '%1$s'";
		queryempresa = String.format(queryempresa, nombreEmpresa);
		Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
		int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
		return idEmpresa;
	}

}
