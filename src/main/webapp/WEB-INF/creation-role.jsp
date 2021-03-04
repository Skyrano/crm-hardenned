<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

    String creation = (String) request.getAttribute("creation");
    if (creation == null)
        creation = "";
%>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>


<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Création d'un rôle</h3>
        <div>
            <hr width="100%">
            <h5>Information du rôle</h5>
            <h6><% out.write(creation); %></h6>
            <form class="form-signin" method="post" action="<%out.write(Pages.CREATION_ROLE.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="nom">Nom du rôle</label>
                        <input type="text" class="form-control" id="nom" name="nom"
                               placeholder="Nom du rôle"
                               maxlength="40" <%out.write((String)request.getAttribute("nom"));%> required
                               autofocus>
                        <label for="ecriture"> Accès en écriture ?</label>
                        <input type="checkbox" class=form-control" id="ecriture" name="ecriture" value="Ecriture">
                    </div>
                </div>
                <hr width="100%">
                <button type="submit" class="btn btn-ensibs mb-4">Créer</button>
                <a href="<%out.write(Pages.GESTION_ROLES.client());%>" class="btn btn-secondary mb-4"> Retour</a>
            </form>

        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>

