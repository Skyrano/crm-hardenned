package services;

import control.EntrepriseDAO;
import control.StringCleaner;
import donnees.Entreprise;
import donnees.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * La servlet des entreprises l'application
 */
@WebServlet("/entreprises")
public class Entreprises extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null){
                List<Entreprise> entreprises = new EntrepriseDAO().listeEntreprises();
                String listing = "<div class='table-responsive-md'>\n" +
                        "        <table class='table table-hover'>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th scope='col'>Nom</th>\n" +
                        "                <th scope='col'>Ville</th>\n" +
                        "                <th scope='col'>Domaine</th>\n" +
                        "                <th scope='col' class='text-center'>Nombre de contacts</th>\n" +
                        "                <th scope='col'></th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n";
                for (Entreprise entreprise :entreprises){
                    request.setAttribute("test","entreprises.size()");
                    listing+=
                    "            <tr>\n" +
                            "                <th scope='row'>"+ StringCleaner.cleaner(entreprise.getNom(), 40)+"</th>\n" +
                            "                <td>"+StringCleaner.cleaner(entreprise.getAdresse().getVille(), 50)+"</td>\n" +
                            "                <td>"+StringCleaner.cleaner(entreprise.getDomaine(), 40)+"</td>\n" +
                            "                <td class='text-center'><span class='badge badge-pill badge-success'>"+entreprise.getNbContacts()+"</span></td>\n" +
                            "                <td><a href='"+ Pages.ENTREPRISE.client() +"?idEntreprise="+entreprise.getLongIdentifiant()+"' class='btn btn-success btn-sm' role='button'>Consulter</a></td>\n" +
                            "            </tr>\n";
                }
                listing+=
                        "            </tbody>\n" +
                        "        </table>\n" +
                        "    </div>";
                request.setAttribute("listing",listing);
                this.getServletContext().getRequestDispatcher(Pages.LISTE_ENTREPRISES.serveur()).forward(request,response);
            }
            else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
