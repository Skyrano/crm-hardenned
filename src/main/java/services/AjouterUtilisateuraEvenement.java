package services;

import control.AuthentificationManager;
import control.EvenementDAO;
import control.StringCleaner;
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
import java.util.List;

/**
 * La servlet des entreprises l'application
 */
@WebServlet("/evenementAjoutUtilisateur")
public class AjouterUtilisateuraEvenement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession firstsession = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        if (firstsession.getAttribute("user") != null){
            String evenementNom = request.getParameter("evenementNom");
            evenementNom = evenementNom.replace('+',' ');
            evenementNom = StringCleaner.cleaner(evenementNom, 80);
            Date evenementDate = Date.valueOf(request.getParameter("evenementDate"));
            String utilisateurSupp = request.getParameter("utilisateur");
            utilisateurSupp = StringCleaner.cleaner(utilisateurSupp, 40);
            String alert = "";
            if (utilisateurSupp != null) {
                if (!new EvenementDAO().ajouterUtilisateurAEvenement(utilisateurSupp, evenementNom, evenementDate))
                    alert = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                            "  L'utilisateur n'a pas pu être ajouté à l'événement !\n" +
                            "</div>";
                else
                    alert = "<div class=\"alert alert-success text-center\" role=\"alert\">\n" +
                            "  L'utilisateur a été ajouté à l'événement !\n" +
                            "</div>";
            }
            request.setAttribute("alert", alert);
            doGet(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");
            if (session.getAttribute("user") != null){
                String nom = request.getParameter("evenementNom");
                nom = nom.replace('+',' ');
                nom = StringCleaner.cleaner(nom, 80);
                Date date = Date.valueOf(request.getParameter("evenementDate"));

                List<Utilisateur> utilisateurs = new AuthentificationManager().recuputilisateurPasPresentAEvenement(nom,date);
                String listingUtilisateurs = "" +
                        "    <div class='table-responsive-md'>\n" +
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
                                    "                <form method='post' action='"+Pages.EVENEMENT_AJOUT_UTILISATEUR.client()+"'>" +
                                    "                   <input type='hidden' id='nomUserSupp' name='evenementNom' value='"+StringCleaner.cleaner(nom, 80)+"'>" +
                                    "                   <input type='hidden' id='dateUserSupp' name='evenementDate' value='"+date.toString()+"'>" +
                                    "                   <input type='hidden' id='userSupp' name='utilisateur' value='"+StringCleaner.cleaner(u.getIdentifiant(), 40)+"'>" +
                                    "                   <button type=\"submit\" class=\"btn btn-sm btn-success\">Ajouter</button>\n"+
                                    "                </form>" +
                                    "   </th>\n";
                }
                listingUtilisateurs +=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "   <form action='"+Pages.EVENEMENT.client()+"' method='get'>"+
                                "        <input type='hidden' id='evenementNom' name='nom' value='"+StringCleaner.cleaner(nom, 80)+"'>"+
                                "        <input type='hidden' id='evenementDate' name='date' value='"+date+"'>"+
                                "        <button type=\"submit\" class=\"btn btn-secondary\">Retour</button>\n"+
                                "   </form>" +
                                "    </div>";

                request.setAttribute("evenement",new EvenementDAO().recupererEvenement(nom, date));
                request.setAttribute("listingUtilisateurs", listingUtilisateurs);
                this.getServletContext().getRequestDispatcher(Pages.EVENEMENT_AJOUT_UTILISATEUR.serveur()).forward(request,response);
            }
            else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
