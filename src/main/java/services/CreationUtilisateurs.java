package services;

import control.*;
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

@WebServlet("/creation-utilisateur")
public class CreationUtilisateurs extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {
            List<Role> roles = new RoleDAO().recupererRoles();
            String rolesAffichage = "" +
                    "    <div class='form-row' style='display: initial'>\n" +
                    "       <label> Veuillez choisir le rôle choisi ci-dessous</label>\n";
            for (int i = 0; i < roles.size(); i++) {
                rolesAffichage +=
                        "       <div class='form-row'>\n" +
                                "            <div class='form-group col-md-4'>\n" +
                                "                <label>" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "</label>\n" +
                                "             </div>\n" +
                                "             <div class='form-group col-md-1'>\n" +
                                "                 <input class='form-check-input' type='radio' name='selectRole' id='selectRole" + i + "' value='" + i + "'>\n" +
                                "             </div>\n" +
                                "        </div>\n";
            }
            rolesAffichage += "    </div>\n";
            request.setAttribute("listeRoles", rolesAffichage);
            this.getServletContext().getRequestDispatcher(Pages.CREATION_UTILISATEUR.serveur()).forward(request, response);
        }else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {
            String identifiant = request.getParameter("identifiant");
            identifiant = StringCleaner.cleaner(identifiant, 40);
            String motDePasse = request.getParameter("motDePasse");
            motDePasse = StringCleaner.cleaner(motDePasse, 128);
            String nom = request.getParameter("nom");
            nom = StringCleaner.cleaner(nom, 40);
            String prenom = request.getParameter("prenom");
            prenom = StringCleaner.cleaner(prenom, 40);
            String poste = request.getParameter("poste");
            poste = StringCleaner.cleaner(poste, 40);
            String email = request.getParameter("email");
            email = StringCleaner.cleaner(email, 80);

            List<Role> roles = new RoleDAO().recupererRoles();
            String rolesAffichage = "" +
                    "    <div class='form-row' style='display: initial'>\n" +
                    "       <label> Veuillez choisir un rôle ci-dessous</label>\n";
            for (int i = 0; i < roles.size(); i++) {
                rolesAffichage +=
                        "       <div class='form-row'>\n" +
                                "            <div class='form-group col-md-4'>\n" +
                                "                <label>" + StringCleaner.cleaner(roles.get(i).getNom(), 40) + "</label>\n" +
                                "             </div>\n" +
                                "             <div class='form-group col-md-1'>\n" +
                                "                 <input class='form-check-input' type='radio' name='selectRole' id='selectRole" + i + "' value='" + i + "'>\n" +
                                "             </div>\n" +
                                "        </div>\n";
            }
            rolesAffichage += "    </div>\n";
            request.setAttribute("listeRoles", rolesAffichage);
            String creation = "";

            String role = request.getParameter("selectRole");
            String id = request.getParameter("id");
            String mdp = request.getParameter("mdp");
            if (role != null) {
                Utilisateur ut = new Utilisateur(identifiant, email, nom, prenom, poste, new RoleDAO().recupererRole(roles.get(Integer.parseInt(role)).getNom()), 0);
                AuthentificationManager authManager = new AuthentificationManager();
                if (authManager.creerUtilisateur(ut, motDePasse, id, mdp)) {
                    creation = "<div class='alert alert-success text-center' role='alert'>Votre utilisateur a bien été créé !</div>";
                    new LogDAO().log(LogType.AJOUT_UTILISATEUR, "utilisateur créé " + ut.getIdentifiant(), utilisateur );
                } else
                    creation = "<div class='alert alert-success text-center' role='alert'>Nous n'avons pas pu créer votre utilisateur</div>";
            }

            request.setAttribute("creation", creation);
            request.setAttribute("identifiant", "value='" + identifiant + "'");
            request.setAttribute("nom", "value='" + nom + "'");
            request.setAttribute("prenom", "value='" + prenom + "'");
            request.setAttribute("poste", "value='" + poste + "'");
            request.setAttribute("email", "value='" + email + "'");
            request.setAttribute("role", "value='" + role + "'");

            this.getServletContext().getRequestDispatcher(Pages.CREATION_UTILISATEUR.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
    }
}

