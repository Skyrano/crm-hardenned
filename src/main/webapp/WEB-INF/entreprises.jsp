<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>


<body class="page_body">

<%@ include file="navbar.jsp" %>



<main role="main" class="container">

    <div class="starter-template">
        <h1>Entreprises</h1>
        <p class="lead">Bienvenue sur la page des entreprises de CLI CRM!</p>
    </div>
    <%
        String attribut = (String) request.getAttribute("listing");
        out.write( attribut );
    %>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>
