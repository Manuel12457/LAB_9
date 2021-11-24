package com.example.lab_9.Servlets;

import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BParticipante;
import com.example.lab_9.Daos.MenuPaisesDao;
import com.example.lab_9.Daos.MenuParticipantesDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "ParticipantesServlet", value = "/participantes")
public class ParticipantesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String mensaje = request.getParameter("mensaje") == null ? "" : request.getParameter("mensaje");
        MenuParticipantesDao menuParticipantesDao = new MenuParticipantesDao();
        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        ArrayList<BParticipante> listaParticipantes = menuParticipantesDao.listarParticipantes();
        HashMap<String, Float> porcentaje = menuParticipantesDao.porcentajeMF();
        float promedio = menuParticipantesDao.promedioEdad();
        RequestDispatcher view;

        switch (action) {

            case "":

                request.setAttribute("listparticipantes",listaParticipantes);
                request.setAttribute("pais", menuParticipantesDao.paisesYNumeroParticipantes());
                request.setAttribute("porcentaje", "M: " + porcentaje.get("M") + "     F: " + porcentaje.get("F"));
                request.setAttribute("promedio", promedio);
                request.setAttribute("mensaje",mensaje);

                view = request.getRequestDispatcher("listaParticipantes.jsp");
                view.forward(request, response);

                break;
            case "crearForm":
                request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                request.setAttribute("mensaje","");

                request.setAttribute("errEdadNumero","0");
                request.setAttribute("errEdadMayor18","0");
                request.setAttribute("errNombreIniciaPalabra","0");
                request.setAttribute("errApellidoIniciaPalabra","0");
                request.setAttribute("participante", new BParticipante());

                view = request.getRequestDispatcher("crearParticipante.jsp");
                view.forward(request, response);
                break;
            case "editarForm":
                int idParticipanteEditar = Integer.parseInt(request.getParameter("id_participante"));
                request.setAttribute("participante", menuParticipantesDao.obtenerParticipantePorId(idParticipanteEditar));
                request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                request.setAttribute("mensaje","");
                request.setAttribute("errEdadNumero","0");
                request.setAttribute("errEdadMayor18","0");
                request.setAttribute("errNombreIniciaPalabra","0");
                request.setAttribute("errApellidoIniciaPalabra","0");
                view = request.getRequestDispatcher("editarParticipante.jsp");
                view.forward(request, response);
                break;
            case "borrar":
                int idParticipanteBorrar = Integer.parseInt(request.getParameter("id_participante"));
                String mensajeB = menuParticipantesDao.eliminarParticipante(idParticipanteBorrar);
                response.sendRedirect(request.getContextPath() + "/participantes?mensaje=" + mensajeB);
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
        MenuParticipantesDao menuParticipantesDao = new MenuParticipantesDao();
        RequestDispatcher view;

        switch (action) {

            case "crear":
                BParticipante participante = new BParticipante();
                participante.setNombre(request.getParameter("nombreparticipante"));
                participante.setApellido(request.getParameter("apellidoparticipante"));

                boolean edadNumero;
                int edad = 0;
                try {
                    edad = Integer.parseInt(request.getParameter("edadparticipante"));
                    participante.setEdad(edad);
                    edadNumero = true;
                } catch (NumberFormatException e) {
                    edadNumero = false;
                }
                boolean edadMayor18 = edad > 18;

                boolean nombreIniciaPalabra;
                String palabraIniNombre = Character.toString(request.getParameter("nombreparticipante").charAt(0));
                try {
                    int a = Integer.parseInt(palabraIniNombre);
                    nombreIniciaPalabra = false;
                } catch (NumberFormatException e) {
                    nombreIniciaPalabra = true;
                }

                boolean apellidoIniciaPalabra;
                String palabraIniApellido = Character.toString(request.getParameter("apellidoparticipante").charAt(0));
                try {
                    int a = Integer.parseInt(palabraIniApellido);
                    apellidoIniciaPalabra = false;
                } catch (NumberFormatException e) {
                    apellidoIniciaPalabra = true;
                }


                BPais pais = menuPaisesDao.obtenerPaisPorId(Integer.parseInt(request.getParameter("paisparticipante")));
                participante.setPais(pais);
                participante.setGenero(request.getParameter("generoparticipante"));

                if (edadNumero && edadMayor18 && nombreIniciaPalabra && apellidoIniciaPalabra) {
                    String mensaje = menuParticipantesDao.anhadirParticipante(participante);
                    request.setAttribute("mensaje",mensaje);

                    request.setAttribute("errEdadNumero","0");
                    request.setAttribute("errEdadMayor18","0");
                    request.setAttribute("errNombreIniciaPalabra","0");
                    request.setAttribute("errApellidoIniciaPalabra","0");
                    request.setAttribute("participante", participante);

                    request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                    view = request.getRequestDispatcher("crearParticipante.jsp");
                    view.forward(request, response);
                } else {

                    if (edadNumero) {
                        request.setAttribute("errEdadNumero","0");
                    } else {
                        request.setAttribute("errEdadNumero","1");
                    }
                    if (edadMayor18) {
                        request.setAttribute("errEdadMayor18","0");
                    } else {
                        request.setAttribute("errEdadMayor18","1");
                    }
                    if (nombreIniciaPalabra) {
                        request.setAttribute("errNombreIniciaPalabra","0");
                    } else {
                        request.setAttribute("errNombreIniciaPalabra","1");
                    }
                    if (apellidoIniciaPalabra) {
                        request.setAttribute("errApellidoIniciaPalabra","0");
                    } else {
                        request.setAttribute("errApellidoIniciaPalabra","1");
                    }
                    request.setAttribute("mensaje","error");
                    request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                    request.setAttribute("participante", participante);
                    view = request.getRequestDispatcher("crearParticipante.jsp");
                    view.forward(request, response);

                }

                break;
            case "update":

                BParticipante participanteE = new BParticipante();
                participanteE.setIdParticipante(Integer.parseInt(request.getParameter("id")));
                participanteE.setNombre(request.getParameter("nombrepart"));
                participanteE.setApellido(request.getParameter("apellpart"));

                boolean edadNumeroE;
                int edadE = 0;
                try {
                    edadE = Integer.parseInt(request.getParameter("edad"));
                    participanteE.setEdad(edadE);
                    edadNumeroE = true;
                } catch (NumberFormatException e) {
                    edadNumeroE = false;
                }
                boolean edadMayor18E = edadE > 18;

                boolean nombreIniciaPalabraE;
                String palabraIniNombreE = Character.toString(request.getParameter("nombrepart").charAt(0));
                try {
                    int a = Integer.parseInt(palabraIniNombreE);
                    nombreIniciaPalabraE = false;
                } catch (NumberFormatException e) {
                    nombreIniciaPalabraE = true;
                }

                boolean apellidoIniciaPalabraE;
                String palabraIniApellidoE = Character.toString(request.getParameter("apellpart").charAt(0));
                try {
                    int a = Integer.parseInt(palabraIniApellidoE);
                    apellidoIniciaPalabraE = false;
                } catch (NumberFormatException e) {
                    apellidoIniciaPalabraE = true;
                }

                BPais paisE = menuPaisesDao.obtenerPaisPorId(Integer.parseInt(request.getParameter("paisparticipante")));
                participanteE.setPais(paisE);
                participanteE.setGenero(request.getParameter("generoparticipante"));

                if (edadNumeroE && edadMayor18E && nombreIniciaPalabraE && apellidoIniciaPalabraE) {

                    String mensaje = menuParticipantesDao.editarParticipante(participanteE);
                    request.setAttribute("mensaje",mensaje);

                    request.setAttribute("errEdadNumero","0");
                    request.setAttribute("errEdadMayor18","0");
                    request.setAttribute("errNombreIniciaPalabra","0");
                    request.setAttribute("errApellidoIniciaPalabra","0");
                    request.setAttribute("participante", participanteE);

                    request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                    view = request.getRequestDispatcher("editarParticipante.jsp");
                    view.forward(request, response);
                } else {

                    if (edadNumeroE) {
                        request.setAttribute("errEdadNumero","0");
                    } else {
                        request.setAttribute("errEdadNumero","1");
                    }
                    if (edadMayor18E) {
                        request.setAttribute("errEdadMayor18","0");
                    } else {
                        request.setAttribute("errEdadMayor18","1");
                    }
                    if (nombreIniciaPalabraE) {
                        request.setAttribute("errNombreIniciaPalabra","0");
                    } else {
                        request.setAttribute("errNombreIniciaPalabra","1");
                    }
                    if (apellidoIniciaPalabraE) {
                        request.setAttribute("errApellidoIniciaPalabra","0");
                    } else {
                        request.setAttribute("errApellidoIniciaPalabra","1");
                    }
                    request.setAttribute("mensaje","error");
                    request.setAttribute("listapaises", menuPaisesDao.listarPaises(""));
                    request.setAttribute("participante", participanteE);
                    view = request.getRequestDispatcher("editarParticipante.jsp");
                    view.forward(request, response);

                }

                break;

        }

    }
}
