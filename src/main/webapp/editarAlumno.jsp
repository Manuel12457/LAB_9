<%@ page import="com.example.lab_9.Beans.BParticipante" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>
<%--jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BParticipante>" scope="request" id="listaparticip"/--%>
<jsp:useBean id="iduniversidad" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="alumno" scope="request" type="com.example.lab_9.Beans.BParticipante"/>

<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Editar Alumno"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="universidades"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Editar Alumno</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <form method="POST" action="<%=request.getContextPath()%>/alumnos?action=update&id_alum=<%=alumno.getIdParticipante()%>&id_uni=<%=iduniversidad%>">
                        <div class="form-group">
                            <label for="nombrealumno"><b>Nombre</b></label>
                            <input class="form-control" type="text" name="nombrealumno" id="nombrealumno" value="<%=alumno.getNombre()%>">
                        </div>
                        <div class="form-group">
                            <label for="apellidoalumno"><b>Apellido</b></label>
                            <input class="form-control" type="text" name="apellidoalumno" id="apellidoalumno" value="<%=alumno.getApellido()%>">
                        </div>
                        <div class="form-group">
                            <label for="edadalumno"><b>Edad</b></label>
                            <input class="form-control" type="text" name="edadalumno" id="edadalumno" value="<%=alumno.getEdad()%>">
                        </div>
                        <div class="form-group">
                            <label for="codigoalumno"><b>Código</b></label>
                            <input class="form-control" type="text" name="codigoalumno" id="codigoalumno" value="<%=alumno.getAlumno().getCodigo()%>">
                        </div>

                        <div class="form-group">
                            <label for="promedioparticipante"><b>Promedio Ponderado</b></label>
                            <input class="form-control" type="text" name="promedioparticipante" id="promedioparticipante" value="<%=alumno.getAlumno().getPromedioPonderado()%>">
                        </div>

                        <button type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn btn-danger" href="<%=request.getContextPath()%>/alumnos?id_uni=<%=iduniversidad%>">Cancelar</a>
                    </form>
                </div>
            </div>
        </div>


        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se añadió el participante correctamente</h5>
                    <a href="<%= request.getContextPath()%>/alumnos" class="btn btn-success mb-2" >Aceptar</a>

                </div>
            </div>
        </nav>
        <% } else if (mensaje.equals("error")){%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Por favor llenar todos los campos </h5>
                    <a href="#popup"  class="btn btn-success mb-2" >Aceptar</a>

                </div>
            </div>
        </nav>

        <% } else {%>
        <% } %>
        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>

