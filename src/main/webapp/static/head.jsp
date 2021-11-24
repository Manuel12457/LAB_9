<!-- Cabecera de todas las páginas donde se encuentran declarados los estilos, no tocar -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'/>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/static/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <title><%=request.getParameter("title")%>
    </title>
    <style>

        #popup:target{
            visibility: hidden; /* Se regresa a hidden para ocultar */
            opacity: 0; /* Se regresa a o para hacerlo "invisible" */
        }
        .overlay{
            display: flex; justify-content: center ; align-items: center; position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(0, 0, 0, 0.7);
            transition: opacity 500ms;
            visibility: visible;
            opacity: 1;

        }

        body {
            height: 400px;
            background-image: url("https://images.unsplash.com/20/cambridge.JPG?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1147&q=80");
            background-size: cover;
            background-repeat:no-repeat;
            background-position: center center;
            background-attachment: fixed;
        }
    </style>
</head>
