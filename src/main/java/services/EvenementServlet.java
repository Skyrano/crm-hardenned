package services;

import control.CommentaireDAO;
import control.ContactDAO;
import control.EvenementDAO;
import control.StringCleaner;
import donnees.Contact;
import donnees.Evenement;
import donnees.Pages;
import donnees.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * La servlet des entreprises l'application
 */
@WebServlet("/evenement")
public class EvenementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");

            if (session.getAttribute("user") != null) {
                Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
                String nom = request.getParameter("nom");
                nom = nom.replace('+', ' ');
                nom = StringCleaner.cleaner(nom, 80);
                String dateString = request.getParameter("date");
                Date date;
                if(dateString.contains("/")){
                    SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date dateTransfo = f1.parse(dateString);
                    date = new java.sql.Date(dateTransfo.getTime());
                } else {
                     date = Date.valueOf(request.getParameter("date"));
                }


                String alert = "";
                String commentaireSupp = request.getParameter("commentaire");
                if (commentaireSupp != null) {
                    int commentaireId = Integer.parseInt(commentaireSupp);
                    if (!new CommentaireDAO().supprimerCommentaireFromId(commentaireId))
                        alert = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                                "  Le commentaire n'a pas pu être supprimé de l'événement !\n" +
                                "</div>";
                }

                String contactSupp = request.getParameter("contact");
                if (contactSupp != null) {
                    int contactId = Integer.parseInt(contactSupp);
                    if (!new ContactDAO().supprimerContactFromEvenement(contactId, nom, date))
                        alert = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                                "  Le contact n'a pas pu être supprimé de l'événement !\n" +
                                "</div>";
                }

                String utilisateurSupp = request.getParameter("utilisateur");
                if (utilisateurSupp != null) {
                    if (!new EvenementDAO().supprimerUtilisateurFromEvenement(StringCleaner.cleaner(utilisateurSupp, 40), StringCleaner.cleaner(nom, 80), date))
                        alert = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                                "  L'utilisateur n'a pas pu être supprimé de l'événement !\n" +
                                "</div>";
                }

                Evenement evenement = new EvenementDAO().recupererEvenement(nom, date);

                List<Utilisateur> utilisateurs = evenement.getUtilisateursPresents();
                String listingUtilisateurs = "" +
                        "    <div class='table-responsive-md mb-4'>\n" +
                        "        <table class='table table-hover'>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th scope='col' class='text-center'>Nom</th>\n" +
                        "                <th scope='col' class='text-center'>Prénom</th>\n" +
                        "                <th scope='col' class='text-center'>Mail</th>\n" +
                        "                <th scope='col' class='text-center'>Poste</th>\n" +
                        "                <th scope='col' class='text-center'></th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n";
                for (Utilisateur u : utilisateurs) {
                    listingUtilisateurs +=
                            "            <tr>\n" +
                                    "                <th scope='row' class='text-center'>" + StringCleaner.cleaner(u.getNom(), 40) + "</th>\n" +
                                    "                <th scope='row' class='text-center'>" + StringCleaner.cleaner(u.getPrenom(), 40) + "</th>\n" +
                                    "                <td class='text-center'>" + StringCleaner.cleaner(u.getMail(), 80) + "</td>\n" +
                                    "                <th scope='row' class='text-center'>" + StringCleaner.cleaner(u.getPoste(), 40) + "</th>\n" +
                                    "                <th scope='row' class='text-center'>" +
                                    "                <form method='get' action='" + Pages.EVENEMENT.client() + "'>" +
                                    "                   <input type='hidden' id='nomUserSupp' name='nom' value='" + nom + "'>" +
                                    "                   <input type='hidden' id='dateUserSupp' name='date' value='" + date.toString() + "'>" +
                                    "                   <input type='hidden' id='userSupp' name='utilisateur' value='" + StringCleaner.cleaner(u.getIdentifiant(), 40) + "'>";
                    if (utilisateur.getRole().getAccesEcriture())
                        listingUtilisateurs += "                   <button type=\"submit\" class=\"btn btn-sm btn-danger\">Supprimer</button>\n";
                    listingUtilisateurs += "                </form>" +
                            "</th>\n";
                }
                listingUtilisateurs +=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "   <form action='" + Pages.EVENEMENT_AJOUT_UTILISATEUR.client() + "' method='get'>" +
                                "        <input type='hidden' id='evenementNom' name='evenementNom' value='" + nom + "'>" +
                                "        <input type='hidden' id='evenementDate' name='evenementDate' value='" + date + "'>" +
                                "        <button type=\"submit\" class=\"btn btn-ensibs\">Ajouter un utilisateur</button>\n" +
                                "   </form>" +
                                "</div>";


                List<Contact> contacts = evenement.getContactsPresents();
                String listingContacts = "" +
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
                    listingContacts +=
                            "            <tr>\n" +
                                    "                <th scope='row'>" + StringCleaner.cleaner(contact.getNom(), 40) + "</th>\n" +
                                    "                <th scope='row'>" + StringCleaner.cleaner(contact.getPrenom(), 40) + "</th>\n" +
                                    "                <td>" + StringCleaner.cleaner(contact.getEntreprise().getNom(), 40) + "</td>\n" +
                                    "                <td class='d-flex'><a href='" + Pages.CONTACT.client() + "?idContact=" + Integer.toString(contact.getIdentifiant()) + "' class='btn btn-success btn-sm' role='button'>Consulter</a>" +
                                    "                <form method='get' action='" + Pages.EVENEMENT.client() + "'>" +
                                    "                   <input type='hidden' id='nomContactSupp' name='nom' value='" + nom + "'>" +
                                    "                   <input type='hidden' id='dateContactSupp' name='date' value='" + date.toString() + "'>" +
                                    "                   <input type='hidden' id='contactSupp' name='contact' value='" + contact.getIdentifiant() + "'>";
                    if (utilisateur.getRole().getAccesEcriture())
                        listingContacts += "                   <button type=\"submit\" class=\"btn btn-sm btn-danger ml-3\">Supprimer</button>\n";
                    listingContacts += "                </form>" +
                            "</td>\n" +
                            "            </tr>\n";
                }
                listingContacts +=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "   <form action='" + Pages.RECHERCHER_CONTACT.client() + "' method='post'>" +
                                "        <input type='hidden' id='evenementNom' name='evenementNom' value='" + nom + "'>" +
                                "        <input type='hidden' id='evenementDate' name='evenementDate' value='" + date + "'>" +
                                "        <button type=\"submit\" class=\"btn btn-ensibs\">Ajouter un contact</button>\n" +
                                "   </form>" +
                                "    </div>";

                String commentaire = "" +
                        "<hr width=\"100%\">\n" +
                        "<h4>Commentaires</h4>\n" +
                        "<div class='card-deck'>\n";
                for (int i = 0; i < evenement.getCommentaires().size(); i++) {
                    commentaire +=
                            "        <div class='card mb-2' style='max-width: 18rem;min-width: 15rem;'>\n" +
                                    "            <div class='card-body d-flex flex-column'>\n" +
                                    "                <h5 class='card-title'>" + StringCleaner.cleaner(evenement.getCommentaires().get(i).getIntitule(), 100) + "</h5>\n" +
                                    "                <p class='card-text'>" + StringCleaner.cleaner(evenement.getCommentaires().get(i).getCommentaire(), 300) + ".</p>\n" +
                                    "                <p class='card-text'><small class='text-muted'>Posté le " + evenement.getCommentaires().get(i).getDate().toString() + "</small></p>\n" +
                                    "                  <a href='" + Pages.EVENEMENT.client() + "?commentaire=" + evenement.getCommentaires().get(i).getId() + "&nom=" + StringCleaner.cleaner(evenement.getNom(), 80) + "&date=" + evenement.getDate().toString() + "'  class='btn-sm btn-outline-danger mt-auto text-center' role='button'>Supprimer</a>" +
                                    "            </div>\n" +
                                    "        </div>\n";
                }
                commentaire += "       </div>" +
                        "<form action='" + Pages.COMMENTAIRE.client() + "' method='get'>" +
                        "           <input type='hidden' id='idEvenementNomPourCommentaire' name='evenementNom' value='" + StringCleaner.cleaner(evenement.getNom(), 80) + "'>" +
                        "           <input type='hidden' id='idEvenementDatePourCommentaire' name='evenementDate' value='" + evenement.getDate().toString() + "'>" +
                        "           <button type='submit' class='btn btn-ensibs'>Ajouter un commentaire</button>\n" +
                        "       </form>";

                request.setAttribute("alert", alert);
                request.setAttribute("evenement", evenement);
                request.setAttribute("listingUtilisateurs", listingUtilisateurs);
                request.setAttribute("listingContacts", listingContacts);
                request.setAttribute("commentaire", commentaire);
                this.getServletContext().getRequestDispatcher(Pages.EVENEMENT.serveur()).forward(request, response);
            } else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            }
        } catch (IOException | ServletException | ParseException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request, response);
    }
}
