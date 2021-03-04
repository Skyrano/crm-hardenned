package services;

import control.AuthentificationManager;
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
import java.io.IOException;
import java.util.List;


@WebServlet("/afficherUtilisateur")
public class AfficherUtilisateur extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = (String) request.getAttribute("identifiant");
        if (id == null) {
                this.getServletContext().getRequestDispatcher(Pages.LISTE_UTILISATEURS.serveur()).forward(request, response);
        }

        Utilisateur utilisateur = new AuthentificationManager().recupererUtilisateurParID(id);

        request.setAttribute("selectRole", StringCleaner.cleaner(utilisateur.getRole().getNom(), 40));
        request.setAttribute("poste", StringCleaner.cleaner(utilisateur.getPoste(), 40));
        request.setAttribute("identifiant", StringCleaner.cleaner(utilisateur.getIdentifiant(), 40));
        request.setAttribute("mail", StringCleaner.cleaner(utilisateur.getMail(), 80));
        request.setAttribute("nom", StringCleaner.cleaner(utilisateur.getNom(), 40));
        request.setAttribute("prenom", StringCleaner.cleaner(utilisateur.getPrenom(), 40));

        List<Role> roles = new RoleDAO().recupererRoles();
        String rolesAffichage = "" +
                "    <div class='form-row' style='display: initial'>\n" +
                "       <label> Voici les roles disponibles</label>\n";
        for (int i = 0; i < roles.size(); i++) {
            String checked = roles.get(i).getNom().equals(utilisateur.getRole().getNom()) ? "checked" : "";
            rolesAffichage +=
                    "       <div class='form-row'>\n" +
                            "            <div class='form-group col-md-4'>\n" +
                            "                <label>" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "</label>\n" +
                            "             </div>\n" +
                            "             <div class='form-group col-md-1'>\n" +
                            "                 <input class='form-check-input' type='radio'  name='selectRole' " + checked + " id='selectRole" + i + "' value='" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "'>\n" +
                            "             </div>\n" +
                            "        </div>\n";
        }
        rolesAffichage += "    </div>\n";
        request.setAttribute("listeRoles", rolesAffichage);
        this.getServletContext().getRequestDispatcher(Pages.UTILISATEUR.serveur()).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = (String) request.getParameter("identifiant");
        if (id == null)
            this.getServletContext().getRequestDispatcher(Pages.LISTE_UTILISATEURS.serveur()).forward(request, response);

        Utilisateur utilisateur = new AuthentificationManager().recupererUtilisateurParID(id);
        request.setAttribute("selectRole", StringCleaner.cleaner(utilisateur.getRole().getNom(), 40));
        request.setAttribute("poste", StringCleaner.cleaner(utilisateur.getPoste(), 40));
        request.setAttribute("identifiant", StringCleaner.cleaner(utilisateur.getIdentifiant(), 40));
        request.setAttribute("mail", StringCleaner.cleaner(utilisateur.getMail(), 80));
        request.setAttribute("nom", StringCleaner.cleaner(utilisateur.getNom(), 40));
        request.setAttribute("prenom", StringCleaner.cleaner(utilisateur.getPrenom(), 40));

        List<Role> roles = new RoleDAO().recupererRoles();
        String rolesAffichage = "" +
                "    <div class='form-row' style='display: initial'>\n" +
                "       <label> Voici les roles disponibles</label>\n";
        for (int i = 0; i < roles.size(); i++) {
            String checked = StringCleaner.cleaner(roles.get(i).getNom(), 40).equals(StringCleaner.cleaner(utilisateur.getRole().getNom(), 40)) ? "checked" : "";
            rolesAffichage +=
                    "       <div class='form-row'>\n" +
                            "            <div class='form-group col-md-4'>\n" +
                            "                <label>" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "</label>\n" +
                            "             </div>\n" +
                            "             <div class='form-group col-md-1'>\n" +
                            "                 <input class='form-check-input' type='radio'  name='selectRole' " + checked + " id='selectRole" + i + "' value='" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "'>\n" +
                            "             </div>\n" +
                            "        </div>\n";
        }
        rolesAffichage += "    </div>\n";
        request.setAttribute("listeRoles", rolesAffichage);
        this.getServletContext().getRequestDispatcher(Pages.UTILISATEUR.serveur()).forward(request, response);
    }
}
