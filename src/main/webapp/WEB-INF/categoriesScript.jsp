<%@ page pageEncoding="UTF-8" %>
<script src="./styles/js/jquery-3.5.1.min.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        var wrapperAdder = $(".categorieAdder");
        var add_button = $(".add-categorie");
        var remove_button = ".remove-categorie";
        var listOpt = "<%   String stringOpts = "";
                            if (categories != null) {
                                for (Categorie categorie : categories){
                                    stringOpts+="<option value='"+categorie.getNom()+"'>"+categorie.getNom()+"</option>";
                                }
                            }
                            out.write(stringOpts); %>";
        $(add_button).click(function(e) {
            e.preventDefault();
            $(wrapperAdder).append('<div class="form-group d-flex"> ' +
                                        '<select class="form-control custom-select" id="select[]" name="categorieInput[]"> ' +
                                            listOpt +
                                        '</select> ' +
                                        '<button class="btn btn-danger remove-categorie" type="button">Supprimer</button> ' +
                                    '</div>');
        });
        $(wrapperAdder).on("click", remove_button, function(e) {
            e.preventDefault();
            $(this).parent('div').remove();
        });
    });
</script>