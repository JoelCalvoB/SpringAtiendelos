/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.categorias;
import com.entrecodigos.repositorios.subcategoria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samv
 */
@Transactional
@Component("objCategoriasDao")
public class categoriasDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getCategorias(String nombre) {
        
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombre);

        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);

        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String query = "select * from categoria where id_empresa = %1$s order by id";
        query = String.format(query,idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        for (Map<String, Object> item : resultado) {
            String id_categoria = String.valueOf(item.get("id"));
            boolean tieneSubmenu = Boolean.valueOf(String.valueOf(item.get("submenu")));

            if (tieneSubmenu) {
                query = "select * from subcategoria where id_categoria = %1$s   order by id_subcategoria,id";
                query = String.format(query, id_categoria,nombre);
                List<Map<String, Object>> resultadoSub = jdbcTemplate.queryForList(query);

                resultadoSub = resultadosMapeo(resultadoSub);

                item.put("submenu_array", resultadoSub);
            }
        }

        return resultado;
    }

    public List<Map<String, Object>> resultadosMapeo(List<Map<String, Object>> lista) {

        String primeroStr = "";
        String primeroStrenlazado = "";
        boolean primeroBool = false;
        boolean conquery = false;

        List<Map<String, Object>> borrar = (List<Map<String, Object>>) new ArrayList<Map<String, Object>>();

        Map<String, Object> SubcategoriaObtenida = null;

        for (int x = 0; x < lista.size(); x++) {
            Map<String, Object> item = lista.get(x);

            String id = String.valueOf(item.get("submenu_enlazado"));
            if (id != "null") {
                String id_categoria = String.valueOf(item.get("id_categoria"));
                String id_subcategoria = String.valueOf(item.get("id_subcategoria"));

                String auxid_cate_sub = id_categoria + id_subcategoria;
                String aux_idEnlazado_cate_sub = id_categoria + id_subcategoria + id;

                if (!primeroBool) {
                    primeroStr = auxid_cate_sub;
                    primeroStrenlazado = aux_idEnlazado_cate_sub;
                    primeroBool = true;
                }
                boolean esTrue = true;
                while (esTrue) {
                    if (primeroStr.equals(auxid_cate_sub)) {

                        if (primeroStrenlazado.equals(aux_idEnlazado_cate_sub)) {
                            if (!conquery) {
                                String query = "select * from subcategoria where id_categoria = %1$s and id_subcategoria = %2$s and submenu_enlazado = %3$s order by id";
                                query = String.format(query, id_categoria, id_subcategoria, id);

                                List<Map<String, Object>> listaSubCategorias = jdbcTemplate.queryForList(query);
                                conquery = true;

                                for (Map<String, Object> aa : listaSubCategorias) {
                                    aa.put("submenu", false);
                                    aa.remove("submenu_enlazado");
                                }

                                for (Map<String, Object> iaux : lista) {
                                    String idiaux = String.valueOf(iaux.get("id"));
                                    if (idiaux.equals(id)) {
                                        iaux.put("submenu_array", listaSubCategorias);
                                        iaux.put("submenu", true);
                                        iaux.remove("submenu_enlazado");
                                        borrar.add(item);
                                        SubcategoriaObtenida = iaux;
                                        break;
                                    }
                                }

                            } else {
                                borrar.add(item);
                            }
                        } else {

                            List<Map<String, Object>> array = (List<Map<String, Object>>) SubcategoriaObtenida.get("submenu_array");
                            array.add(item);
                            array.get(0).put("submenu_enlazado", null);
                            SubcategoriaObtenida.put("submenu_array", resultadosMapeo(array));
                            primeroStrenlazado = aux_idEnlazado_cate_sub;
                        }
                        esTrue = false;
                    } else {
                        primeroStr = auxid_cate_sub;
                        primeroStrenlazado = aux_idEnlazado_cate_sub;
                        conquery = false;
                    }
                }
            } else {
                item.put("submenu", false);
                item.remove("submenu_enlazado");
                conquery = false;
            }

            String variable = "";
        }

        for (Map<String, Object> item : borrar) {
            lista.remove(item);
        }
        return lista;
    }

    public Object getCategoriaEspecifico(String id,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        
        Object resultado;
        try {
            int idInt = Integer.parseInt(id);
            String query = "select * from categoria where id=? and id_empresa = ?";
            resultado = jdbcTemplate.queryForMap(query, new Object[]{idInt,idEmpresa});
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object eliminar(String id,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        
        String query = "delete from categoria where id = %1$s and id_empresa = %2$s";
        query = String.format(query,id,idEmpresa);
        
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object insertar(categorias p,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        
        String query = "insert into categoria (nombre,descripcion,submenu,id_empresa) values (upper(?),upper(?),?,?) returning id";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {

            Map<String, Object> categoria = jdbcTemplate.queryForMap(query, new Object[]{p.getNombre(), p.getDescripcion(), p.isSubmenu(),idEmpresa});

            int id_categoria = Integer.parseInt(String.valueOf(categoria.get("id")));

            int contador = 1;
            query = "";
            for (subcategoria item : p.getSubcategorias()) {

                String nombre = item.getNombre();
                if (nombre.contains(",")) {
                    String[] submenus = nombre.split(",");

                    String primero = submenus[0];

                    String aux = "insert into subcategoria (id_categoria,id_subcategoria,nombre)values(%1$s,%2$s,upper('%3$s')) returning id;";
                    aux = String.format(aux, id_categoria, contador, primero);
                    Map<String, Object> diccionario = jdbcTemplate.queryForMap(aux);
                    String id = String.valueOf(diccionario.get("id"));

                    for (int x = 1; x < submenus.length; x++) {
                        aux = "insert into subcategoria (id_categoria,id_subcategoria,nombre,submenu_enlazado)values(%1$s,%2$s,upper('%3$s'),%4$s);";
                        query += String.format(aux, id_categoria, contador, submenus[x], id);
                    }

                } else {
                    String aux = "insert into subcategoria (id_categoria,id_subcategoria,nombre)values(%1$s,%2$s,upper('%3$s'));";
                    query += String.format(aux, id_categoria, contador, nombre);
                }

                contador++;
            }

            if(!p.getSubcategorias().isEmpty()){
            jdbcTemplate.update(query);
            }

            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object actualiza(categorias p,String nombreEmpresa) {
         String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        
        String query = "update categoria set nombre=?,descripcion=? where id=? and id_empresa = ?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query, new Object[]{p.getNombre(), p.getDescripcion(), p.getId(),idEmpresa});
            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
    }
}
