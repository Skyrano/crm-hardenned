<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String attribut;
    try {
        attribut = (String) request.getAttribute("listeRoles");
    } catch (NullPointerException e) {
        attribut = "";
    }
    String creation = (String) request.getAttribute("creation");
    if(creation == null)
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
        <h3>Création d'un utilisateur </h3>
        <div>
            <hr width="100%">
            <h5>Information de l'utilisateur</h5>
            <h6> <% out.write(creation); %></h6>
            <form class="form-signin" method="post" action="<%out.write(Pages.CREATION_UTILISATEUR.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="identifiant">Identifiant</label>
                        <input type="text" class="form-control" id="identifiant" name="identifiant"
                               placeholder="Identifiant de l'utilisateur"
                               maxlength="80" <%out.write((String)request.getAttribute("identifiant"));%> required
                               autofocus>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="motDePasse">Mot de passe</label>
                        <input type="password" class="form-control" id="motDePasse" name="motDePasse"
                               placeholder="Mot de passe de l'utilisateur"
                               maxlength="80" required>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="nom">Nom de l'utilisateur</label>
                        <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom de l'utilisateur"
                               maxlength="40"<%out.write((String)request.getAttribute("nom"));%> required>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="prenom">Prénom de l'utilisateur</label>
                        <input type="text" class="form-control" id="prenom" name="prenom"
                               placeholder="Prénom de l'utilisateur"
                               maxlength="40" <%out.write((String)request.getAttribute("prenom"));%> required>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="poste">Poste de l'utilisateur</label>
                        <input type="text" class="form-control" id="poste" name="poste" placeholder="Poste occupé"
                               maxlength="40" <%out.write((String)request.getAttribute("poste"));%>>
                    </div>
                </div>
                <%
                    out.write(attribut);
                %>
                <div class="form-row">
                    <div class="form-group col-md-9">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                               maxlength="80" <%out.write((String)request.getAttribute("email"));%> required>
                    </div>
                </div>
                <hr width="100%">
                <div class="form-group col-md-5">
                    <label for="id">Votre identifiant</label>
                    <input type="text" class="form-control" id="id" name="id" maxlength="40">
                </div>
                <div class="form-group col-md-5">
                    <label for="mdp">Votre mot de passe</label>
                    <input type="text" class="form-control" id="mdp" name="mdp" maxlength="80" >
                </div>

                <button type="submit" class="btn btn-ensibs mb-4">Créer</button>
                <a href="<%out.write(Pages.LISTE_UTILISATEURS.client());%>" class="btn btn-secondary mb-4"> Retour</a>
            </form>
        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>

