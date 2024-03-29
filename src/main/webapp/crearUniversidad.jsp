<%@ page import="com.example.lab_9.Beans.BPais" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="mensaje" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BPais>" scope="request" id="listapaises"/>

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
                <h1>Crear Universidad</h1>
            </div>
            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <form method="POST" action="<%=request.getContextPath()%>/universidades?action=crear">
                        <div class="form-group">
                            <label for="nombreuniversidad"><b>Nombre</b></label>
                            <input class="form-control" type="text" name="nombreuniversidad" id="nombreuniversidad" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label"><b>Pais</b></label>
                            <select class="form-select form-select-sm" name="paisuniversidad">
                                <% for (BPais pais : listapaises) { %>
                                <option value="<%=pais.getIdPais()%>"><%=pais.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="rankinguniversidad"><b>Ranking</b></label>
                            <input class="form-control" type="text" name="rankinguniversidad" id="rankinguniversidad"required >
                        </div>

                        <div class="form-group">
                            <label for="fotouniversidad"><b>Foto</b></label>
                            <input class="form-control" type="text" name="fotouniversidad" id="fotouniversidad" required>
                        </div>


                        <button type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn btn-danger" href="<%=request.getContextPath()%>/universidades">Cancelar</a>
                    </form>
                </div>
            </div>
        </div>


        <% if (mensaje.equals("ok")) {%>
        <nav id="popup"  class="overlay">
            <div class=" popup card text-center " style="background-color: white">
                <h5 class="card-header text-center ">Mensaje</h5>
                <div class="card-body">
                    <h5 class="card-title p-2">Se ha creado la universidad correctamente</h5>
                    <a href="<%= request.getContextPath()%>/universidades" class="btn btn-success mb-2" >Aceptar</a>

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

