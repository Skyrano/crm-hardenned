<%@ page contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<!doctype html>
<html lang='fr'>

<%@ include file="header.jsp" %>

<body class='page_body'>

<%@ include file="navbar.jsp" %>

    <main role='main' class='container'>

        <div class='starter-template'>
            <h1>Accueil</h1>
            <p class='lead'>Bienvenue sur la page d'accueil de CLI CRM!</p>
        </div>
        <div class='container'>
            <div class='row'>
                <div class='col'>
                    <%
                        String liste1 = (String) request.getAttribute("prochainsEvenementsENSIBS");
                        out.write( liste1 );
                    %>
                </div>
                <div class='col'>
                    <%
                        String liste2 = (String) request.getAttribute("prochainsEvenementsUtilisateur");
                        out.write( liste2 );
                    %>
                </div>
                <div class='col'>
                    <%
                        String liste3 = (String) request.getAttribute("derniersContacts");
                        out.write( liste3 );
                    %>
                </div>
            </div>
        </div>
    </main>


    <%@ include file="footer.jsp" %>
</body>

</html>
