package com.example.lab_9.Servlets;

import com.example.lab_9.Beans.BContinente;
import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Daos.MenuPaisesDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PaisesServlet", urlPatterns = {"/PaisesServlet", "/paises"})
public class PaisesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";
        String idContinente = request.getParameter("idContinente") != null ? request.getParameter("idContinente") : "";
        String mensaje = request.getParameter("msg") != null ? request.getParameter("msg") : "";


        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        RequestDispatcher view;


        switch (action) {
            case "listar":
                request.setAttribute("listpaises", menuPaisesDao.listarPaises(idContinente));
                request.setAttribute("listacontinentes", menuPaisesDao.listarContinentes());
                view = request.getRequestDispatcher("/listaPaises.jsp");
                view.forward(request, response);
                break;

            case "formCrear":

                request.setAttribute("listacontinentes", menuPaisesDao.listarContinentes());
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }
                view = request.getRequestDispatcher("/crearPaises.jsp");
                view.forward(request, response);
                break;

            case "formEditar":
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }

                String idPaisEditarStr = request.getParameter("id_pais") != null ? request.getParameter("id_pais") : "";
                int idPaisEditarInt = Integer.parseInt(idPaisEditarStr);
                BPais bPaisEditar = menuPaisesDao.obtenerPaisPorId(idPaisEditarInt);
                if (bPaisEditar != null) {
                    request.setAttribute("bPais", bPaisEditar);
                    request.setAttribute("listacontinentes", menuPaisesDao.listarContinentes());
                    view = request.getRequestDispatcher("/editarPaises.jsp");
                    view.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/listaPaises");
                }
                break;

            case "borrar":
                String idPaisBorrarStr = request.getParameter("id_pais") != null ? request.getParameter("id_pais") : "";
                int idPaisBorrarInt = Integer.parseInt(idPaisBorrarStr);
                BPais bPaisBorrar = menuPaisesDao.obtenerPaisPorId(idPaisBorrarInt);
                String msg;

                if (bPaisBorrar != null) {
                    msg = menuPaisesDao.eliminarPais(idPaisBorrarInt);
                    if (msg.equals("e")) {
                        //se enviará un mensaje de borrado exitoso: ?msg=be
                        response.sendRedirect(request.getContextPath() + "/listaPaises");
                    } else {
                        //se enviará un mensaje de borrado NO exitoso: ?msg=bne
                        response.sendRedirect(request.getContextPath() + "/listaPaises");
                    }
                } else { //el iD no existe, se enviará un mensaje de borrado no exitoso por id: ?msg=bneid
                    response.sendRedirect(request.getContextPath() + "/listaPaises");
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";

        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        String msg;
        RequestDispatcher view;


        switch (action) {

            case "filtrarpaises":
                String idContinente = request.getParameter("id_continente");
                if (idContinente == null) {
                    response.sendRedirect(request.getContextPath() + "/paises");
                } else {
                    response.sendRedirect(request.getContextPath() + "/paises?idContinente=" + idContinente);
                }
                break;

            case "crear":
                String nombrepais = request.getParameter("nombrepais") != null ? request.getParameter("nombrepais") : "";
                String continenteStr = request.getParameter("continente") != null ? request.getParameter("continente") : "";
                String poblacionStr = request.getParameter("poblacion") != null ? request.getParameter("poblacion") : "";
                String tamanhoStr = request.getParameter("tamanho") != null ? request.getParameter("tamanho") : "";


                boolean datosCorrecto;
                int continenteInt = 0;
                long poblacionLong = 0;
                double tamanhoDou = 0;
                try {
                    continenteInt = Integer.parseInt(continenteStr);
                    poblacionLong = Long.parseLong(poblacionStr);
                    tamanhoDou = Double.parseDouble(tamanhoStr);
                    datosCorrecto = true;
                } catch (NumberFormatException e) {
                    datosCorrecto = false;
                }

                // validar los campos
                if (datosCorrecto) {
                    BContinente bContinente = menuPaisesDao.obtenerContinentePorId(continenteInt);
                    String anhadirExitoso;
                    BPais bPaisAnhadir = new BPais();
                    bPaisAnhadir.setNombre(nombrepais);
                    bPaisAnhadir.setContinente(bContinente);
                    bPaisAnhadir.setTamanho(tamanhoDou);
                    bPaisAnhadir.setPoblacion(poblacionLong);
                    anhadirExitoso = menuPaisesDao.anhadirPais(bPaisAnhadir);
                    if (anhadirExitoso.equals("e")) {
                        msg = "ok";
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/paises?action=formCrear&msg=" + msg);
                break;

            case "update":
                String id_paisEditarStr = request.getParameter("id_pais") != null ? request.getParameter("id_pais") : "";
                String nombrepais2 = request.getParameter("nombrepais") != null ? request.getParameter("nombrepais") : "";
                String continenteStr2 = request.getParameter("continente") != null ? request.getParameter("continente") : "";
                String poblacionStr2 = request.getParameter("poblacion") != null ? request.getParameter("poblacion") : "";
                String tamanhoStr2 = request.getParameter("tamanho") != null ? request.getParameter("tamanho") : "";


                boolean datosCorrecto2;
                int id_paisEditarInt = 0;
                int continenteInt2 = 0;
                long poblacionLong2 = 0;
                double tamanhoDou2 = 0;
                try {
                    id_paisEditarInt = Integer.parseInt(id_paisEditarStr);
                    continenteInt2 = Integer.parseInt(continenteStr2);
                    poblacionLong2 = Long.parseLong(poblacionStr2);
                    tamanhoDou2 = Double.parseDouble(tamanhoStr2);
                    datosCorrecto2 = true;
                } catch (NumberFormatException e) {
                    datosCorrecto2 = false;
                }

                // validar los campos
                if (datosCorrecto2) {
                    BContinente bContinente2 = menuPaisesDao.obtenerContinentePorId(continenteInt2);
                    String editarExitoso;
                    BPais bPaisEditar = new BPais();
                    bPaisEditar.setIdPais(id_paisEditarInt);
                    bPaisEditar.setNombre(nombrepais2);
                    bPaisEditar.setContinente(bContinente2);
                    bPaisEditar.setTamanho(tamanhoDou2);
                    bPaisEditar.setPoblacion(poblacionLong2);
                    editarExitoso = menuPaisesDao.editarPais(bPaisEditar);
                    if (editarExitoso.equals("e")) {
                        msg = "ok";
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/paises?action=formEditar&msg=" + msg+"&id_pais=" + id_paisEditarInt);
                break;

        }


    }
}
