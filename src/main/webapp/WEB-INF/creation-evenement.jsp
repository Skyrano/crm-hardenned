<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>


<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Création d'un évènement</h3>
        <div>
            <hr width="100%">
            <h6>Informations générales</h6>
            <form method="post" action="<%out.write(Pages.CREATION_EVENEMENT.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="nom">Nom</label>
                        <input type="text" required maxlength="80" class="form-control" id="nom" name="nom"
                               placeholder="Nom">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="date">Date</label>
                        <input type="text" required maxlength="10" class="form-control" id="date" name="date"
                               placeholder="jour/mois/année">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="heure">Heure</label>
                        <input type="text" required maxlength="5" class="form-control" id="heure" name="heure"
                               placeholder="heures:minutes">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="type">Type</label>
                        <input type="text" required maxlength="40" class="form-control" id="type" name="type"
                               placeholder="Type">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control" required maxlength="1000" id="description" name="description"
                              rows="3"></textarea>
                </div>
                <h6>Adresse de l'évènement</h6>
                <div class="form-row">
                    <div class="form-group col-md-1">
                        <label for="numRue">Numéro</label>
                        <input type="text" required maxlength="10" class="form-control" id="numRue" name="numRue"
                               placeholder="Num.">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="rue">Rue</label>
                        <input type="text" class="form-control" required maxlength="100" id="rue" name="rue"
                               placeholder="Ex: Boulevard Flandres Dunkerque 1940">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="codePostal">Code Postal</label>
                        <input type="text" required maxlength="5" class="form-control" id="codePostal" name="codePostal"
                               placeholder="xxxxx">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ville">Ville</label>
                        <input type="text" required maxlength="50" class="form-control" id="ville" name="ville"
                               placeholder="Ville">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="pays">Pays</label>
                        <input type="text" required maxlength="30" class="form-control" id="pays" name="pays"
                               placeholder="Pays">
                    </div>
                    <hr width="100%">
                </div>
                <button type="submit" class="btn btn-ensibs mb-4">Créer</button>
            </form>
            <a href="<%out.write(Pages.LISTE_EVENEMENTS.client());%>" class="btn btn-secondary mb-4"> Retour</a>
        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>
