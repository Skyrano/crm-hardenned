<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ page import="donnees.Evenement" %>
<%@ page import="donnees.Pages" %>
<%
    Evenement evenement = (Evenement) request.getAttribute("evenement");
    String commentaires = (String) request.getAttribute("commentaire");
    String listingContacts = (String) request.getAttribute("listingContacts");
    String listingUtilisateurs = (String) request.getAttribute("listingUtilisateurs");
%>

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>



<main role="main" class="container">

    <div class="page-header">
        <div class="float-left">
            <h1> <% out.write(evenement.getNom()); %> </h1>
        </div>
        <div class="float-right">
            <%
                if(utilisateur.getRole().getAccesEcriture())
                    out.println("<a href='"+Pages.MODIFIER_EVENEMENT.client()+"?nom="+evenement.getNom()+"&date="+evenement.getDate()+"' class='btn btn-ensibs btn-sm' role='button'>Modifier l'évènement</a>");%>
        </div>
        <div class="clearfix"></div>
    </div>
    <%
        String alert = (String) request.getAttribute("alert");
        out.write(alert);
    %>
    <hr width="100%">
    <div class="row">
        <div class="col-md-8">
            <h4> <% out.write("Le "+evenement.getDateString()+ " à " + evenement.getHeureString()); %> </h4>
        </div>
        <div class="col-md-4">
            <h5> <% out.write(evenement.getType()); %> </h5>
        </div>
    </div>
    <hr width="100%">
    <div class="row">
        <div class="col-md-8">
            <h5>Description</h5><br />
            <% out.write(evenement.getDescription()); %>
        </div>
        <div class="col-md-4">
            <h5>Adresse de l'événement</h5><br />
            <label class="contactPolice"><%out.write(evenement.getAdresse().getNumeroRue() + " " + evenement.getAdresse().getRue());%></label><br />
            <label class="contactPolice"><%out.write(evenement.getAdresse().getVille());%></label><br/>
            <label class="contactPolice"><%out.write(evenement.getAdresse().getPays());%></label><br/>
        </div>
    </div>
    <hr width=\"100%\">
    <h4>Contacts présents</h4>
    <% out.write(listingContacts); %>
    <hr width=\"100%\">
    <h4>Utilisateurs présents</h4>
    <% out.write(listingUtilisateurs); %>
    <hr width="100%">
    <% out.write(commentaires); %>
    <a href="<%out.write(Pages.LISTE_EVENEMENTS.client());%>" class="btn btn-secondary mb-4"> Retour</a>
</main>


<%@ include file="footer.jsp" %>
</body>

</html>
