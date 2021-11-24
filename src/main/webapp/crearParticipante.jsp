<%@ page import="com.example.lab_9.Beans.BPais" %>
<%@ page import="com.example.lab_9.Beans.BContinente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BContinente>" scope="request" id="listacontinentes"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BPais>" scope="request" id="listapaises"/>

<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Crear Participante"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="participantes"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Crear Participante</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <form method="POST" action="<%=request.getContextPath()%>/participante?action=crear">
                        <div class="form-group">
                            <label for="nombreparticipante"><b>Nombre</b></label>
                            <input class="form-control" type="text" name="nombreparticipante" id="nombreparticipante">
                        </div>
                        <div class="form-group">
                            <label for="apellidoparticipante"><b>Apellido</b></label>
                            <input class="form-control" type="text" name="apellidoparticipante" id="apellidoparticipante">
                            <select class="form-select form-select-sm" name="continente">
                                <% for (BContinente cont : listacontinentes) { %>
                                <option value="<%=cont.getIdContinente()%>"><%=cont.getContinente() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="edadparticipante"><b>Edad</b></label>
                            <input class="form-control" type="text" name="edadparticipante" id="edadparticipante" >
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Nacionalidad</b></label>
                            <select class="form-select form-select-sm" name="paisparticipante">
                                <% for (BPais pais : listapaises) { %>
                                <option value="<%=pais.getIdPais()%>"><%=pais.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Género</b></label>
                            <select class="form-select form-select-sm" name="paisparticipante">
                                <option value="M">Hombre</option>
                                <option value="F">Mujer</option>
                                <option value="Otro">Otros</option>
                            </select>
                        </div>


                        <button type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn btn-danger" href="<%=request.getContextPath()%>/participantes">Cancelar</a>
                    </form>
                </div>
            </div>
        </div>


        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha editado el pais correctamente</h5>
                    <a href="<%= request.getContextPath()%>/paises" class="btn btn-success mb-2" >Aceptar</a>

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

