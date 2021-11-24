<%@ page import="com.example.lab_9.Beans.BParticipante" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BParticipante>" scope="request" id="listalumnos"/>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>


<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Lista de Universidades"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="universidades"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Lista de Alumnos</h1>
            </div>

            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <div class="my-2">
                        <a href="<%=request.getContextPath()%>/alumnos?action=crear" class="btn btn-info">Añadir Alumno</a>
                    </div>

                    <table class="table table-dark table-transparent table-hover">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Edad</th>
                                <th>Código de Alumno</th>
                                <th>Promedio Ponderado</th>
                                <th>Condición</th>
                            </tr>
                        </thead>



                        <tbody>
                            <%
                                for (BParticipante participante: listalumnos) {
                            %>

                            <%if(participante.getAlumno().getCondicion().equals("normal")) {%>
                            <tr>

                                <td><%=participante.getNombre()%>
                                </td>
                                <td><%=participante.getApellido()%>
                                </td>
                                <td><%=participante.getEdad()%>
                                </td>
                                <td><%=participante.getAlumno().getCodigo()%>
                                </td>
                                <td><%=participante.getAlumno().getPromedioPonderado()%>
                                </td>
                                <td><%=participante.getAlumno().getCondicion()%>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/alumnos?action=editar&id_alumno=<%=participante.getIdParticipante()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/alumnos?action=borrar&id_alumno=<%=participante.getIdParticipante()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                            </tr>
                            <%}else{%>
                            <tr>

                                <td style="color: red"><%=participante.getNombre()%>
                                </td>
                                <td><%=participante.getApellido()%>
                                </td>
                                <td><%=participante.getEdad()%>
                                </td>
                                <td><%=participante.getAlumno().getCodigo()%>
                                </td>
                                <td><%=participante.getAlumno().getPromedioPonderado()%>
                                </td>
                                <td><%=participante.getAlumno().getCondicion()%>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/alumnos?action=editar&id_alumno=<%=participante.getIdParticipante()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/alumnos?action=borrar&id_alumno=<%=participante.getIdParticipante()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                            </tr>

                            <%}%>
                            <%
                                }
                            %>
                        </tbody>


                    </table>
                </div>
            </div>
        </div>



        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha eliminado la universidad correctamente</h5>
                    <a href="#popup" class="btn btn-success mb-2" >Aceptar</a>

                </div>
            </div>
        </nav>
        <% } else if (mensaje.equals("error")){%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2"></h5>
                    <a href="#popup"  class="btn btn-success mb-2" >No se ha podido eliminar la universidad</a>

                </div>
            </div>
        </nav>

        <% } else {%>
        <% } %>
        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>
