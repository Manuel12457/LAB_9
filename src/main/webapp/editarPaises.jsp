<%@ page import="com.example.lab_9.Beans.BPais" %>
<%@ page import="com.example.lab_9.Beans.BContinente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean id="bPais" scope="request" type="com.example.lab_9.Beans.BPais" class="com.example.lab_9.Beans.BPais"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BContinente>"
             scope="request" id="listacontinentes"/>
<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Edicion de Paises"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="paises"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Editar País</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <form method="POST"
                          action="<%=request.getContextPath()%>/paises?action=update&id_pais=<%=bPais.getIdPais()%>">
                        <div class="form-group">
                            <label for="nombrepais"><b>Nombre</b></label>
                            <input class="form-control" type="text" disabled name="nombrepais" id="nombrepais"
                                   value="<%=bPais.getNombre()%>">
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Continente</b></label>
                            <select class="form-select form-select-sm" name="continente">
                                <% for (BContinente cont : listacontinentes) { %>
                                <option value="<%=cont.getIdContinente()%>" <%=bPais.getContinente().getIdContinente() == cont.getIdContinente() ? "selected" : ""%> ><%=cont.getContinente() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="poblacion"><b>Poblacion</b></label>
                            <input class="form-control" type="text" name="poblacion" id="poblacion"
                                   value="<%=bPais.getPoblacion()%>">
                        </div>
                        <div class="form-group">
                            <label for="tamanho"><b>Tamaño del país en km^2</b></label>
                            <input class="form-control" type="text" name="tamanho" id="tamanho"
                                   value="<%=bPais.getTamanho()%>">
                        </div>

                        <button type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn btn-danger" href="<%=request.getContextPath()%>/paises">Cancelar</a>
                    </form>
                </div>
            </div>
        </div>


        <% if (mensaje.equals("ok")) {%>
        <nav id="popup" class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha editado el pais correctamente</h5>
                    <a href="<%= request.getContextPath()%>/paises" class="btn btn-success mb-2">Aceptar</a>

                </div>
            </div>
        </nav>
        <% } else if (mensaje.equals("error")) {%>
        <nav id="popup" class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Por favor llenar todos los campos </h5>
                    <a href="#popup" class="btn btn-success mb-2">Aceptar</a>

                </div>
            </div>
        </nav>

        <% } else {%>
        <% } %>
        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>
