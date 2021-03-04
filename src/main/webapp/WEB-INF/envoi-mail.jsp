<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

    <%@ include file="navbar.jsp" %>

    <main role="main" class="container">
        <form class="form-group" method="post" action="<%out.write(Pages.MAIL.client());%>" name="mailTo">

            <div class="form-group">
                <label class="label" for="emetteur">Émetteur</label>
                <select class="form-control custom-select" id="emetteur" name="emetteur" required>
                    <option selected value="${sessionScope.user.getMail()}">${sessionScope.user.toString()} &lt;${sessionScope.user.getMail()}&gt;</option>
                    <option value="contact@ensibs.fr">Contact &lt;contact@ensibs.fr&gt; </option>
                </select>
            </div>

            <div class="form-group">
                <label class="label" for="destinataire">Destinataire</label>
                <select class="form-control custom-select" id="destinataire" name="destinataire" required>
                    <%
                        String mailto = (String) request.getAttribute("mailto");
                        out.write(mailto);
                    %>
                    <%
                        String listing = (String) request.getAttribute("listing");
                        out.write(listing);
                    %>
                </select>
            </div>

            <div class="form-group">
                <label class="label" for="carbonCopy">Cc</label>
                <select class="form-control custom-select" id="carbonCopy" name="carbonCopy">
                    <option selected value=""></option>
                    <%
                        out.write(listing);
                    %>
                </select>
            </div>

            <div class="form-group">
                <label class="label" for="contenuMail">Message</label>
                <textarea type="text" id="contenuMail" name="contenuMail" class="form-control" rows="6" required autofocus></textarea>
            </div>

            <div class="text-right">
                <button class="btn btn-ensibs" type="submit">Envoyer</button>
            </div>
        </form>
        <%
            String alert = (String) request.getAttribute("alert");
            out.write(alert);
        %>
    </main>

    <%@ include file="footer.jsp" %>

</body>

</html>
