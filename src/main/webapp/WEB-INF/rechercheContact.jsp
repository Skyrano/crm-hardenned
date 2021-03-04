<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String verif = (String) request.getAttribute("verif");
    if(verif==null)
        verif="";

%>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Recherche d'un contact</h3>
        <p> <% out.write(verif);%></p>
        <div>
            <hr width="100%">
            <h5>Information du contact</h5>
            <form class="form-signin" method="post" action="<%out.write(Pages.RECHERCHER_CONTACT.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="nom">Nom du contact</label>
                        <input type="text" class="form-control" id="nom" name="nom"  placeholder="Nom du contact recherché" maxlength="40" value="<%out.write((String)request.getAttribute("nom")==null?"":(String)request.getAttribute("nom"));%>" >
                    </div>
                    <div class="form-group col-md-5">
                        <label for="prenom">Prénom du contact</label>
                        <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Prénom du contact recherché" maxlength="40" value="<%out.write((String)request.getAttribute("prenom")==null?"":(String)request.getAttribute("prenom"));%>" >
                    </div>
                    <div class="form-group col-md-5">
                        <label for="mail">Email du contact</label>
                        <input type="email" class="form-control" id="mail" name="mail" placeholder="Email recherché"
                               maxlength="80" value="<%out.write((String)request.getAttribute("mail")==null?"":(String)request.getAttribute("mail"));%>" >
                    </div>
                    <div class="form-group col-md-5">
                        <label for="statut">Statut du contact</label>
                        <input type="text" class="form-control" id="statut" name="statut" placeholder="Statut du contact recherché"
                               maxlength="100"  value="<%out.write((String)request.getAttribute("statut")==null?"":(String)request.getAttribute("statut"));%>" >
                    </div>
                    <div class="form-group col-md-5">
                        <label for="categorie">Catégorie</label>
                        <input type="text" class="form-control" id="categorie" name="categorie" placeholder="Catégorie de recherche" maxlength="80" value="<%out.write((String)request.getAttribute("categorie")==null?"":(String)request.getAttribute("categorie"));%>" >
                    </div>
                    <div class="form-group col-md-5">
                        <label for="entreprise">Entreprise</label>
                        <input type="text" class="form-control" id="entreprise" name="entreprise" placeholder="Entreprise de recherche" maxlength="40" value="<%out.write((String)request.getAttribute("entreprise")==null?"":(String)request.getAttribute("entreprise"));%>" >
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-3">
                        <label for="numTelephone">Numéro de Téléphone</label>
                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <input type="text" class="form-control" id="indicateur" name="indicateur" placeholder="+XX" maxlength="4" value="<%out.write((String)request.getAttribute("indicateur")==null?"":(String)request.getAttribute("indicateur"));%>">
                            </div>
                            <div class="form-group col-md-9">
                                <input type="text" class="form-control" id="numTelephone" name="numTelephone" placeholder="Numéro de Téléphone" maxlength="10" value="<%out.write((String)request.getAttribute("numTelephone")==null?"":(String)request.getAttribute("numTelephone"));%>">
                            </div>
                        </div>
                    </div>

                    <div class="form-group col-md-2">
                        <label for="dateDeNaissance">Date de naissance</label>
                        <input type="text" class="form-control" id="dateDeNaissance" name="dateDeNaissance" placeholder="JJ-MM-AAAA" maxlength="10" value="<%out.write((String)request.getAttribute("dateDeNaissance")==null?"":(String)request.getAttribute("dateDeNaissance"));%>">
                    </div>
                </div>
                <input type="hidden" id="evenementNomRenvoi" name="evenementNom" value="<%out.write(request.getParameter("evenementNom"));%>">
                <input type="hidden" id="evenementDateRenvoi" name="evenementDate" value="<%out.write(request.getParameter("evenementDate"));%>">

                <div class="row">
                    <button type="submit" class="btn btn-ensibs mb-4">Rechercher</button>
                    <a href='<%out.write(Pages.LISTE_CONTACTS.client());%>' class='btn btn-ensibs ml-4 mb-4'role='button'>Tous les contacts</a>
                    <%
                        if(utilisateur.getRole().getAccesEcriture())
                    out.println("<a href='"+Pages.CREATION_CONTACT.client()+"' class='btn btn-ensibs ml-4 mb-4' role='button'>Créer un contact</a>");%>

                </div>
            </form>
        </div>

        <%out.write((String)request.getAttribute("listing")==null?"":(String)request.getAttribute("listing"));%>
    </div>

</main>



<%@ include file="footer.jsp" %>
</body>

</html>

