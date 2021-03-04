<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String verif = (String) request.getAttribute("verif");
    if (verif == null)
        verif = "";

%>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Modification d'un utilisateur</h3>
        <p><% out.write(verif);%></p>
        <div>
            <hr width="100%">
            <h5>Information de l'utilisateur</h5>
            <form class="form-signin" method="post" action="<%out.write(Pages.MODIFIER_UTILISATEUR.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="identifiant">Identifiant de l'utilisateur</label>
                        <input type="text" readonly class="form-control" id="identifiant" name="identifiant"
                               placeholder="Identifiant de l'utilisateur" maxlength="80"
                               value=<%out.write((String)request.getAttribute("identifiant")==null?"":(String)request.getAttribute("identifiant"));%> autofocus>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="nom">Nom de l'utilisateur</label>
                        <input type="text" class="form-control" id="nom" name="nom" maxlength="40"
                               value=<%out.write((String)request.getAttribute("nom")==null?"":(String)request.getAttribute("nom"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="prenom">Prénom de l'utilisateur</label>
                        <input type="text" class="form-control" id="prenom" name="prenom"
                               placeholder="Prénom du contact" maxlength="40"
                               value=<%out.write((String)request.getAttribute("prenom")==null?"":(String)request.getAttribute("prenom"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="mail">Email de l'utilisateur</label>
                        <input type="email" class="form-control" id="mail" name="mail" placeholder="Email"
                               maxlength="80"
                               value=<%out.write((String)request.getAttribute("mail")==null?"":(String)request.getAttribute("mail"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="mdp">Mot de passe utilisateur</label>
                        <input type="email" class="form-control" id="mdp" name="mdp"
                               placeholder="Modifier le mot de passe"
                               maxlength="80">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="poste">Statut au sein de l'entreprise</label>
                        <input type="text" class="form-control" id="poste" name="poste" placeholder="poste"
                               maxlength="40"
                               value= <%out.write((String)request.getAttribute("poste")==null?"":(String)request.getAttribute("poste"));%>>
                    </div>
                </div>

                <hr width="100%">
                <h5>Gestion du rôle</h5>
                <%out.write((String) request.getAttribute("listeRoles"));%>
                <hr width="100%">


                <div class="form-group col-md-5">
                    <label for="idAdmin">Votre identifiant</label>
                    <input type="text" class="form-control" id="idAdmin" name="idAdmin" maxlength="40">
                </div>
                <div class="form-group col-md-5">
                    <label for="mdpAdmin">Votre mot de passe</label>
                    <input type="text" class="form-control" id="mdpAdmin" name="mdpAdmin" maxlength="80">
                </div>

                <button type="submit" class="btn btn-ensibs mb-4">Modifier</button>
                <a href="<%out.write(Pages.LISTE_UTILISATEURS.client());%>" class="btn btn-secondary mb-4"> Retour</a>
            </form>

        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>

