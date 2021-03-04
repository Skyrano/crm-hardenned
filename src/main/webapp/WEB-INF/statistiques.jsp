<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>


<main role="main" class="container">
    <h1>Statistiques</h1>
    <!-- paragraphe contenant le lien vers le site de l'API -->
    <p>
        Cette page indique quelques statistiques sur l'utilisations du crm.
    </p>
    <hr width="100%">
    <h3>Entreprises</h3>
    <br>
    <div class="card-deck" >
        <div class="card border-success mb-3 text-center mx-auto" style="max-width: 18rem;">
            <div class="card-header">Nombre de groupes</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("groupesDifferents"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center mx-auto" style="max-width: 18rem;">
            <div class="card-header">Nombre d'entreprise</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entreprisesDifferentes"));%></span></h1>
            </div>
        </div>
    </div>
    <div class="card-deck" >
        <div class="card border-success mb-3 text-center " style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center h-50">Nombre de très petites entreprises (<10)</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entrepriseMoinsDe10"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center " style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center h-50">Nombre de petites entreprises (<50)</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entrepriseMoinsDe50"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center " style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center h-50">Nombre de moyennes entreprises (<150)</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entrepriseMoinsDe150"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center " style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center h-50">Nombre de grandes entreprises (<500)</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entrepriseMoinsDe500"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center " style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center h-50">Nombre de très grandes entreprises (>500)</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("entreprisePlusDe500"));%></span></h1>
            </div>
        </div>
    </div>

    <hr width="100%">
    <h3>Contacts</h3>
    <br>
    <div class="card-deck" >
        <div class="card border-success mb-3 text-center mx-auto" style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center " style="height: 35%">Nombre de contact</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("contactsDifferents"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center mx-auto" style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center" style="height: 35%">Nombre de contact sans entreprises renseignées</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("contactsSansEntreprise"));%></span></h1>
            </div>
        </div>
        <div class="card border-success mb-3 text-center mx-auto" style="max-width: 18rem;">
            <div class="card-header d-flex align-items-center justify-content-center" style="height: 35%">Moyenne de contact par entreprise</div>
            <div class="card-body">
                <h1><span class="card-title badge badge-pill badge-success"><%out.write((String)request.getAttribute("contactParEntrepriseMoyenne"));%></span></h1>
            </div>
        </div>
    </div>

</main>


<%@ include file="footer.jsp" %>

</body>
</html>
