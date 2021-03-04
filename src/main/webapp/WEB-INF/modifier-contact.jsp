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
            <h3>Modification d'un contact</h3>
            <div>
                <hr width="100%">
                <h5>Information du contact</h5>
                <form id="modifier-contact" class="form-signin" method="post" action="<%out.write(Pages.MODIFIER_CONTACT.client());%>">
                    <div class="form-row">
                        <div class="form-group col-md-5">
                            <label for="nom">Nom du contact</label>
                            <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom du contact" maxlength="40" <%out.write((String)request.getAttribute("nom"));%> required autofocus>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="prenom">Prénom du contact</label>
                            <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Prénom du contact" maxlength="40" <%out.write((String)request.getAttribute("prenom"));%> required>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="dateDeNaissance">Date de naissance</label>
                            <input type="text" class="form-control" id="dateDeNaissance" name="dateDeNaissance" placeholder="JJ-MM-AAAA" maxlength="10" <%out.write((String)request.getAttribute("dateDeNaissance"));%>>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="entreprise">Nom de l'entreprise</label>
                            <input type="text" class="form-control" id="entreprise" name="entreprise" placeholder="Entreprise" maxlength="40" <%out.write((String)request.getAttribute("nomEntreprise"));%>>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="creationEntreprise">Créer une nouvelle entreprise</label>
                            <button onclick="callCreationEntreprise()"  id="creationEntreprise" class="btn btn-ensibs">Créer une nouvelle entreprise</button>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="statut">Statut au sein de l'entreprise</label>
                            <input type="text" class="form-control" id="statut" name="statut" placeholder="Statut" maxlength="100" <%out.write((String)request.getAttribute("statut"));%>>
                        </div>
                    </div>
                    <%
                        String attribut = (String) request.getAttribute("listeMultipleEntreprise");
                        out.write( attribut );
                    %>

                    <div class="form-row">
                        <div class="form-group col-md-9">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" maxlength="80" <%out.write((String)request.getAttribute("mail"));%> required>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="numTelephone">Numéro de Téléphone</label>
                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <input type="text" class="form-control" id="indicateur" name="indicateur" placeholder="+XX" maxlength="4" <%out.write((String)request.getAttribute("indicateur"));%>>
                                </div>
                                <div class="form-group col-md-9">
                                    <input type="text" class="form-control" id="numTelephone" name="numTelephone" placeholder="Numéro de Téléphone" maxlength="10" <%out.write((String)request.getAttribute("numTel"));%>>
                                </div>
                            </div>


                        </div>
                    </div>

                    <hr width="100%">
                    <input type='hidden' id='idContactHidden' name='idContact' value='<%out.write((String)request.getAttribute("idContact"));%>'>
                    <button type="submit" class="btn btn-ensibs mb-4">Modifier</button>

                </form>
            </div>
        </div>

    </main>



    <%@ include file="footer.jsp" %>
    <script>
        function callModifierContact(){
            document.getElementById("modifier-contact").action = "<%out.write(Pages.MODIFIER_CONTACT.client());%>"
            document.getElementById("modifier-contact").submit;
        }
        function callCreationEntreprise(){
            document.getElementById("modifier-contact").action = "<%out.write(Pages.CREATION_ENTREPRISE_FROM_CONTACT.client());%>"
            document.getElementById("modifier-contact").submit;
        }
    </script>
</body>

</html>

