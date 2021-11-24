
<%@ page import="com.example.lab_9.Dtos.DtoUniversidad" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<com.example.lab_9.Dtos.DtoUniversidad>" scope="request" id="listuniversidad"/>
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
                <h1>Lista de Universidades</h1>
            </div>

            <div class="d-flex justify-content-center">
                <div class="w-75">

                    <div class="my-2">
                        <a href="<%=request.getContextPath()%>/universidades?action=formCrear" class="btn btn-info">Añadir Universidad</a>
                    </div>
                    <div class="tabla">
                        <table class="table table-dark table-transparent table-hover">
                            <thead>
                                <tr>
                                    <th><a href="<%=request.getContextPath()%>/universidades?order=nombre">Nombre</a></th>
                                    <th><a href="<%=request.getContextPath()%>/universidades?order=pais">País</a></th>
                                    <th><a href="<%=request.getContextPath()%>/universidades?order=ranking">ranking</a></th>
                                    <th><a href="<%=request.getContextPath()%>/universidades?order=alumnos">Número de Alumnos</a></th>
                                    <th>Foto de la universidad</th>
                                </tr>
                            </thead>



                            <%
                                for (DtoUniversidad universidad: listuniversidad) {
                            %>
                                <% switch (universidad.getUniversidad().getPais().getContinente().getContinente()) {
                                    case "Africa": %>
                            <tr class="fila-red" style="color: red;font-weight: bold">

                                <td ><%=universidad.getUniversidad().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getPais().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getRanking()%>
                                </td>
                                <td><%=universidad.getAlumnos()%>
                                </td>
                                <td><img src="<%=universidad.getUniversidad().getFoto()%>" width="100">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=formEditar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=borrar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=alumnos&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary">Ver alumnos</a></td>

                            </tr>
                            <%
                                    break;
                                case "America":
                            %>
                            <tr class="fila-purple" style="color: purple;font-weight: bold">

                                <td><%=universidad.getUniversidad().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getPais().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getRanking()%>
                                </td>
                                <td><%=universidad.getAlumnos()%>
                                </td>
                                <td><img src="<%=universidad.getUniversidad().getFoto()%>" width="100">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=formEditar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=borrar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=alumnos&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary">Ver alumnos</a></td>

                            </tr>
                            <%
                                    break;
                                case "Asia":
                            %>
                            <tr class="fila-green" style="color: green;font-weight: bold">

                                <td><%=universidad.getUniversidad().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getPais().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getRanking()%>
                                </td>
                                <td><%=universidad.getAlumnos()%>
                                </td>
                                <td><img src="<%=universidad.getUniversidad().getFoto()%>" width="100">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=formEditar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=borrar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=alumnos&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary">Ver alumnos</a></td>
                            </tr>
                            <%
                                    break;
                                case "Europa":
                            %>
                            <tr class="fila-blue" style="color: blue;font-weight: bold">

                                <td><%=universidad.getUniversidad().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getPais().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getRanking()%>
                                </td>
                                <td><%=universidad.getAlumnos()%>
                                </td>
                                <td><img src="<%=universidad.getUniversidad().getFoto()%>" width="100">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=formEditar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=borrar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=alumnos&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary">Ver alumnos</a></td>
                            </tr>
                            <%
                                    break;
                                default:
                            %>
                            <tr class="fila-yellow" style="color: yellow;font-weight: bold">

                                <td><%=universidad.getUniversidad().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getPais().getNombre()%>
                                </td>
                                <td><%=universidad.getUniversidad().getRanking()%>
                                </td>
                                <td><%=universidad.getAlumnos()%>
                                </td>
                                <td><img src="<%=universidad.getUniversidad().getFoto()%>" width="100">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=formEditar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary"><span class="fa fa-edit"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=borrar&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-danger"><span class="fa fa-trash"></span></a></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/universidades?action=alumnos&id_uni=<%=universidad.getUniversidad().getIdUniversidad()%>"
                                       class="btn btn-primary">Ver alumnos</a></td>
                            </tr>
                            <%
                                        break;
                                }
                            %>


                            <%
                                }
                            %>

                        </table>
                    </div>
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
