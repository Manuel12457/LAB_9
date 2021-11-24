package com.example.lab_9.Servlets;

import com.example.lab_9.Beans.BContinente;
import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BUniversidad;
import com.example.lab_9.Daos.AlumnosDao;
import com.example.lab_9.Daos.MenuPaisesDao;
import com.example.lab_9.Daos.MenuUniversidadesDao;
import com.example.lab_9.Dtos.DtoUniversidad;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UniversidadesServlet", value = "/universidades")
public class UniversidadesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";
        String mensaje = request.getParameter("msg") != null ? request.getParameter("msg") : "";
        String order = request.getParameter("order") != null ? request.getParameter("order") : "ranking";
        MenuUniversidadesDao menunidao = new MenuUniversidadesDao();
        MenuPaisesDao menpaisdao = new MenuPaisesDao();
        AlumnosDao alumdao = new AlumnosDao();
        RequestDispatcher view;
        switch (action) {
            case "listar":
                request.setAttribute("listuniversidad",menunidao.listarUniversidades(order));
                request.setAttribute("mensaje", mensaje);
                view = request.getRequestDispatcher("/listarUniversidades.jsp");
                view.forward(request, response);
                break;

            case "formCrear":

                request.setAttribute("listapaises",menpaisdao.listarPaises(""));
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }
                view = request.getRequestDispatcher("/crearUniversidad.jsp");
                view.forward(request, response);
                break;

            case "formEditar":
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }

                String id_uniStr = request.getParameter("id_uni") != null ? request.getParameter("id_uni") : "";
                int id_uniEditarInt = Integer.parseInt(id_uniStr);
                BUniversidad universidad= menunidao.obtenerUniversidadPorId(id_uniEditarInt);
                if (universidad != null) {
                    request.setAttribute("universidad", universidad);
                    request.setAttribute("listapaises", menpaisdao.listarPaises(""));
                    view = request.getRequestDispatcher("/editarUniversidad.jsp");
                    view.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/universidades");
                }
                break;

            case "borrar":
                String id_uniBorrarStr = request.getParameter("id_uni") != null ? request.getParameter("id_uni") : "";
                int id_uniBorrarInt = Integer.parseInt(id_uniBorrarStr);
                BUniversidad universidadborrar= menunidao.obtenerUniversidadPorId(id_uniBorrarInt);
                String msg;

                if(universidadborrar!= null){
                    msg=menunidao.eliminarUniversidad(id_uniBorrarInt);
                    if (msg.equals("e")) {
                        //se enviará un mensaje de borrado exitoso: ?msg=be
                        response.sendRedirect(request.getContextPath() + "/universidades?msg=ok");
                    } else {
                        //se enviará un mensaje de borrado NO exitoso: ?msg=bne
                        response.sendRedirect(request.getContextPath() + "/universidades?msg=error");
                    }
                }else { //el iD no existe, se enviará un mensaje de borrado no exitoso por id: ?msg=bneid

                    response.sendRedirect(request.getContextPath() + "/universidades?msg=error");
                }
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        MenuUniversidadesDao menunidao = new MenuUniversidadesDao();
        String msg;
        switch (action) {

            case "filtrarpaises":
//                String idContinente = request.getParameter("id_continente");
//                if (idContinente == null) {
//                    response.sendRedirect(request.getContextPath() + "/paises");
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/paises?idContinente=" + idContinente);
//                }
//                break;

            case "crear":
                String nombreuniversidad = request.getParameter("nombreuniversidad") != null ? request.getParameter("nombreuniversidad") : "";
                String paisuniversidad = request.getParameter("paisuniversidad") != null ? request.getParameter("paisuniversidad") : "";
                String rankinguniversidadstr = request.getParameter("rankinguniversidad") != null ? request.getParameter("rankinguniversidad") : "";
                String fotouniversidad = request.getParameter("fotouniversidad") != null ? request.getParameter("fotouniversidad") : "";


                boolean datosCorrecto;
                int rankinguniversidadInt = 0;
                int paisuniversidadInt = 0;
                paisuniversidadInt = Integer.parseInt(paisuniversidad);
                try {
                    rankinguniversidadInt = Integer.parseInt(rankinguniversidadstr);
                    datosCorrecto = true;
                } catch (NumberFormatException e) {
                    datosCorrecto = false;
                }

                // validar los campos
                if (datosCorrecto) {
                    String anhadirExitoso;
                    BUniversidad bunivanhadir = new BUniversidad();
                    bunivanhadir.setNombre(nombreuniversidad);
                    bunivanhadir.setFoto(fotouniversidad);
                    BPais bpais =new BPais();
                    bpais.setIdPais(paisuniversidadInt);
                    bunivanhadir.setPais(bpais);
                    bunivanhadir.setRanking(rankinguniversidadInt);
                    anhadirExitoso =menunidao.anhadirUniversidad(bunivanhadir);

                    if (anhadirExitoso.equals("e")) {
                        msg = "ok";
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/universidades?action=formCrear&msg=" + msg);
                break;

            case "update":
                String id_unieditarStr = request.getParameter("id_uni") != null ? request.getParameter("id_uni") : "";
                String nombreuniversidad1 = request.getParameter("nombreuniversidad") != null ? request.getParameter("nombreuniversidad") : "";
                String paisuniversidad1 = request.getParameter("paisuniversidad") != null ? request.getParameter("paisuniversidad") : "";
                String rankinguniversidadstr1 = request.getParameter("rankinguniversidad") != null ? request.getParameter("rankinguniversidad") : "";
                String fotouniversidad1 = request.getParameter("fotouniversidad") != null ? request.getParameter("fotouniversidad") : "";


                boolean datosCorrecto1;
                int rankinguniversidadInt1 = 0;
                int paisuniversidadInt1 = 0;
                int id_unieditarInt1=0;
                paisuniversidadInt1 = Integer.parseInt(paisuniversidad1);
                try {
                    rankinguniversidadInt1 = Integer.parseInt(rankinguniversidadstr1);
                    id_unieditarInt1 = Integer.parseInt(id_unieditarStr);
                    datosCorrecto1 = true;
                } catch (NumberFormatException e) {
                    datosCorrecto1 = false;
                }

//                // validar los campos
                if (datosCorrecto1) {
                    String editarExitoso1;
                    BUniversidad bunivanhadir = new BUniversidad();
                    bunivanhadir.setIdUniversidad(id_unieditarInt1);
                    bunivanhadir.setNombre(nombreuniversidad1);
                    bunivanhadir.setFoto(fotouniversidad1);
                    BPais bpais1 =new BPais();
                    bpais1.setIdPais(paisuniversidadInt1);
                    bunivanhadir.setPais(bpais1);
                    bunivanhadir.setRanking(rankinguniversidadInt1);
                    editarExitoso1 =menunidao.editarUniversidad(bunivanhadir);


                    if (editarExitoso1.equals("e")) {
                        msg = "ok";
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/universidades?action=formEditar&msg=" + msg+"&id_uni=" + id_unieditarStr);
                break;

        }

    }
}
