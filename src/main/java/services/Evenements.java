package services;

import control.EvenementDAO;
import control.StringCleaner;
import donnees.Evenement;
import donnees.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * La servlet des entreprises l'application
 */
@WebServlet("/evenements")
public class Evenements extends HttpServlet {
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
                List<Evenement> evenements = new EvenementDAO().listeEvenements();
                String listing = "<div class='table-responsive-md'>\n" +
                        "        <table class='table table-hover'>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th scope='col'>Nom</th>\n" +
                        "                <th scope='col'>Ville</th>\n" +
                        "                <th scope='col'>Type</th>\n" +
                        "                <th scope='col'>Date</th>\n" +
                        "                <th scope='col' class='text-center'>Nombre d'invités</th>\n" +
                        "                <th scope='col'></th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n";
                for (Evenement evenement :evenements){
                    listing+=
                            "            <tr>\n" +
                                    "                <th scope='row'>"+ StringCleaner.cleaner(evenement.getNom(), 80)+"</th>\n" +
                                    "                <td>"+StringCleaner.cleaner(evenement.getAdresse().getVille(), 50)+"</td>\n" +
                                    "                <td>"+StringCleaner.cleaner(evenement.getType(), 40)+"</td>\n" +
                                    "                <td>"+evenement.getDateString()+"</td>\n" +
                                    "                <td class='text-center'><span class='badge badge-pill badge-success'>"+evenement.getContactsPresents().size()+"</span></td>\n" +
                                    "                <td><a href='"+Pages.EVENEMENT.client()+"?nom="+ StringCleaner.cleaner(evenement.getNom(), 80)+"&date="+evenement.getDate()+"' class='btn btn-success btn-sm' role='button'>Consulter</a></td>\n" +
                                    "            </tr>\n";
                }
                listing+=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "    </div>";
                request.setAttribute("listing",listing);
                this.getServletContext().getRequestDispatcher(Pages.LISTE_EVENEMENTS.serveur()).forward(request,response);
            }
            else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");

            if (session.getAttribute("user") != null){
                List<Evenement> evenements = new EvenementDAO().listeEvenements();
                String listing = "<div class='table-responsive-md'>\n" +
                        "        <table class='table table-hover'>\n" +
                        "            <thead>\n" +
                        "            <tr>\n" +
                        "                <th scope='col'>Nom</th>\n" +
                        "                <th scope='col'>Ville</th>\n" +
                        "                <th scope='col'>Type</th>\n" +
                        "                <th scope='col'>Date</th>\n" +
                        "                <th scope='col' class='text-center'>Nombre d'invités</th>\n" +
                        "                <th scope='col'></th>\n" +
                        "            </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n";
                for (Evenement evenement :evenements){
                    request.setAttribute("test","entreprises.size()");
                    listing+=
                            "            <tr>\n" +
                                    "                <th scope='row'>"+StringCleaner.cleaner(evenement.getNom(), 80)+"</th>\n" +
                                    "                <td>"+StringCleaner.cleaner(evenement.getAdresse().getVille(), 50)+"</td>\n" +
                                    "                <td>"+StringCleaner.cleaner(evenement.getType(), 40)+"</td>\n" +
                                    "                <td>"+evenement.getDateString()+"</td>\n" +
                                    "                <td class='text-center'><span class='badge badge-pill badge-success'>"+evenement.getContactsPresents().size()+"</span></td>\n" +
                                    "                <td><a href='"+Pages.EVENEMENT.client()+"?nom="+ StringCleaner.cleaner(evenement.getNom(), 80)+"&date="+evenement.getDate()+"' class='btn btn-success btn-sm' role='button'>Consulter</a></td>\n" +
                                    "            </tr>\n";
                }
                listing+=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "    </div>";
                request.setAttribute("listing",listing);
                this.getServletContext().getRequestDispatcher(Pages.LISTE_EVENEMENTS.serveur()).forward(request,response);
            }
            else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
