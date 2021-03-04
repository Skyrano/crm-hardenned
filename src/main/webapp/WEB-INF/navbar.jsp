<%@ page import="donnees.Utilisateur" %>
<%@ page import="donnees.Pages" %>
<%@ page pageEncoding="UTF-8" %>
<nav class="navbar navbar-expand-md navbar-dark bg-barre fixed-top justify-content-between">
    <a href="accueil"><img src="./styles/img/logo.jpg" alt="Logo ENSIBS" id="logo"></a>
    <h2><a class="navbar-brand" href="accueil">CLI CRM</a></h2>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu" aria-controls="menu" aria-expanded="false">
    <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">
        <ul class="navbar-nav ml-auto mr-0">
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.ACCUEIL.client());%>">Accueil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.RECHERCHER_CONTACT.client());%>">Contacts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.RECHERCHER_ENTREPRISE.client());%>">Entreprises</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.LISTE_EVENEMENTS.client());%>">Événements</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.MAIL.client());%>">Mail</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%out.write(Pages.STATISTIQUES.client());%>">Statistiques</a>
            </li>
            <li class="nav-item marginMenuUtilisateur">
                <a class="nav-link" href="<%out.write(Pages.A_PROPOS.client());%>">A propos</a>
            </li>
            <li class="nav-item dropdown marginMenuUtilisateur">
                <a class="nav-link dropdown-toggle" href="#" id="menuUtilisateur" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> ${sessionScope.user.toString()}</a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="menuUtilisateur">
                    <a class="dropdown-item" href="<%out.write(Pages.PROFIL.client());%>">Profil</a>
                    <%
                        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
                        if (utilisateur != null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")))
                            out.write("<a class=\"dropdown-item\" href='" + Pages.ADMIN.client() + "'>Admin Panel</a>");
                    %>
                    <a class="dropdown-item" href="<%out.write(Pages.LOGOUT.client());%>">Se déconnecter</a>
                </div>
            </li>
        </ul>
    </div>
</nav>


