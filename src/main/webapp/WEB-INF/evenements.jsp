<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">

    <div class="starter-template">
        <h1>Événements</h1>
        <p class="lead">Bienvenue sur la page des événements de CLI CRM!</p>
        <%
            if(utilisateur.getRole().getAccesEcriture())
                out.println(" <a href='"+Pages.CREATION_EVENEMENT.client()+"' class='btn btn-ensibs mb-4' role='button'>Créer un événement</a>");%>

        <p>     <%
            out.write((String) request.getAttribute("retourCreation")==null?"":(String) request.getAttribute("retourCreation"));
        %></p>
    </div>

    <%
        String attribut = (String) request.getAttribute("listing");
        out.write( attribut );
    %>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>
