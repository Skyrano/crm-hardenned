<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

    <%@ include file="navbar.jsp" %>

    <main role="main" class="container">

        <div class="form-row">
            <div class="form-group col-md-10">
                <h3> <%out.write((String)request.getAttribute("nomPrenom"));%> </h3>
                <h6> <%out.write((String)request.getAttribute("dateDeNaissance"));%> </h6>
            </div>
            <div class="form-group col-md-2">
                <%
                    if(utilisateur.getRole().getAccesEcriture())
                        out.println("<td><a href='"+Pages.MODIFIER_CONTACT.client()+"?idContact=" + (request.getParameter("idContact"))+  "' class='btn btn-success btn-ensibs btn-sm' role='button'>Modifier</a></td>");%>

            </div>
        </div>
        <hr width="100%">
        <h3>Contact</h3>
        <div class="form-row">
            <div class="form-group col-md-8">
                <label>E-mail</label><br />
                   <form action='<%out.write(Pages.MAIL.client());%>' method='post' id="formMail">
                        <input type='hidden' id='NomPrenom' name='NomPrenom' value='<%
                            String nomPrenom = (String)request.getAttribute("nomPrenom");
                            if (nomPrenom != null)
                                out.write(nomPrenom);
                        %>'>
                        <input type='hidden' id='mail' name='mail' value='<%
                            String emailContact = (String) request.getAttribute("emailContact");
                            if (emailContact != null)
                                out.write(emailContact);
                        %>'>
                   </form>
                    <a class="contactPolice" href='#' onclick="document.getElementById('formMail').submit()"><%out.write((String)request.getAttribute("emailContact"));%></a>
            </div>
            <div class="form-group col-md-4">
                <label>Numéro de téléphone</label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("numTel"));%></label>
            </div>
        </div>
        <%out.write((String)request.getAttribute("entrepriseAssocie"));%>
        <%out.write((String)request.getAttribute("commentaire"));%>
        <hr width="100%">
        <h4>Catégories</h4>
        <div class="form-row mb-4 mt-1">
            <%@ include file="categories.jsp" %>
        </div>

    </main>

    <%@ include file="footer.jsp" %>
    <%out.write((String)request.getAttribute("scriptCommentaire"));%>

</body>

</html>
