<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!--Colocar como value: nombre de la página actual -->
    <jsp:include page="/static/head.jsp">
        <jsp:param name="title" value="AllMusic"/>
    </jsp:include>
    <body>
        <div class='container'>
            <!-- Incluir el navbar indicando el nombre de página correcto para que se señale la sección actual -->
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="page" value=""/>
            </jsp:include>

            <div class="container text-center mt-2">
                <h1>Bienvenido a Toda la Música</h1>

                <p>Esta es una página donde puede conocer más sobre sus bandas y artistas favoritos e incluso actualizar nuestra
                    base de datos con las nuevas estrellas.</p>
            </div>


        </div>

        <jsp:include page="/static/scripts.jsp"/>

    </body>
</html>