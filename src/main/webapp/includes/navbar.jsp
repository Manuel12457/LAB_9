<!-- No tocar -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">WEB APP</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link <%=request.getParameter("page").equals("paises")? "active": "" %>"
                   href="<%=request.getContextPath()%>/paises">Paises</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%=request.getParameter("page").equals("participantes")? "active": "" %>"
                   href="<%=request.getContextPath()%>/participantes">Participantes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%=request.getParameter("page").equals("universidades")? "active": "" %>"
                   href="<%=request.getContextPath()%>/universidades?order=ranking">Universidades</a>
            </li>

        </ul>
    </div>
</nav>

