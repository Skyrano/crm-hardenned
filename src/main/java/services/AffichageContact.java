package services;

import control.*;
import donnees.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

@WebServlet("/affichage-contact")
public class AffichageContact extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            String idContact = request.getParameter("idContact");

            if (!idContact.equals("")) {
                idContact = StringCleaner.cleaner(idContact, 80);


                String nomCategorie = request.getParameter("idCategorie");
                if (nomCategorie != null) {
                    int contactId = Integer.parseInt(idContact);
                    new ContactDAO().supprimerRelationCategorieContact(contactId, nomCategorie);
                }

                String idCommentaire = request.getParameter("idCommentaire");
                if (idCommentaire != null) {
                    int commentaireId = Integer.parseInt(idCommentaire);
                    new CommentaireDAO().supprimerCommentaireFromId(commentaireId);
                }

                Contact contact = new ContactDAO().recupContactParId(Integer.parseInt(idContact));
                String nomPrenom = contact.getNom() + " " + contact.getPrenom();

                Date dateNaissance = contact.getDateNaissance();
                String dateNaissancee = "";
                if (dateNaissance != null) {
                    String jour = "" + dateNaissance.toLocalDate().getDayOfMonth();
                    if (dateNaissance.toLocalDate().getDayOfMonth() < 10) {
                        jour = "0" + dateNaissance.toLocalDate().getDayOfMonth();
                    }
                    String mois = "" + dateNaissance.toLocalDate().getMonthValue();

                    if (dateNaissance.toLocalDate().getMonthValue() < 10) {
                        mois = "0" + dateNaissance.toLocalDate().getMonthValue();
                    }
                    dateNaissancee = jour + "/" + mois + "/" + dateNaissance.toLocalDate().getYear();
                }
                String emailContact = contact.getMail();
                String numTel = contact.getNumTel();

                String entrepriseAssocie = "";
                if (contact.getEntreprise().getIdentifiant() == -1.0) {
                    entrepriseAssocie = "<hr width='100%'>\n" +
                            "<h3> Aucune entreprise renseignée</h3>";
                } else {

                    entrepriseAssocie = "" +
                            "<hr width='100%'>\n" +
                            "<h3>Entreprise</h3>\n" +
                            "<div class='form-row'>\n" +
                            "    <div class='form-group col-md-4'>\n" +
                            "        <label>Nom de l'entreprise</label><br />\n" +
                            "        <a class='contactPolice' href='"+Pages.ENTREPRISE.client()+"?idEntreprise="+String.format("%.0f",contact.getEntreprise().getIdentifiant())+"'>" + StringCleaner.cleaner(contact.getEntreprise().getNom(),40) + "</a>\n" +
                            "    </div>\n" +
                            "    <div class='form-group col-md-4'>\n" +
                            "        <label>Domaine de l'entreprise</label><br />\n" +
                            "        <label class='contactPolice'>" + StringCleaner.cleaner(contact.getEntreprise().getDomaine(), 40) + "</label>\n" +
                            "    </div>\n" +
                            "    <div class='form-group col-md-2'>\n" +
                            "        <label class='surChampPolice'>N° Siret</label><br />\n" +
                            "        <label class='contactPolice'>" + String.format("%.0f",contact.getEntreprise().getGroupeParent().getNumSiret()) + "</label>\n" +
                            "    </div>\n" +
                            "    <div class='form-group col-md-2'>\n" +
                            "        <label>Nb d'employés</label><br />\n" +
                            "        <label class='contactPolice'>" + contact.getEntreprise().getNbEmployes() + "</label>\n" +
                            "    </div>\n" +
                            "</div>\n" +
                            "<h4>Contact de l'entreprise</h4>\n" +
                            "<div class='form-row'>\n" +
                            "    <div class='form-group col-md-8'>\n" +
                            "        <label>E-mail</label><br />\n" +
                            "       <form action='"+Pages.MAIL.client()+"' method='post' id=\"formMailEntreprise\">\n" +
                            "                        <input type='hidden' id='NomPrenomEntreprise' name='NomPrenom' value='"+StringCleaner.cleaner(contact.getEntreprise().getNom(), 40)+"'>\n" +
                            "                        <input type='hidden' id='mailEntreprise' name='mail' value='"+StringCleaner.cleaner(contact.getEntreprise().getMailContact(), 80)+"'>\n" +
                            "        </form>" +
                            "        <a class='contactPolice' href='#' onclick=\"document.getElementById('formMailEntreprise').submit()\">" + StringCleaner.cleaner(contact.getEntreprise().getMailContact(), 80) + "</a>\n" +
                            "    </div>\n" +
                            "    <div class='form-group col-md-4'>\n" +
                            "        <label>Adresse du siège social</label><br />\n" +
                            "        <label class='contactPolice'>" + contact.getEntreprise().getAdresse().getNumeroRue() + " " + StringCleaner.cleaner(contact.getEntreprise().getAdresse().getRue(), 100) + "</label><br />\n" +
                            "        <label class='contactPolice'>" + contact.getEntreprise().getAdresse().getCodePostal() + " " + StringCleaner.cleaner(contact.getEntreprise().getAdresse().getVille(), 50) + "</label><br/>\n" +
                            "        <label class='contactPolice'>" + StringCleaner.cleaner(contact.getEntreprise().getAdresse().getPays(), 30) + "</label><br/>\n" +
                            "    </div>\n" +
                            "</div>\n";

                }


                String commentaire = this.ajoutDesCommentaires(contact);
                String scriptCommentaire = "";
                if(contact.getCommentaires().size()>3){
                    scriptCommentaire = scriptingCommentaire(contact);
                }
                nomPrenom = StringCleaner.cleaner(nomPrenom, 81);
                emailContact = StringCleaner.cleaner(emailContact, 80);
                request.setAttribute("nomPrenom", nomPrenom);
                request.setAttribute("idContact", idContact);
                request.setAttribute("dateDeNaissance", dateNaissancee);
                request.setAttribute("emailContact", emailContact);
                request.setAttribute("numTel", numTel);
                request.setAttribute("entrepriseAssocie", entrepriseAssocie);
                request.setAttribute("commentaire", commentaire);
                request.setAttribute("scriptCommentaire", scriptCommentaire);

                String page = Pages.CONTACT.client();
                request.setAttribute("page", page);

                String categoriesButton = "<input type='hidden' id='lastIdContact' name='idContact' value='"+Integer.toString(contact.getIdentifiant())+"'>";
                request.setAttribute("categoriesButton", categoriesButton);

                List<Categorie> categories = new CategorieDAO().recupererCategories();
                request.setAttribute("categories", categories);

                CategoriesManager manager = new CategoriesManager(contact, categories);

                String[] categoriesInput = request.getParameterValues("categorieInput[]");
                if(categoriesInput != null) {
                    new LogDAO().log(LogType.MODIFICATION_CONTACT, "Modification des categories d'un contact : "+contact.toString(), (Utilisateur) session.getAttribute("user"));
                    manager.updateContact(contact, categoriesInput);
                    contact = new ContactDAO().recupContactParId(Integer.parseInt(idContact));
                    manager = new CategoriesManager(contact, categories);
                }

                String premiereCategorie = manager.premiereCategorie();
                request.setAttribute("premiereCategorie", premiereCategorie);
                String categoriesPossedee = manager.categoriesPossedee();
                request.setAttribute("categoriesPossedee", categoriesPossedee);


                try {
                    this.getServletContext().getRequestDispatcher(Pages.CONTACT.serveur()).forward(request, response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    this.getServletContext().getRequestDispatcher(Pages.ACCUEIL.serveur()).forward(request, response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
            }


        } else {
            try {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }


    private String ajoutDesCommentaires(Contact contact) {
        String commentaire = "" +
                "<hr width=\"100%\">\n" +
                "<h4>Commentaires</h4>\n" +
                "<div class='card-deck'>\n";
        for (int i = 0; i < contact.getCommentaires().size(); i++) {
            commentaire +=
                    "        <div class='card mb-2' style='max-width: 18rem;min-width: 15rem;'>\n" +
                    "            <div class='card-body d-flex flex-column'>\n" +
                    "                <h5 class='card-title'>"+StringCleaner.cleaner(contact.getCommentaires().get(i).getIntitule(), 100)+"</h5>\n" +
                    "                <p class='card-text'>"+StringCleaner.cleaner(contact.getCommentaires().get(i).getCommentaire(), 300)+".</p>\n" +
                    "                <p class='card-text'><small class='text-muted'>Posté le "+contact.getCommentaires().get(i).getDate().toString()+"</small></p>\n" +
                    "                  <a href='"+Pages.CONTACT.client()+"?idCommentaire="+contact.getCommentaires().get(i).getId()+"&idContact="+contact.getIdentifiant()+"'  class='btn-sm btn-outline-danger mt-auto text-center' role='button'>Supprimer</a>" +
                    "            </div>\n" +
                    "        </div>\n";
        }
        commentaire += "       </div>" +
                "<form action='"+Pages.COMMENTAIRE.client()+"' method='get'>"+
                "           <input type='hidden' id='idContactPourCommentaire' name='contactId' value='"+Integer.toString(contact.getIdentifiant())+"'>"+
                "           <button type='submit' class='btn btn-ensibs'>Ajouter un commentaire</button>\n"+
                "       </form>";

        return commentaire;
    }

    private String scriptingCommentaire(Contact contact){
        String scriptCommentaire = ""+
                "<script type=\"text/javascript\">\n"+
                "   function makeCommentAppear(){\n"+
                "       document.getElementById(\"affichePlusCommentaire\").style.display = \"none\";\n"+
                "       document.getElementById(\"afficheMoinsCommentaire\").style.display = \"initial\";\n";
        for(int i = 3; i < contact.getCommentaires().size(); i++){
            scriptCommentaire += ""+
                    "       document.getElementById(\"comment"+i+"\").style.display = \"initial\";\n";
        }
        scriptCommentaire += "  }"+
                "   function makeCommentDisappear(){"+
                "       document.getElementById(\"affichePlusCommentaire\").style.display = \"initial\";\n"+
                "       document.getElementById(\"afficheMoinsCommentaire\").style.display = \"none\";\n";
        for(int i = 3; i < contact.getCommentaires().size(); i++){
            scriptCommentaire += ""+
                    "       document.getElementById(\"comment"+i+"\").style.display = \"none\";\n";
        }
        scriptCommentaire += "  }"+
                "</script>";
        return scriptCommentaire;
    }

    private String ajoutDesCategorie(Contact contact) {
        String categorie = "" +
                "<hr width=\"100%\">\n" +
                "    <h4>Catégories</h4>\n" +
                "    <div class=\"form-row\">\n";

        for (int i = 0; i < contact.getCategories().size(); i++) {
            String onAffichePas = "";
            if(i >= 4){
                onAffichePas = "id='categ"+i+"' style='display: none'";
            }
            categorie += "<div class=\"form-group col-md-3\" "+ onAffichePas +">\n" +
                    "            <div class=\"card bg-light mb-3\">\n" +
                    "                <div class=\"container\">\n" +
                    "                    <p>" + contact.getCategories().get(i).getNom() + "</p>\n" +
                    "                </div>\n" +
                    "                     <td><a href='"+Pages.CONTACT.client()+"?idCategorie="+contact.getCategories().get(i).getNom()+"&idContact="+contact.getIdentifiant()+"' class='btn btn-outline-danger btn-sm' role='button'>Supprimer</a></td>\n" +
                    "            </div>\n" +
                    "        </div>\n";
        }
        categorie += "</div>\n" +
                "<div class=\"form-row\">\n"+
                "   <div class=\"form-group col-md-3\">\n"+
                "   </div>\n";
        if(contact.getCategories().size() > 4){
            categorie += "   <div class=\"form-group col-md-3\" onclick=\"makeCategAppear()\" id='affichePlusCategorie'>\n"+
                    "       <button class=\"btn btn-ensibs\">Afficher plus</button>\n"+
                    "   </div>\n" +
                    "   <div class=\"form-group col-md-3\" onclick=\"makeCategDisappear()\" id='afficheMoinsCategorie' style='display: none'>\n"+
                    "       <button class=\"btn btn-ensibs\">Afficher moins</button>\n"+
                    "   </div>\n";
        }
        categorie += "</div>\n";

        return categorie;
    }

    private String scriptingCategorie(Contact contact){
        String scriptCategorie = ""+
                "<script type=\"text/javascript\">\n"+
                "   function makeCategAppear(){\n"+
                "       document.getElementById(\"affichePlusCategorie\").style.display = \"none\";\n"+
                "       document.getElementById(\"afficheMoinsCategorie\").style.display = \"initial\";\n";
        for(int i = 4; i < contact.getCategories().size(); i++){
            scriptCategorie += ""+
                    "       document.getElementById(\"categ"+i+"\").style.display = \"initial\";\n";
        }
        scriptCategorie += "  }"+
                "   function makeCategDisappear(){"+
                "       document.getElementById(\"affichePlusCategorie\").style.display = \"initial\";\n"+
                "       document.getElementById(\"afficheMoinsCategorie\").style.display = \"none\";\n";
        for(int i = 4; i < contact.getCategories().size(); i++){
            scriptCategorie += ""+
                    "       document.getElementById(\"categ"+i+"\").style.display = \"none\";\n";
        }
        scriptCategorie += "  }"+
                "</script>";
        return scriptCategorie;
    }
}
