package services;

import control.LogDAO;
import donnees.Log;
import donnees.Pages;
import donnees.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * La servlet des contacts de l'application
 */
@WebServlet("/logs")
public class Logs extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur != null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin"))){
                List<Log> logs = new LogDAO().listeLogs();
                String listing = "" +
                        "    <div class='table-responsive-md'>\n" +
                        "        <table class='table table-hover'>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th scope='col'>Date</th>\n" +
                        "                <th scope='col'>Type</th>\n" +
                        "                <th scope='col'>Contenu</th>\n" +
                        "                <th scope='col'>Utilisateur</th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n";
                for (Log log:logs){
                    listing+=
                                    "            <tr>\n" +
                                    "                <th scope='row'>"+log.getDate().toString()+"</th>\n" +
                                    "                <th scope='row'>"+log.getType()+"</th>\n" +
                                    "                <td>"+log.getContenu()+"</td>\n" +
                                    "                <td><a href='"+ Pages.UTILISATEUR.client()+"?identifiant="+log.getUtilisateurConcerne().getIdentifiant()+"'>"+log.getUtilisateurConcerne().toString()+"</a></td>" +
                                    "            </tr>\n";
                }
                listing+=
                        "            </tbody>\n" +
                        "        </table>\n" +
                        "    </div>";
                request.setAttribute("listing",listing);
                this.getServletContext().getRequestDispatcher(Pages.LISTE_LOGS.serveur()).forward(request,response);
            }
            else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.doGet(request,response);
    }
}
