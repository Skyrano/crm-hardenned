<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ page import="donnees.Evenement" %>
<%@ page import="donnees.Pages" %>
<%
    Evenement evenement = (Evenement) request.getAttribute("evenement");
%>

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>


<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Modification d'un évènement</h3>
        <div>
            <hr width="100%">
            <h6>Informations générales</h6>
            <form method="post" action="<%out.write(Pages.MODIFIER_EVENEMENT.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="nom">Nom</label>
                        <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom"
                               value="<%out.write(evenement.getNom());%>" readonly>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="date">Date</label>
                        <input type="text" class="form-control" id="date" name="date" placeholder="jour/mois/année"
                               value="<%out.write(evenement.getDateString());%>" readonly>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="heure">Heure</label>
                        <input type="text" class="form-control" id="heure" name="heure" placeholder="heures:minutes"
                               value="<%out.write(evenement.getHeureString());%>">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="type">Type</label>
                        <input type="text" class="form-control" id="type" name="type" placeholder="Type"
                               value="<%out.write(evenement.getType());%>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control" id="description" name="description"
                              rows="3"><%out.write(evenement.getDescription());%></textarea>
                </div>
                <h6>Adresse de l'évènement</h6>
                <div class="form-row">
                    <div class="form-group col-md-1">
                        <label for="numRue">Numéro</label>
                        <input type="text" class="form-control" id="numRue" name="numRue" placeholder="Num."
                               value="<%out.write(String.valueOf(evenement.getAdresse().getNumeroRue()));%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="rue">Rue</label>
                        <input type="text" class="form-control" id="rue" name="rue"
                               placeholder="Ex: Boulevard Flandres Dunkerque 1940"
                               value="<%out.write(evenement.getAdresse().getRue());%>">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="codePostal">Code Postal</label>
                        <input type="text" class="form-control" id="codePostal" name="codePostal" placeholder="xxxxx"
                               value="<%out.write(String.valueOf(evenement.getAdresse().getCodePostal()));%>">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="ville">Ville</label>
                        <input type="text" class="form-control" id="ville" name="ville" placeholder="Ville"
                               value="<%out.write(evenement.getAdresse().getVille());%>">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="pays">Pays</label>
                        <input type="text" class="form-control" id="pays" name="pays" placeholder="Pays"
                               value="<%out.write(evenement.getAdresse().getPays());%>">
                    </div>
                    <hr width="100%">
                    <button type="submit" class="btn btn-ensibs mb-4">Enregistrer</button>
                </div>
            </form>

        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>
