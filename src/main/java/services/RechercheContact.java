package services;

import control.ContactDAO;
import control.EvenementDAO;
import control.StringCleaner;
import donnees.Contact;
import donnees.Pages;

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


@WebServlet("/rechercherContact")
public class RechercheContact extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            this.getServletContext().getRequestDispatcher(Pages.RECHERCHER_CONTACT.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {

            String nomContact = request.getParameter("nom");
            if (nomContact == null)
                nomContact = "";
            else
                nomContact = StringCleaner.cleaner(nomContact, 40);
            String prenomContact = request.getParameter("prenom");
            if (prenomContact == null)
                prenomContact = "";
            else
                prenomContact = StringCleaner.cleaner(prenomContact, 40);
            String entreprise = request.getParameter("entreprise");
            if (entreprise == null)
                entreprise = "";
            else
                entreprise = StringCleaner.cleaner(entreprise, 40);
            String dateNaissanceContactCheck = request.getParameter("dateDeNaissance");
            if (dateNaissanceContactCheck == null)
                dateNaissanceContactCheck = "";
            String categorie = request.getParameter("categorie");
            if (categorie == null)
                categorie = "";
            else
                categorie = StringCleaner.cleaner(categorie, 80);
            String statut = request.getParameter("statut");
            if (statut == null)
                statut = "";
            String email = request.getParameter("mail");
            if (email == null)
                email = "";
            else
                email = StringCleaner.cleaner(email, 40);
            String numTelephone = "";
            String numeroTelephone = request.getParameter("numTelephone");
            if (numeroTelephone == null)
                numeroTelephone = "";
            String indicateur = request.getParameter("indicateur");
            if (indicateur == null)
                indicateur = "";

            String evenementDate = request.getParameter("evenementDate");
            if (evenementDate != null && evenementDate.isEmpty())
                evenementDate = null;
            String evenementNom = request.getParameter("evenementNom");
            if (evenementNom != null) {
                evenementNom = evenementNom.replace('+', ' ');
                evenementNom = StringCleaner.cleaner(evenementNom, 80);
            }
            if (evenementNom != null && evenementNom.isEmpty())
                evenementNom = null;
            String idAjout = request.getParameter("idAjout");
            if (idAjout != null && idAjout.isEmpty())
                idAjout = null;

            String btnRet = "";
            if (evenementDate != null && evenementNom != null)
                btnRet = "<form method='get' action='evenement'> " +
                        "<input type='hidden' name='nom' value='" + evenementNom + "'>" +
                        "<input type='hidden' name='date' value='" + evenementDate + "'>" +
                        "<button type='submit' class='btn btn-secondary'>Retour</button></form>";


            String dateNaissanceContact = dateNaissanceContactCheck.replaceAll("/", "-");
            if (!dateNaissanceContact.equals("")) {
                String[] arrOfStr = dateNaissanceContact.split("-");
                if (arrOfStr.length > 2) {
                    dateNaissanceContact = arrOfStr[2] + "-" + arrOfStr[1] + "-" + arrOfStr[0];
                }
            }

            Date dateNaissance = null;
            try {
                dateNaissance = Date.valueOf(dateNaissanceContact);
            } catch (Exception e) {

            }

            if (!numeroTelephone.equals("") && indicateur.equals("")) {
                numTelephone = numeroTelephone;
            } else if (!numeroTelephone.equals("") && !indicateur.equals("")) {
                numeroTelephone = numeroTelephone.replaceAll(" ", "");
                numeroTelephone = numeroTelephone.replaceAll("\\.", "");

                if (indicateur.charAt(0) != '+') {
                    indicateur = "+" + indicateur;
                }
                indicateur = indicateur.replaceAll(" ", "");

                numTelephone = indicateur + " " + numeroTelephone;
            }

            String alert = "";
            List<Contact> contacts = new ContactDAO().recupererContactParCritere(nomContact, prenomContact, dateNaissance, numTelephone, email, categorie, statut, entreprise);
            if (idAjout != null && evenementDate != null && evenementNom != null) {
                if(!new EvenementDAO().ajouterContactAEvenement(idAjout, evenementNom, java.sql.Date.valueOf(evenementDate)))
                    alert = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                            "  L'utilisateur n'a pas pu être ajouté à l'événement !\n" +
                            "</div>";
                else
                    alert = "<div class=\"alert alert-success text-center\" role=\"alert\">\n" +
                            "  L'utilisateur a été ajouté à l'événement !\n </div>";
            }

            String listing = "";
            if (contacts != null) {
                listing = alert +
                        "    <div class='table-responsive-md'>\n" +
                                "        <table class='table table-hover'>\n" +
                                "            <thead>\n" +
                                "            <tr>\n" +
                                "                <th scope='col'>Nom</th>\n" +
                                "                <th scope='col'>Prénom</th>\n" +
                                "                <th scope='col'>Entreprise</th>\n" +
                                "                <th scope='col'></th>\n" +
                                "            </tr>\n" +
                                "            </thead>\n" +
                                "            <tbody>\n";

                for (Contact contact : contacts) {
                    String btn = "";

                    if (evenementDate != null && evenementNom != null) {
                        btn = "<form method='post' action='"+Pages.RECHERCHER_CONTACT.client()+"'>" +
                                "<input type='hidden' name='idAjout' value='" + contact.getIdentifiant() + "'>" +
                                "<input type='hidden' id='evenementNomRenvoi' name='evenementNom' value='"+StringCleaner.cleaner(evenementNom, 80)+"'>" +
                                "<input type='hidden' id='evenementDateRenvoi' name='evenementDate' value='"+evenementDate+"'>"+
                            "<button type='submit'class='btn btn-ensibs'>Ajouter</button></form>";
                    } else
                        btn = "<a href='"+Pages.CONTACT.client()+"?idContact=" + Integer.toString(contact.getIdentifiant()) + "' class='btn btn-ensibs' role='button'>Consulter</a>";
                    listing +=
                            "            <tr>\n" +
                                    "                <th scope='row'>" + StringCleaner.cleaner(contact.getNom(), 40) + "</th>\n" +
                                    "                <th scope='row'>" + StringCleaner.cleaner(contact.getPrenom(), 40) + "</th>\n" +
                                    "                <td>" + StringCleaner.cleaner(contact.getEntreprise().getNom(), 40) + "</td>\n" +
                                    "                <td>" + btn + "</td>\n" +
                                    "            </tr>\n";
                }
                listing +=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                btnRet +
                                "    </div>";
            }
            request.setAttribute("listing", listing);
            request.setAttribute("nom", nomContact);
            request.setAttribute("prenom", prenomContact);
            request.setAttribute("dateDeNaissance", dateNaissanceContactCheck);
            request.setAttribute("statut", statut);
            request.setAttribute("mail", email);
            request.setAttribute("indicateur", indicateur);
            request.setAttribute("numTelephone", numeroTelephone);
            request.setAttribute("categorie", categorie);
            request.setAttribute("entreprise", entreprise);

            this.getServletContext().getRequestDispatcher(Pages.RECHERCHER_CONTACT.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
    }
}
