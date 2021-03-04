<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="CLI CRM">
    <meta name="author" content="Yoann KERGOSIEN, Lucas MANGIN, Alistair RAMEAU, Alexis MATIAS GOMES">
    <title>CLI CRM - Login</title>
    <link href="./styles/css/bootstrap.min.css" rel="stylesheet">
    <link href="./styles/css/styles.css" rel="stylesheet">
    <link href="./styles/css/styles-login.css" rel="stylesheet">
</head>

<body>
<form class="form-signin" method="post" action="<%out.write(Pages.ACCUEIL.client());%>" name="login">
    <div class="text-center mb-4">
        <img class="mb-4" src="./styles/img/logo.jpg" alt="Logo ENSIBS">
        <h1 class="h3 mb-3 font-weight-normal">Connexion</h1>
        <p>Bienvenue sur CLI CRM. Pour vous connecter veuillez utiliser votre adresse email ou votre identifiant.</p>
    </div>

    <p><% out.println((String) request.getAttribute("retourConnexion") == null ? "" : (String) request.getAttribute("retourConnexion"));%></p>
    <div class="form-label-group">
        <input type="text" id="inputIdentifiant" name="identifiant" class="form-control" placeholder="Identifiant" required autofocus>
        <label for="inputIdentifiant">Identifiant</label>
    </div>

    <div class="form-label-group">
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Mot de passe"
               required>
        <label for="inputPassword">Mot de passe</label>
    </div>

    <button class="btn btn-lg btn-ensibs btn-block" type="submit">Se connecter</button>
    <p class="mt-5 mb-3 text-muted text-center">&copy; 2020-2021</p>
</form>
</body>
</html>