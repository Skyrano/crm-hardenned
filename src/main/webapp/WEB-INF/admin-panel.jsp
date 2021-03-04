<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<%
    Boolean importFait = (Boolean) request.getAttribute("dbAjout");

    String test = "";
    if(importFait != null)
        test = importFait ? "Votre fichier a bien été importé !" : "Une erreur s'est produite à l'import";
    Boolean erreurFormat = (Boolean) request.getAttribute("erreurFormat");
    String erreurFormatString = erreurFormat != null && erreurFormat ? "Vos dates doivent être de la forme 'yyyy-[m]m-[d]d'" : "";
%>
<%@ include file="header.jsp" %>


<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">

    <div class="starter-template">
        <h1>Panel d'administration</h1>
        <p class="lead">Gérez ici votre base de données!</p>
    </div>
    <div>
        <h4><%=test%>
        </h4>
        <h1><%= erreurFormatString %>
        </h1>
        <div id="importexport">
            <p> Importer un fichier csv</p>
            <div class="input-group">

                <form class="form-label-group" method="post" action="<%out.write(Pages.IMPORT.client());%>"
                      name="import"
                      enctype="multipart/form-data">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="import" name="import" accept=".csv"
                               onchange="verificationExtension()">
                        <label class="custom-file-label" for="import">Choisir un fichier</label>
                    </div>
                    </br>
                    <button class="btn-secondary input-group-text mt-2" id="btn_import" type="submit" disabled> Veuillez
                        choisir un fichier csv
                    </button>
                </form>
            </div>

                <div class="row mt-2">
                    <div class="card text-center" style="width: 15rem;">
                        <img src="./styles/img/role.png" class="card-img-top adminCard" alt="gestion role">
                        <div class="card-body text-center">
                            <a href="<%out.write(Pages.GESTION_ROLES.client());%>" class="btn btn-ensibs ">Gérer les
                                rôles</a>
                        </div>
                    </div>

                    <div class="card text-center" style="width: 15rem;">
                        <img src="./styles/img/user.png" class="card-img-top adminCard" alt="gestion utilisateur">
                        <div class="card-body text-center">
                            <a href="<%out.write(Pages.LISTE_UTILISATEURS.client());%>" class="btn btn-ensibs"
                               role="button">Gérer les utilisateurs</a>
                        </div>
                    </div>

                    <div class="card text-center" style="width: 15rem;">
                        <img src="./styles/img/export.png" class="adminCard " alt="export contact">
                        <div class="card-body text-center">
                            <a href="<%out.write(Pages.EXPORT.client());%>" class="btn btn-ensibs " role="button">Exporter
                                les contacts</a>
                        </div>
                    </div>

                    <div class="card text-center" style="width: 15rem;">
                        <img src="./styles/img/log.png" class="adminCard " alt="liste logs">
                        <div class="card-body text-center">
                            <a href="<%out.write(Pages.LISTE_LOGS.client());%>" class="btn btn-ensibs " role="button">Liste
                                des logs</a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
</main>

<%@ include file="footer.jsp" %>
<script>
    function verificationExtension() {
        var extension = $("#import").val().split('.')[1];
        if (extension == "csv") {
            $("#btn_import").attr("disabled", false);
            $("#btn_import").removeClass("btn-secondary");
            $("#btn_import").addClass("btn-ensibs");
            $("#btn_import").html("Valider l'import");
        } else {
            $("#btn_import").attr("disabled", true);
            $("#btn_import").removeClass("btn-ensibs");
            $("#btn_import").addClass("btn-secondary");
            $("#btn_import").html("Veuillez choisir un fichier csv");
        }
    }
</script>

</body>

</html>
