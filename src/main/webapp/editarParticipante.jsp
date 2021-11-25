<%@ page import="com.example.lab_9.Beans.BPais" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean id="participante" scope="request" type="com.example.lab_9.Beans.BParticipante"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BPais>" scope="request" id="listapaises"/>
<jsp:useBean type="java.lang.String" scope="request" id="errEdadNumero"/>
<jsp:useBean type="java.lang.String" scope="request" id="errEdadMayor18"/>
<jsp:useBean type="java.lang.String" scope="request" id="errNombreIniciaPalabra"/>
<jsp:useBean type="java.lang.String" scope="request" id="errApellidoIniciaPalabra"/>

<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Edicion de Participantes"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="participantes"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Editar Participante</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <%String edad;
                        if (participante != null) {
                            if (participante.getEdad() == 0) {
                                edad = "";
                            } else {
                                edad = Integer.toString(participante.getEdad()) == null ? "" : Integer.toString(participante.getEdad());
                            }
                        } else {
                            edad = "";
                        }
                    %>

                    <form method="POST" action="<%=request.getContextPath()%>/participantes?action=update&id_part=<%=participante.getIdParticipante()%>">
                        <input class="form-control" type="text" name="id" id="id" hidden value="<%=participante.getIdParticipante()%>">
                        <div class="form-group">
                            <label for="nombrepart">Nombre</label>
                            <input class="form-control" type="text" name="nombrepart" id="nombrepart" value="<%=participante.getNombre()%>" placeholder="Ingrese el nombre del participante" required maxlength="50">
                        </div>
                        <div class="form-group">
                            <label for="apellpart">Apellido</label>
                            <input class="form-control" type="text"  name="apellpart" id="apellpart" value="<%=participante.getApellido()%>" placeholder="Ingrese el apellido del participante" required maxlength="50">
                        </div>
                        <div class="form-group">
                            <label for="edad">Edad</label>
                            <input class="form-control" type="text" name="edad" id="edad" value="<%=edad%>" placeholder="Ingrese la edad del participante" required maxlength="2">
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Nacionalidad</b></label>
                            <select class="form-select form-select-sm" name="paisparticipante">
                                <% for (BPais pais : listapaises) { %>
                                <option value="<%=pais.getIdPais()%>" <%=participante.getPais().getIdPais() == pais.getIdPais() ? "selected" : ""%>  ><%=pais.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Género</b></label>
                            <select class="form-select form-select-sm" name="generoparticipante">

                                <option value="M" <%=participante.getGenero().equals("M") ? "selected" : ""%>>Hombre</option>
                                <option value="F" <%=participante.getGenero().equals("F") ? "selected" : ""%>>Mujer</option>
                                <option value="Otro" <%=participante.getGenero().equals("Otro") ? "selected" : ""%>>Otro</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn btn-danger" href="<%=request.getContextPath()%>/participantes">Cancelar</a>

                    </form>
                    <%if (errNombreIniciaPalabra.equals("1")) {%>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        El nombre del participante debe iniciar con una letra
                    </div>
                    <%}%>
                    <%if (errApellidoIniciaPalabra.equals("1")) {%>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        El apellido del participante debe iniciar con una letra
                    </div>
                    <%}%>
                    <%
                        if (errEdadNumero.equals("1")) {
                    %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        La edad ingresada debe contener unicamente números
                    </div>
                    <%
                    } else {
                        if (errEdadMayor18.equals("1")) {
                    %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        El participante debe ser mayor a 18 años
                    </div>
                    <%
                            }
                        }%>
                </div>
            </div>
        </div>


        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha editado el participante correctamente</h5>
                    <a href="<%= request.getContextPath()%>/participantes" class="btn btn-success mb-2" >Aceptar</a>

                </div>
            </div>
        </nav>
        <% } %>

        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>
