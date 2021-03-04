package services;

import control.RoleDAO;
import control.StringCleaner;
import donnees.Pages;
import donnees.Role;
import donnees.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/supprimerRole")
public class SupprimerRole extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {

            String id = request.getParameter("idSuppression");
            String retourSuppression;
            if (id.equals("admin"))
                retourSuppression = "Impossible de supprimer un administrateur.";
            else
                retourSuppression = new RoleDAO().supprimerRole(id) ? "Votre role a été supprimé" : "Il y a eu une erreur";

            List<Role> roles = new RoleDAO().recupererRoles();
            String listing = "" +
                    "    <div class='table-responsive-md'>\n" +
                    "        <table class='table table-hover'>\n" +
                    "            <thead>\n" +
                    "            <tr>\n" +
                    "                <th scope='col' class='text-center'>Nom</th>\n" +
                    "                <th scope='col' class='text-center'>Nombre d'utilisateurs</th>\n" +
                    "                <th scope='col'class='text-center'> </th>\n" +
                    "            </tr>\n" +
                    "            </thead>\n" +
                    "            <tbody>\n";
            for (Role r : roles) {
                listing +=
                        "            <tr>\n" +
                                "                <th scope='row' class='text-center'>" + StringCleaner.cleaner(r.getNom(), 40) + "</th>\n" +
                                "                <th scope='row' class='text-center'>" + new RoleDAO().recupNbUtilisateursParRole(r.getNom()) + "</th>\n";
                if (!"admin".equals(r.getNom()) && !"user".equals(r.getNom())) {
                    listing += "                <td><button data-toggle='modal' data-target='#modalSuppr' data-whatever = '" + StringCleaner.cleaner(r.getNom(), 40) + "' class='btn btn-danger btn-sm ' role='button'><svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-trash\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                            "  <path d=\"M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z\"/></btn>\n" +
                            "  <path fill-rule=\"evenodd\" d=\"M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z\"/>\n" +
                            "</svg></a></td>\n" +
                            "            </tr>\n";
                }
            }
            listing +=
                    "            </tbody>\n" +
                            "        </table>\n" +
                            "    </div>";
            request.setAttribute("listing", listing);
            request.setAttribute("retourSuppression", retourSuppression);

            this.getServletContext().getRequestDispatcher(Pages.GESTION_ROLES.serveur()).forward(request, response);
        }else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doGet(request, response);
    }
}
