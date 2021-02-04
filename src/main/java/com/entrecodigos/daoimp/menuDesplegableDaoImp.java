/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.menuDesplegable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samv
 */
@Transactional
@Component("objMenuDesplegableDao")
public class menuDesplegableDaoImp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int idEmpresaMethod(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        return idEmpresa;
    }

    public Object getMenu(String nombreEmpresa) {

        String query = "select * from menuDesplegable where id_empresa = %1$s";
        query = String.format(query, idEmpresaMethod(nombreEmpresa));

        List<Map<String, Object>> lista = (List<Map<String, Object>>) jdbcTemplate.queryForList(query);
        return lista;
    }

    public Object setMenu(menuDesplegable menu, String nombreEmpresa) {
        int idEmpresa = idEmpresaMethod(nombreEmpresa);

        String query = "select COALESCE(max(id_menu),0) as id_menu from menuDesplegable where id_empresa = %1$s";
        query = String.format(query, idEmpresa);

        Map<String, Object> resultadoMapa = jdbcTemplate.queryForMap(query);
        int id_menu = Integer.parseInt(String.valueOf(resultadoMapa.get("id_menu")));
        
        id_menu++;

        query = "insert into menuDesplegable values(%1$s,'%2$s',%3$s) returning id_menu,nombre ";
        query = String.format(query, id_menu, menu.getNombre(), idEmpresa);

        Map<String, Object> resultado = (Map<String, Object>) jdbcTemplate.queryForMap(query);

        return resultado;

    }

    public Object updateMenu(menuDesplegable menu, String nombreEmpresa) {
        int idEmpresa = idEmpresaMethod(nombreEmpresa);

        String query = "update menuDesplegable set nombre='%1$s' where id_empresa = %2$s and id_menu = %3$s";
        query = String.format(query, menu.getNombre(), idEmpresa, menu.getId_menu());

        int rows = jdbcTemplate.update(query);
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("resultado", "Registro actualizado correctamente");
        mapa.put("nombre", menu.getNombre());
        return mapa;
    }

    public Object deleteMenu(int id_menu, String nombreEmpresa) {
        int idEmpresa = idEmpresaMethod(nombreEmpresa);
        String query = "delete from menuDesplegable where id_menu = %1$s and id_empresa = %2$s";
        query = String.format(query, id_menu, idEmpresa);

        int rows = jdbcTemplate.update(query);
        Map<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("resultado", "Registro actualizado correctamente");
        return mapa;
    }
}
