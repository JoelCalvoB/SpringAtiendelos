/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.funciones.archivos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.entrecodigos.repositorios.Productos;
import com.entrecodigos.repositorios.menuDesplegable;
import com.entrecodigos.repositorios.subcategoria;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author i7
 */
@Transactional
@Component("objProductos")
public class ProductosDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getProductos(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select p.*,c.nombre AS nombre_categoria from productos p inner join categoria c on c.id = p.id_categoria where p.id_empresa = %1$s";
        query = String.format(query, idEmpresa);

        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;

    }

    public Object seleccionaProducto(String id, String nombreEmpresa) {
        Object resultado;
        try {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
            queryempresa = String.format(queryempresa, nombreEmpresa);
            Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

            int idInt = Integer.parseInt(id);
            String query = " select * from productos where id_categoria=? and id_empresa = ? order by id_producto asc ";
            List<Map<String, Object>> auxResultado = jdbcTemplate.queryForList(query, new Object[]{idInt, idEmpresa});

            for (Map<String, Object> item : auxResultado) {
                query = "select mmenu.* from detalle_submenudesplegable ssub inner join  "
                        + " menudesplegable mmenu on  mmenu.id_menu = ssub.id_submenudesplegable  "
                        + " where id_producto = %1$s and mmenu.id_empresa = %2$s ";

                query = String.format(query, item.get("id_producto"), idEmpresa);
                List<Map<String, Object>> listaMenu = jdbcTemplate.queryForList(query);

                List<menuDesplegable> menuaInsertar = new ArrayList<menuDesplegable>();
                for (Map<String, Object> auxitem : listaMenu) {
                    int idMenu = Integer.parseInt(String.valueOf(auxitem.get("id_menu")));
                    String nombre = String.valueOf(auxitem.get("nombre"));
                    menuDesplegable objMenu = new menuDesplegable();
                    objMenu.setId_empresa(idEmpresa);
                    objMenu.setId_menu(idMenu);
                    objMenu.setNombre(nombre);
                    menuaInsertar.add(objMenu);
                }
                item.put("lista_menudesplegable", menuaInsertar);
            }

            resultado = auxResultado;

        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object elimnarProducto(String id_producto, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "delete from productos where id_producto= %1$s and id_empresa = %2$s";
        query = String.format(query, id_producto, idEmpresa);
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object ActualizaProducto(Productos p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "update productos set nombre=upper(?) , descripcion=upper(?) , precio=? , id_categoria=?,id_video=?,nombre_video=?,notificacion=?,tipo_menudesplegable=? where id_producto=? and id_empresa = ?";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            creacionArchivo(p);
            jdbcTemplate.update(query, new Object[]{p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getId_categoria(), p.getId_video(), p.getNombre_video(), p.getNotificacion(),p.getTipo_menuDesplegable(), p.getId_producto(), idEmpresa});

            query = "delete from detalle_submenudesplegable where id_empresa = %1$s and id_producto = %2$s";
            query = String.format(query, idEmpresa,p.getId_producto());
            jdbcTemplate.update(query);
            List<menuDesplegable> lista = p.getMenu_desplegable();
            if (lista != null) {
                if (!lista.isEmpty()) {
                    query = "";
                    for (menuDesplegable item : lista) {
                        String auxQuery = " insert into detalle_submenudesplegable (id_producto,id_submenudesplegable,id_empresa) values (%1$s,%2$s,%3$s); ";
                        auxQuery = String.format(auxQuery, p.getId_producto(), item.getId_menu(), idEmpresa);

                        query += auxQuery;
                    }
                    jdbcTemplate.update(query);
                }else{
               query = "update productos set tipo_menudesplegable = '' where id_empresa = %1$s and id_producto = %2$s";
               query = String.format(query, idEmpresa,p.getId_producto());
               jdbcTemplate.update(query);
            }
            }else{
                
                query = "update productos set tipo_menudesplegable = '' where id_empresa = %1$s and id_producto = %2$s";
               query = String.format(query, idEmpresa,p.getId_producto());
               jdbcTemplate.update(query);
            
            }

            resultado.put("respuesta", "Registro Actualizado");
        } catch (Exception e) {
            resultado.put("respuesta", "Registro  No Actualizado");

        }

        return resultado;
    }

    public Object InsertarProducto(Productos p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "Insert into productos (nombre,descripcion,precio,id_categoria,id_video,nombre_video,notificacion,subcategoria,id_empresa,tipo_menudesplegable) values (upper(?),upper(?),?,?,?,?,?,?,?,?) returning id_producto";
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {

            Map<String, Object> aux = jdbcTemplate.queryForMap(query, new Object[]{p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getId_categoria(), p.getId_video(), p.getNombre_video(), p.getNotificacion(), p.getSubcategoria(), idEmpresa, p.getTipo_menuDesplegable()});

            int id_producto = Integer.parseInt(String.valueOf(aux.get("id_producto")));

            //Parte que se actualiza si es que tiene submebu
            List<menuDesplegable> lista = p.getMenu_desplegable();
            if (lista != null) {
                if (!lista.isEmpty()) {
                    query = "";
                    for (menuDesplegable item : lista) {
                        String auxQuery = " insert into detalle_submenudesplegable (id_producto,id_submenudesplegable,id_empresa) values (%1$s,%2$s,%3$s); ";
                        auxQuery = String.format(auxQuery, id_producto, item.getId_menu(), idEmpresa, p.getTipo_menuDesplegable());

                        query += auxQuery;
                    }
                    jdbcTemplate.update(query);
                }
            }

            p.setId_producto(Integer.parseInt(String.valueOf(aux.get("id_producto"))));
            creacionArchivo(p);
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object seleccionarListaPorCategorias(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from categoria where id_empresa = %1$s order by id";
        query = String.format(query, idEmpresa);

        List<Map<String, Object>> resultado = this.jdbcTemplate.queryForList(query);

        for (Map<String, Object> item : resultado) {
            boolean submenu = Boolean.valueOf(String.valueOf(item.get("submenu")));
            if (submenu) {
                query = "select * from subcategoria where id_categoria = " + item.get("id") + " order by id_subcategoria,id ";
                List<Map<String, Object>> resultadoSub = jdbcTemplate.queryForList(query);

                resultadoSub = resultadosMapeo(resultadoSub);

                resultadoSub = agregarProductos(resultadoSub, item.get("id").toString(), idEmpresa);

                item.put("submenu_array", resultadoSub);

            } else {
                query = "select * from productos where id_categoria = %1$s and id_empresa = %2$s order by id_producto asc";
                query = String.format(query, item.get("id").toString(), idEmpresa);
                List<Map<String, Object>> productos = this.jdbcTemplate.queryForList(query);

                for (Map<String, Object> itemProducto : productos) {
                    query = "select mmenu.* from detalle_submenudesplegable ssub inner join  "
                            + " menudesplegable mmenu on  mmenu.id_menu = ssub.id_submenudesplegable  "
                            + " where id_producto = %1$s and mmenu.id_empresa = %2$s ";

                    query = String.format(query, itemProducto.get("id_producto"), idEmpresa);
                    List<Map<String, Object>> listaMenu = jdbcTemplate.queryForList(query);

                    List<menuDesplegable> menuaInsertar = new ArrayList<menuDesplegable>();
                    for (Map<String, Object> auxitem : listaMenu) {
                        int idMenu = Integer.parseInt(String.valueOf(auxitem.get("id_menu")));
                        String nombre = String.valueOf(auxitem.get("nombre"));
                        menuDesplegable objMenu = new menuDesplegable();
                        objMenu.setId_empresa(idEmpresa);
                        objMenu.setId_menu(idMenu);
                        objMenu.setNombre(nombre);
                        menuaInsertar.add(objMenu);
                    }

                    itemProducto.put("lista_menudesplegable", menuaInsertar);
                }

                item.put("productos", productos);
            }
        }

        return resultado;
    }

    public List<Map<String, Object>> agregarProductos(List<Map<String, Object>> lista, String id_categoria, int idEmpresa) {

        String query = "";
        for (Map<String, Object> item : lista) {
            boolean submenu = Boolean.valueOf(String.valueOf(item.get("submenu")));
            if (submenu) {

                item.put("submenu_array", this.agregarProductos((List<Map<String, Object>>) item.get("submenu_array"), id_categoria, idEmpresa));

            } else {
                String id_subcategoria = String.valueOf(item.get("id_subcategoria"));

                query = "select * from productos where id_categoria = " + id_categoria + "  and subcategoria = '%1$s' and id_empresa = %2$s order by id_producto asc";
                query = String.format(query, item.get("id").toString(), idEmpresa);
                List<Map<String, Object>> productos = this.jdbcTemplate.queryForList(query);

                for (Map<String, Object> itemProducto : productos) {
                    query = "select mmenu.* from detalle_submenudesplegable ssub inner join  "
                            + " menudesplegable mmenu on  mmenu.id_menu = ssub.id_submenudesplegable  "
                            + " where id_producto = %1$s and mmenu.id_empresa = %2$s ";

                    query = String.format(query, itemProducto.get("id_producto"), idEmpresa);
                    List<Map<String, Object>> listaMenu = jdbcTemplate.queryForList(query);

                    List<menuDesplegable> menuaInsertar = new ArrayList<menuDesplegable>();
                    for (Map<String, Object> auxitem : listaMenu) {
                        int idMenu = Integer.parseInt(String.valueOf(auxitem.get("id_menu")));
                        String nombre = String.valueOf(auxitem.get("nombre"));
                        menuDesplegable objMenu = new menuDesplegable();
                        objMenu.setId_empresa(idEmpresa);
                        objMenu.setId_menu(idMenu);
                        objMenu.setNombre(nombre);
                        menuaInsertar.add(objMenu);
                    }

                    itemProducto.put("lista_menudesplegable", menuaInsertar);
                }

                item.put("productos", productos);

            }
        }
        return lista;
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

    private void creacionArchivo(Productos p) {
        if (p.isSubirImagen()) {
            String query = String.format("update productos set ruta_imagen='%1$s' where id_producto=" + p.getId_producto(), p.getImagen());
            this.jdbcTemplate.update(query);
            archivos a = new archivos();
            BufferedImage img = a.decodificarImagenBase64(p.getImagen());
            //a.escribirArchivo(img,p.getId_producto());
        }
    }

    public Object seleccionaProductoSubcategoria(String id, String id_categoria, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Object resultado;
        try {
            int idInt = Integer.parseInt(id);
            String query = " select * from productos where id_categoria=%1$s and subcategoria = '%2$s' and id_empresa = %3$s  order by id_producto asc ";
            query = String.format(query, id, id_categoria, idEmpresa);
            List<Map<String,Object>> auxResultado = jdbcTemplate.queryForList(query);
            
           
             for (Map<String, Object> item : auxResultado) {
                query = "select mmenu.* from detalle_submenudesplegable ssub inner join  "
                        + " menudesplegable mmenu on  mmenu.id_menu = ssub.id_submenudesplegable  "
                        + " where id_producto = %1$s and mmenu.id_empresa = %2$s ";

                query = String.format(query, item.get("id_producto"), idEmpresa);
                List<Map<String, Object>> listaMenu = jdbcTemplate.queryForList(query);

                List<menuDesplegable> menuaInsertar = new ArrayList<menuDesplegable>();
                for (Map<String, Object> auxitem : listaMenu) {
                    int idMenu = Integer.parseInt(String.valueOf(auxitem.get("id_menu")));
                    String nombre = String.valueOf(auxitem.get("nombre"));
                    menuDesplegable objMenu = new menuDesplegable();
                    objMenu.setId_empresa(idEmpresa);
                    objMenu.setId_menu(idMenu);
                    objMenu.setNombre(nombre);
                    menuaInsertar.add(objMenu);
                }
                item.put("lista_menudesplegable", menuaInsertar);
            }

            resultado = auxResultado;
            
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }
}
