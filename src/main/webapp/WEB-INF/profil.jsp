<%@ page import="donnees.Pages" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String verif = (String) request.getAttribute("verif");
    if (verif == null)
        verif = "";

%>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">

    <div>
        <!-- simple paragraphe présentant notre application -->
        <h3>Modification d'un utilisateur</h3>
        <p><% out.write(verif);%></p>
        <div>
            <hr width="100%">
            <h5>Information de l'utilisateur</h5>
            <form class="form-signin" method="post" action="<%out.write(Pages.PROFIL.client());%>">
                <% out.write((String)request.getAttribute("retourMdp")==null?"":(String)request.getAttribute("retourMdp"));%>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="identifiant">Identifiant de l'utilisateur</label>
                        <input type="text" readonly class="form-control" id="identifiant" name="identifiant"
                               placeholder="Identifiant de l'utilisateur" maxlength="80"
                               value=<%out.write((String)request.getAttribute("identifiant")==null?"":(String)request.getAttribute("identifiant"));%> autofocus>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="nom">Nom de l'utilisateur</label>
                        <input type="text" readonly class="form-control" id="nom" name="nom" maxlength="40"
                               value=<%out.write((String)request.getAttribute("nom")==null?"":(String)request.getAttribute("nom"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="prenom">Prénom de l'utilisateur</label>
                        <input type="text" readonly class="form-control" id="prenom" name="prenom"
                               placeholder="Prénom du contact" maxlength="40"
                               value=<%out.write((String)request.getAttribute("prenom")==null?"":(String)request.getAttribute("prenom"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="mail">Email de l'utilisateur</label>
                        <input type="email" class="form-control" id="mail" name="mail" placeholder="Email"
                               maxlength="80" readonly value=<%out.write((String)request.getAttribute("mail")==null?"":(String)request.getAttribute("mail"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="nouvMdp">Nouveau mot de passe</label>
                        <input type="password" oninput="verifSecur()" class="form-control" id="nouvMdp" name="nouvMdp"
                               placeholder="Nouveau le mot de passe"
                               maxlength="80">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="mdpConfirm">Confirmer mot de passe</label>
                        <input type="password" oninput="verifIdentique()" class="form-control" id="mdpConfirm" name="mdpConfirm"
                               placeholder="Confirmez votre mot de passe"
                               maxlength="80">
                    </div>

                </div>

                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="poste">Votre poste</label>
                        <input type="text" readonly class="form-control" id="poste" name="poste" placeholder="poste"
                               maxlength="40" value= <%out.write((String)request.getAttribute("poste")==null?"":(String)request.getAttribute("poste"));%>>
                    </div>
                    <div class="form-group col-md-5">
                        <label for="role">Votre rôle</label>
                        <input type="text" readonly class="form-control" id="role" name="role" placeholder="Role"
                               maxlength="40" value= <%out.write((String)request.getAttribute("role")==null?"":(String)request.getAttribute("role"));%>>
                    </div>
                </div>
                <div class="modal" id="modalModif" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Modifier le mot de passe</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Pour modifier votre mot de passe, veuillez entrer votre ancien mot de passe.</p>
                                <input type="password" class="form-control" id="ancienMdp" name="ancienMdp"
                                       placeholder="Confirmez votre mot de passe"
                                       maxlength="80">
                            </div>
                            <div class="modal-footer">
                                <button  id="modif" type = "submit" class="btn btn-ensibs">Modifier</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <button data-toggle='modal' data-target='#modalModif' class='btn btn-ensibs btn-sm ' required role='button'>Modifier</button>
        </div>
    </div>

    <script>
        function verifSecur() {
            let mdpInput = document.getElementById("nouvMdp");

            if ($("#nouvMdp").val().match("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")) {
                if (mdpInput.classList.contains('mdpNonValid')) {
                    mdpInput.classList.remove('mdpNonValid');
                }
                if (!mdpInput.classList.contains('mdpValid')) {
                    mdpInput.classList.add('mdpValid');
                }
            } else {
                if (mdpInput.classList.contains('mdpValid')) {
                    mdpInput.classList.remove('mdpValid');
                }
                if (!mdpInput.classList.contains('mdpNonValid')) {
                    mdpInput.classList.add('mdpNonValid');
                }
            }
        }
        function verifIdentique() {
            let mdpInput = document.getElementById("mdpConfirm");

            if ($("#nouvMdp").val() == $("#mdpConfirm").val()) {
                if (mdpInput.classList.contains('mdpNonValid')) {
                    mdpInput.classList.remove('mdpNonValid');
                }
                if (!mdpInput.classList.contains('mdpValid')) {
                    mdpInput.classList.add('mdpValid');
                }
            } else {
                if (mdpInput.classList.contains('mdpValid')) {
                    mdpInput.classList.remove('mdpValid');
                }
                if (!mdpInput.classList.contains('mdpNonValid')) {
                    mdpInput.classList.add('mdpNonValid');
                }
            }
        }
    </script>

</main>



<%@ include file="footer.jsp" %>

</body>

</html>

