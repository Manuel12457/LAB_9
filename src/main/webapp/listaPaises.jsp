<%@ page import="com.example.lab_9.Beans.BPais" %>
<%@ page import="com.example.lab_9.Beans.BContinente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BContinente>" scope="request" id="listacontinentes"/>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Beans.BPais>" scope="request" id="listpaises"/>

<html>
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="Lista de Paises"/>
    </jsp:include>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value="paises"/>
            </jsp:include>

            <div class="mt-2 text-center">
                <h1>Lista de Paises</h1>
            </div>

            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <div class="d-flex justify-content-between align-items-center">
                        <div class="my-2">
                            <a href="<%=request.getContextPath()%>/paises?action=formCrear" class="btn btn-info">Añadir Pais</a>
                        </div>

                        <form method="POST" action="<%=request.getContextPath()%>/paises?action=filtrarpaises">
                            <select class="form-select form-select-sm" name="id_continente">
                                <% for (BContinente listcont : listacontinentes) { %>
                                <option value="<%=listcont.getIdContinente() %>"><%=listcont.getContinente() %>
                                </option>
                                <% } %>
                            </select>
                            <button type="submit" class="btn btn-primary">Buscar por Continente</button>
                        </form>

                    </div>




                    <table class="table table-dark table-transparent table-hover">
                        <thead>
                            <tr>
                                <th>Nombre del País</th>
                                <th>Continente</th>
                                <th>Poblacion</th>
                                <th>tamaño del país</th>
                            </tr>
                        </thead>

                        <%
                            for (BPais listpais : listpaises) {
                        %>

                        <tbody>
                            <tr>
                                <td><%=listpais.getNombre()%>
                                </td>
                                <td><%=listpais.getContinente().getContinente()%>
                                </td>
                                <td><%=listpais.getPoblacion()%>
                                </td>
                                <td><%=listpais.getTamanho()%>
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/paises?action=formEditar&id_pais=<%=listpais.getIdPais()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/paises?action=borrar&id_pais=<%=listpais.getIdPais()%>"
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
        <jsp:include page="/static/scripts.jsp"/>
    </body>
</html>
