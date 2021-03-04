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
                <h3><%out.write((String)request.getAttribute("nom"));%></h3>
            </div>
            <div class="form-group col-md-2">
                <%
                    if(utilisateur.getRole().getAccesEcriture())
                        out.println("<td><a href='"+ Pages.MODIFIER_ENTREPRISE.client() +"?idEntreprise="+request.getParameter("idEntreprise")+ "' class='btn btn-success btn-ensibs btn-sm' role='button'>Modifier</a></td>");%>
            </div>
        </div>
        <hr width="100%">
        <h3>Entreprise</h3>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label>Domaine de l'entreprise</label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("domaine"));%></label>
            </div>
            <div class="form-group col-md-4">
                <label>N° Siret</label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("numSiret"));%></label>
            </div>
            <div class="form-group col-md-4">
                <label>Nb d'employés</label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("nbEmploye"));%></label>
            </div>
        </div>
        <hr width="100%">
        <h4>Contact de l'entreprise</h4>
        <div class="form-row">
            <div class="form-group col-md-8">
                <label class="surChampPolice">E-mail</label><br />
                <form action='<%out.write(Pages.MAIL.client());%>' method='post' id="formMail">
                    <input type='hidden' id='NomPrenom' name='NomPrenom' value='<%
                            String nomPrenom = (String)request.getAttribute("nom");
                            if (nomPrenom != null)
                                out.write(nomPrenom);
                        %>'>
                    <input type='hidden' id='mail' name='mail' value='<%
                            String emailContact = (String) request.getAttribute("emailEntreprise");
                            if (emailContact != null)
                                out.write(emailContact);
                        %>'>
                </form>
                <a class="contactPolice" href='#' onclick="document.getElementById('formMail').submit()"><%out.write((String)request.getAttribute("emailEntreprise"));%></a>
            </div>
            <div class="form-group col-md-4">
                <label>Adresse de l'entreprise</label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("rue"));%></label><br />
                <label class="contactPolice"><%out.write((String)request.getAttribute("ville"));%></label><br/>
                <label class="contactPolice"><%out.write((String)request.getAttribute("pays"));%></label><br/>
            </div>
        </div>

        <%out.write((String)request.getAttribute("groupe"));%>
        <%out.write((String)request.getAttribute("employees"));%>
        <%out.write((String)request.getAttribute("commentaire"));%>
        <hr width="100%">
        <h4>Catégories</h4>
        <div class="form-row mb-4 mt-1">
            <%@ include file="categories.jsp" %>
        </div>

    </main>

    <%@ include file="footer.jsp" %>
    <%out.write((String)request.getAttribute("scriptCommentaire"));%>
    <%out.write((String)request.getAttribute("scriptEmploye"));%>

</body>

</html>
