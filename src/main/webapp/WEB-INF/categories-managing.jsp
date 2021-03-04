<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="donnees.Categorie" %>
<%@ page import="java.util.List" %>
<%@ page import="donnees.Pages" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

    <%@ include file="navbar.jsp" %>

    <main role="main" class="container">
        <div>
                <h4 class="label">Gestion des catégories</h4>
                <div class="form-row">
                    <form id="newCategories" name="newCategories" class="form-group col-md-10" method="post" action="<%out.write(Pages.CATEGORIES.client());%>" name="categoriesManaging">
                        <div class="form-group newcategorieAdder" name="newCategoriesManager">
                            <%
                                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                                String stringcats = "";
                                if (categories != null && categories.size() > 0) {
                                    stringcats += "<div class='form-group d-flex'> <input class='form-control' id='categories[]' name='newcategorieInput[]' placeholder='Catégorie' value='"+categories.get(0).getNom()+"'/>";
                                    stringcats += "<button class='btn btn-success add-new-categorie' type='button'>Ajouter</button> </div>";
                                    for (int i = 1; i < categories.size(); i++) {
                                        stringcats += "<div class='form-group d-flex'> <input class='form-control' id='categories[]' name='newcategorieInput[]' placeholder='Catégorie' value='"+categories.get(i).getNom()+"'/>";
                                        stringcats += "<button class='btn btn-danger remove-new-categorie' type='button'>Supprimer</button> </div>";
                                    }
                                }
                                out.write(stringcats);
                            %>
                        </div>
                        <%
                            String idContact = request.getParameter("idContact");
                            if (idContact != null)
                                out.write("<input type='hidden' id='contactIdRetour' name='idContact' value='"+idContact+"'>");
                        %>
                        <%
                            String idEntreprise = request.getParameter("idEntreprise");
                            if (idEntreprise != null)
                                out.write("<input type='hidden' id='entrepriseIdRetour' name='idEntreprise' value='"+idEntreprise+"'>");
                        %>
                    </form>
                    <div class="form-group col-md-1">
                        <button class="btn btn-ensibs" type="submit" form="newCategories">Valider</button>
                    </div>
                    <div class="form-group col-md-1">
                        <%
                            String lastPageButton = (String) request.getAttribute("lastPageButton");
                            out.write(lastPageButton);
                        %>
                    </div>
                </div>
        </div>

        <%
            String alert = (String) request.getAttribute("alert");
            out.write(alert);
        %>

    </main>

    <%@ include file="footer.jsp" %>

    <%@ include file="categories-managingScript.jsp" %>

</body>

</html>