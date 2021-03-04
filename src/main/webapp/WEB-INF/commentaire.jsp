<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

    <%@ include file="navbar.jsp" %>

    <main role="main" class="container">
        <form class="form-group col-md-8 offset-md-2" method="post" action="<%out.write(Pages.COMMENTAIRE.client());%>" name="commentaire">

            <input type="hidden" id="IdContact" name="contactId" value="<% out.write((String) request.getAttribute("contactId")); %>">
            <input type="hidden" id="IdEntreprise" name="entrepriseId" value="<% out.write((String) request.getAttribute("entrepriseId")); %>">
            <input type="hidden" id="NomEvenement" name="evenementNom" value="<% out.write((String) request.getAttribute("evenementNom")); %>">
            <input type="hidden" id="DateEvenement" name="evenementDate" value="<% out.write((String) request.getAttribute("evenementDate")); %>">

            <div class="form-group">
                <label class="label" for="intitule">Intitulé</label>
                <input type="text" id="intitule" name="intitule" class="form-control" required autofocus>
            </div>

            <div class="form-group">
                <label class="label" for="contenu">Commentaire</label>
                <textarea type="text" id="contenu" name="contenu" class="form-control" rows="6" required></textarea>
            </div>

            <div class="text-right">
                <button class="btn btn-ensibs" type="submit">Valider</button>
                <a class="btn btn-secondary" href="<% out.write((String) request.getAttribute("lastPage")); %>" role="button">Retour</a>
            </div>
            <%
                String alert = (String) request.getAttribute("alert");
                out.write(alert);
            %>
        </form>
    </main>

    <%@ include file="footer.jsp" %>

</body>

</html>
