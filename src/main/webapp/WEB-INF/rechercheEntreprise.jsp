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
        <h3>Recherche d'une entreprise</h3>
        <p><% out.write(verif);%></p>
        <div>
            <hr width="100%">
            <h5>Information de l'entreprise</h5>
            <form class="form-signin" method="post" action="<%out.write(Pages.RECHERCHER_ENTREPRISE.client());%>">
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="nomGroupe">Groupe</label>
                        <input type="text" class="form-control" id="nomGroupe" name="nomGroupe"
                               placeholder="Nom du groupe de l'entreprise recherchée" maxlength="40"
                               value="<%out.write((String)request.getAttribute("nomGroupe")==null?"":(String)request.getAttribute("nomGroupe"));%>">
                    </div>

                    <div class="form-group col-md-4">
                        <label for="domaine">Domaine</label>
                        <input type="text" class="form-control" id="domaine" name="domaine"
                               placeholder="Domaine de l'entreprise recherchée" maxlength="40"
                               value="<%out.write((String)request.getAttribute("domaine")==null?"":(String)request.getAttribute("domaine"));%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="numSiret">NumSiret</label>
                        <input type="text" class="form-control" id="numSiret" name="numSiret"
                               placeholder="Numéro SIRET de l'entreprise recherchée"
                               maxlength="100"
                               value="<%out.write((String)request.getAttribute("numSiret")==null?"":(String)request.getAttribute("numSiret"));%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="nom">Nom</label>
                        <input type="text" class="form-control" id="nom" name="nom"
                               placeholder="Nom de l'entreprise recherchée" maxlength="40"
                               value="<%out.write((String)request.getAttribute("nom")==null?"":(String)request.getAttribute("nom"));%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="categorie">Catégorie</label>
                        <input type="text" class="form-control" id="categorie" name="categorie"
                               placeholder="Catégorie de l'entreprise recherchée" maxlength="80"
                               value="<%out.write((String)request.getAttribute("categorie")==null?"":(String)request.getAttribute("categorie"));%>">
                    </div>
                </div>

                <div class="form-row">

                    <div class="form-group col-md-4">
                        <label for="pays">Pays</label>
                        <input type="text" class="form-control" id="pays" name="pays" placeholder="Pays de l'entreprise recherchée"
                               maxlength="40"
                               value="<%out.write((String)request.getAttribute("pays")==null?"":(String)request.getAttribute("pays"));%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="codePostal">Code Postal</label>
                        <input type="text" class="form-control" id="codePostal" name="codePostal"
                               placeholder="Code postal de l'entreprise recherchée" maxlength="40"
                               value="<%out.write((String)request.getAttribute("codePostal")==null?"":(String)request.getAttribute("codePostal"));%>">
                    </div>
                </div>

                <div class="row">
                    <button type="submit" class="btn btn-ensibs mb-4">Rechercher</button>
                    <a href='<%out.write(Pages.LISTE_ENTREPRISES.client());%>' class='btn btn-ensibs ml-4 mb-4' role='button'>Toutes les entreprises</a>
                    <%
                        if(utilisateur.getRole().getAccesEcriture())
                            out.println("<a href='"+Pages.CREATION_ENTREPRISE.client()+"' class='btn btn-ensibs ml-4 mb-4' role='button'>Créer une entreprise</a>");%>
                </div>
            </form>
        </div>

        <%
            out.write((String) request.getAttribute("listing") == null ? "" : (String) request.getAttribute("listing"));
        %>
    </div>

</main>


<%@ include file="footer.jsp" %>
</body>

</html>

