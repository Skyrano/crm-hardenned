<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="CLI CRM">
    <meta name="author" content="Yoann KERGOSIEN, Lucas MANGIN, Alistair RAMEAU, Alexis MATIAS GOMES">
    <title>CLI CRM</title>
    <link href="./styles/css/bootstrap.min.css" rel="stylesheet">
    <link href="./styles/css/styles.css" rel="stylesheet">
    <link href="./styles/css/styles-login.css" rel="stylesheet">
</head>

<body>
<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 offset-3 float-md-center">
<div class="jumbotron text-center">
    <% out.write((String)request.getAttribute("message")==null?"":(String)request.getAttribute("message"));%>
</div>
</div>
</body>
</html>