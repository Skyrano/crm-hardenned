<%@ page pageEncoding="UTF-8" %>
<%@ page import="donnees.Categorie" %>
<%@ page import="java.util.List" %>


<div class="form-group col-md-7">
    <form id="validationCategories" name="validationCategories" action="<% out.write((String) request.getAttribute("page")); %>" method="post">
        <div class="form-group categorieAdder" name="categoriesManager">
            <div class="form-group d-flex">
                <select class="form-control custom-select" id="select[]" name="categorieInput[]">
                    <option selected value="<% out.write( (String) request.getAttribute("premiereCategorie")); %>"><% out.write( (String) request.getAttribute("premiereCategorie")); %></option>
                    <%
                        List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                        String stringcats = "";
                        if (categories != null) {
                            for (Categorie categorie : categories){
                                if (!categorie.getNom().equals((String) request.getAttribute("premiereCategorie")))
                                    stringcats+="<option value='"+categorie.getNom()+"'>"+categorie.getNom()+"</option>";
                            }
                        }
                        out.write(stringcats);
                    %>
                    <%
                        if (!((String) request.getAttribute("premiereCategorie")).isEmpty())
                            out.write("<option value=''></option>");
                    %>
                </select>
                <button class="btn btn-success add-categorie" type="button">Ajouter</button>
            </div>
            <% out.write( (String) request.getAttribute("categoriesPossedee")); %>
        </div>
        <% out.write( (String) request.getAttribute("categoriesButton")); %>
    </form>
</div>
<div class="form-group col-md-3">
    <button type='submit' class='btn btn-ensibs' style="float: right" form='validationCategories'>Valider ces catégories</button>
</div>
<div class="form-group col-md-2">
    <form action='<%out.write(Pages.CATEGORIES.client());%>' method='post'>
        <% out.write( (String) request.getAttribute("categoriesButton")); %>
        <button type='submit' class='btn btn-ensibs'>Gérer les catégories</button>
    </form>
</div>


<%@ include file="categoriesScript.jsp" %>