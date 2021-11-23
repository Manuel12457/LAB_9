package com.example.lab_9.Servlets;

import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Daos.ContinentesDao;
import com.example.lab_9.Daos.MenuPaisesDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PaisesServlet", urlPatterns = {"/PaisesServlet", "/Paises"})
public class PaisesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";
        String idContinenteStr = request.getParameter("idContinente") != null ? request.getParameter("idContinente") : "";
        String msg;
        int idContinenteInt = Integer.parseInt(idContinenteStr);

        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        ContinentesDao continentesDao = new ContinentesDao();
        RequestDispatcher view;


        switch (action) {
            case "listar":
                request.setAttribute("listpaises", menuPaisesDao.listarPaises(idContinenteInt));
                request.setAttribute("listacontinentes", continentesDao.listarContinentes());
                view = request.getRequestDispatcher("/listaPaises.jsp");
                view.forward(request, response);
                break;

            case "formCrear":
                view = request.getRequestDispatcher("/crearPaises.jsp");
                view.forward(request, response);
                break;

            case "formEditar":
                String idEditarStr = request.getParameter("id") != null ? request.getParameter("id") : "";
                int idEditarInt = Integer.parseInt(idEditarStr);
                BPais bPaisEditar = menuPaisesDao.obtenerPaisPorId(idEditarInt);

                if (bPaisEditar != null) {
                    request.setAttribute("bPais", bPaisEditar);
                    view = request.getRequestDispatcher("/editarPaises.jsp");
                    view.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/listarPaises");
                }
                break;

            case "borrar":
                String idBorrarStr = request.getParameter("id") != null ? request.getParameter("id") : "";
                int idBorrarInt = Integer.parseInt(idBorrarStr);
                BPais bPaisBorrar = menuPaisesDao.obtenerPaisPorId(idBorrarInt);

                if (bPaisBorrar != null) {
                    msg = menuPaisesDao.eliminarPais(idBorrarStr);
                    if ( msg == "e") {
                        //se enviará un mensaje de borrado exitoso: ?msg=be
                        response.sendRedirect(request.getContextPath() + "/listarPaises?msg=be");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/listarPaises?msg=bne");
                    }
                } else { //el jobId no existe
                    response.sendRedirect(request.getContextPath() + "/listarPaises?msg=bne");
                }

                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "crear";

        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        RequestDispatcher view;


    }
}
