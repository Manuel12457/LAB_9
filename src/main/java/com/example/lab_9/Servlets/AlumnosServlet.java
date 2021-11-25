package com.example.lab_9.Servlets;

import com.example.lab_9.Beans.BAlumno;
import com.example.lab_9.Beans.BParticipante;
import com.example.lab_9.Beans.BUniversidad;
import com.example.lab_9.Daos.AlumnosDao;

import com.example.lab_9.Daos.MenuParticipantesDao;
import com.example.lab_9.Daos.MenuUniversidadesDao;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AlumnosServlet", value = "/alumnos")
public class AlumnosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";
        String mensaje = request.getParameter("msg") != null ? request.getParameter("msg") : "";
        String id_uniStr = request.getParameter("id_uni") != null ? request.getParameter("id_uni") : "";


        int id_uniInt = Integer.parseInt(id_uniStr);

        AlumnosDao alumnosDao = new AlumnosDao();
        MenuUniversidadesDao menunidao = new MenuUniversidadesDao();
        BUniversidad universidad = menunidao.obtenerUniversidadPorId(id_uniInt);
        MenuParticipantesDao menuParticipantesDao = new MenuParticipantesDao();


        RequestDispatcher view;

        switch (action) {
            case "listar":
                request.setAttribute("universidad", universidad);
                request.setAttribute("listalumnos", alumnosDao.listarAlumnos(id_uniInt));
                request.setAttribute("mensaje", mensaje);
                view = request.getRequestDispatcher("/listaAlumnos.jsp");
                view.forward(request, response);
                break;

            case "crear":
                request.setAttribute("universidad", universidad.getIdUniversidad());
                request.setAttribute("listaparticip", menuParticipantesDao.listarParticipantesSinUniFiltradoPorPais(universidad.getPais().getIdPais()));
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }
                view = request.getRequestDispatcher("/crearAlumno.jsp");
                view.forward(request, response);
                break;

            case "editar":
                if (!mensaje.equals("")) {
                    request.setAttribute("mensaje", mensaje);
                }
                String id_AlumnoStr = request.getParameter("id_alumno") != null ? request.getParameter("id_alumno") : "";
                int id_AlumnoEditarInt = Integer.parseInt(id_AlumnoStr);
                BAlumno bAlumno = alumnosDao.obtenerAlumnoPorId(id_AlumnoEditarInt);

                BParticipante participanteEditar = menuParticipantesDao.obtenerParticipantePorIdAlumno(id_AlumnoEditarInt);

                if (bAlumno != null) {
                    request.setAttribute("iduniversidad", universidad.getIdUniversidad());
                    request.setAttribute("alumno", participanteEditar);
                    view = request.getRequestDispatcher("/editarAlumno.jsp");
                    view.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/alumnos");
                }
                break;

            case "borrarlogico":
                String id_AlumnoBorrarStr = request.getParameter("id_alumno") != null ? request.getParameter("id_alumno") : "";
                int id_AlumnoBorrarInt = Integer.parseInt(id_AlumnoBorrarStr);
                BAlumno bAlumnoBorrar = alumnosDao.obtenerAlumnoPorId(id_AlumnoBorrarInt);
                String msg;

                if (bAlumnoBorrar != null) {
                    msg = alumnosDao.eliminarAlumno(id_AlumnoBorrarInt);
                    if (msg.equals("e")) {
                        //se enviará un mensaje de borrado exitoso: ?msg=be
                        response.sendRedirect(request.getContextPath() + "/alumnos?msg=ok&id_uni=" + universidad.getIdUniversidad());
                    } else {
                        //se enviará un mensaje de borrado NO exitoso: ?msg=bne
                        response.sendRedirect(request.getContextPath() + "/alumnos?msg=error&id_uni=" + universidad.getIdUniversidad());
                    }
                } else { //el iD no existe, se enviará un mensaje de borrado no exitoso por id: ?msg=bneid
                    response.sendRedirect(request.getContextPath() + "/alumnos?msg=error&id_uni=" + universidad.getIdUniversidad());
                }
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String idUniStr = request.getParameter("id_uni") != null ? request.getParameter("id_uni") : "";

        int id_uniInt = Integer.parseInt(idUniStr);

        AlumnosDao alumnosDao = new AlumnosDao();
        MenuUniversidadesDao menunidao = new MenuUniversidadesDao();
        MenuParticipantesDao menuParticipantesDao = new MenuParticipantesDao();
        BUniversidad universidad = menunidao.obtenerUniversidadPorId(id_uniInt);
        String msg;

        switch (action) {

            case "crear":
                String idParticipanteStr = request.getParameter("participante") != null ? request.getParameter("participante") : "";
                String codigoparticipante = request.getParameter("codigoparticipante") != null ? request.getParameter("codigoparticipante") : "";
                String promedioparticipanteStr = request.getParameter("promedioparticipante") != null ? request.getParameter("promedioparticipante") : "";

                boolean datosCorrecto;
                int idParticipanteInt = 0;
                double promedioparticipanteDou = 0;

                try {
                    idParticipanteInt = Integer.parseInt(idParticipanteStr);
                    promedioparticipanteDou = Double.parseDouble(promedioparticipanteStr);
                    datosCorrecto = true;
                } catch (NumberFormatException e) {
                    datosCorrecto = false;
                }
                // validar los campos
                if (datosCorrecto) {
                    String anhadirExitoso;
                    BAlumno bAlumnoCrear = new BAlumno();
                    bAlumnoCrear.setCodigo(codigoparticipante);
                    bAlumnoCrear.setPromedioPonderado(promedioparticipanteDou);
                    bAlumnoCrear.setCondicion("normal");
                    bAlumnoCrear.setUniversidad(universidad);
                    anhadirExitoso = alumnosDao.anhadirAlumno(bAlumnoCrear, idParticipanteInt);

                    if (anhadirExitoso.equals("e")) {
                        msg = "ok";
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/alumnos?action=formCrear&msg=" + msg + "&id_uni=" + universidad.getIdUniversidad());
                break;

            case "update":
                String id_AlumStr = request.getParameter("id_alum") != null ? request.getParameter("id_alum") : "";
                String nombreAlumno = request.getParameter("nombrealumno") != null ? request.getParameter("nombrealumno") : "";
                String apellidoAlumno = request.getParameter("apellidoalumno") != null ? request.getParameter("apellidoalumno") : "";
                String edadAlumnoStr = request.getParameter("edadalumno") != null ? request.getParameter("edadalumno") : "";
                String codigoAlumno = request.getParameter("codigoalumno") != null ? request.getParameter("codigoalumno") : "";
                String promedioParticipanteStr = request.getParameter("promedioparticipante") != null ? request.getParameter("promedioparticipante") : "";

                boolean datosCorrecto1;
                int id_AlumInt = 0;
                int edadAlumnoInt = 0;
                double promedioParticipanteDou = 0;
                try {
                    id_AlumInt = Integer.parseInt(id_AlumStr);
                    edadAlumnoInt = Integer.parseInt(edadAlumnoStr);
                    promedioParticipanteDou = Double.parseDouble(promedioParticipanteStr);
                    datosCorrecto1 = true;
                } catch (NumberFormatException e) {
                    datosCorrecto1 = false;
                }

//                // validar los campos
                if (datosCorrecto1) {
                    String editarExitoso1;
                    String editarExitoso2;

                    BAlumno alumnoEditar = new BAlumno();
                    alumnoEditar.setIdAlumno(id_AlumInt);
                    alumnoEditar.setCondicion("normal");
                    alumnoEditar.setCodigo(codigoAlumno);
                    alumnoEditar.setUniversidad(universidad);
                    alumnoEditar.setPromedioPonderado(promedioParticipanteDou);
                    editarExitoso1 = alumnosDao.editarAlumno(alumnoEditar);

                    BParticipante participanteEditar = menuParticipantesDao.obtenerParticipantePorIdAlumno(id_AlumInt);
                    participanteEditar.setNombre(nombreAlumno);
                    participanteEditar.setApellido(apellidoAlumno);
                    participanteEditar.setEdad(edadAlumnoInt);

                    editarExitoso2 = menuParticipantesDao.editarParticipante(participanteEditar);

                    if (editarExitoso1.equals("e")) {
                        if (editarExitoso2.equals("e")) {
                            msg = "ok";
                        } else {
                            msg = "error";
                        }
                    } else {
                        msg = "error";
                    }
                } else {
                    msg = "error";
                }
                response.sendRedirect(request.getContextPath() + "/alumnos?action=formEditar&msg=" + msg + "&id_uni=" + universidad.getIdUniversidad());
                break;
        }
    }
}
