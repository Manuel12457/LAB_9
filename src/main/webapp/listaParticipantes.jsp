
<%@ page import="com.example.lab_9.Beans.BParticipante" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BParticipante>" scope="request" id="listparticipantes"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BParticipante>" scope="request" id="listpaises"/>
<jsp:useBean id="pais" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean id="porcentaje" scope="request" type="java.lang.Float" class="java.lang.Float"/>
<jsp:useBean id="promedio" scope="request" type="java.lang.Float" class="java.lang.Float"/>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>

<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Lista de Participantes"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="participantes"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Lista de Participantes</h1>
            </div>

            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <div class="my-2">
                        <a href="<%=request.getContextPath()%>/participantes?action=crear" class="btn btn-info">Añadir Participante</a>
                    </div>

                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>País con mayor número de participantes</th>
                                <th>Porcentaje de hombres y mujeres</th>
                                <th>Promedio de edad de los participantes</th>

                            </tr>
                        </thead>

                        <tbody>
                            <tr>
                                <td><%=pais%></td>
                                <th><%=porcentaje%></th>
                                <th><%=promedio%></th>
                            </tr>

                        </tbody>
                    </table>

                    <table class="table table-dark table-transparent table-hover">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Edad</th>
                                <th>Nacionalidad</th>
                                <th>Género</th>
                            </tr>
                        </thead>

                        <%
                            for (BParticipante partic : listparticipantes) {
                        %>

                        <tbody>
                            <tr>
                                <td><%=partic.getNombre()%>
                                </td>
                                <td><%=partic.getApellido()%>
                                </td>
                                <td><%=partic.getEdad()%>
                                </td>
                                <td><%=partic.getPais()%>
                                </td>
                                <td><%=partic.getGenero()%>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/participantes?action=editar&id_participante=<%=partic.getIdParticipante()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/participantes?action=borrar&id_participante=<%=partic.getIdParticipante()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                            </tr>
                        </tbody>

                        <%
                            }
                        %>
                    </table>
                </div>
            </div>
        </div>



        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha eliminado el participante correctamente</h5>
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
                    <a href="#popup"  class="btn btn-success mb-2" >No se ha podido eliminar al participante</a>

                </div>
            </div>
        </nav>

        <% } else {%>
        <% } %>
        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>
