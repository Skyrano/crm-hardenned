<%@ page pageEncoding="UTF-8" %>
<script src="./styles/js/jquery-3.5.1.min.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        var wrapperAdder = $(".newcategorieAdder");
        var add_button = $(".add-new-categorie");
        var remove_button = ".remove-new-categorie";
        var newInputDiv =  "<div class='form-group d-flex'> <input class='form-control' id='categories[]' name='newcategorieInput[]' placeholder='Catégorie' value=''/> <button class='btn btn-danger remove-new-categorie' type='button'>Supprimer</button> </div>";
        $(add_button).click(function(e) {
            e.preventDefault();
            $(wrapperAdder).append(newInputDiv);
        });
        $(wrapperAdder).on("click", remove_button, function(e) {
            e.preventDefault();
            $(this).parent('div').remove();
        });
    });
</script>