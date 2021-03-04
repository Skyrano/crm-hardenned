<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<%@ include file="WEB-INF/header.jsp" %>

<body class="page_body">

<nav class="navbar navbar-expand-md navbar-dark bg-barre fixed-top justify-content-between">
    <a href="<%out.write(Pages.ACCUEIL.client());%>"><img src="./styles/img/logo.jpg" alt="Logo ENSIBS" id="logo"></a>
    <h2><a class="navbar-brand" href="<%out.write(Pages.ACCUEIL.client());%>">CLI CRM</a></h2>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu" aria-controls="menu" aria-expanded="false">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="menu">
    </div>
</nav>

<main role="main" class="container">
    <div class="text-center mb-4">
        <h1> Une erreur est arrivée lors de votre navigation !</h1>
    </div>

</main>

<%@ include file="WEB-INF/footer.jsp" %>
<script type="text/javascript">
</script>

</body>

</html>


