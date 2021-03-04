<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ page import="donnees.Evenement" %>
<%
    Evenement evenement = (Evenement) request.getAttribute("evenement");
    String listingUtilisateurs = (String) request.getAttribute("listingUtilisateurs");
%>

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>



<main role="main" class="container">

    <div class="page-header">
        <div class="float-left">
            <h1> Ajouter un utilisateur à l'événement <% out.write(evenement.getNom()); %> </h1>
        </div>
        <div class="clearfix"></div>
    </div>
    <%
        String alert = (String) request.getAttribute("alert");
        out.write(alert);
    %>
    <hr width=\"100%\">
    <% out.write(listingUtilisateurs); %>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>
