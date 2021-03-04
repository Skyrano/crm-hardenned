package services;

import control.*;
import donnees.Pages;
import donnees.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/modifier-utilisateur")
public class ModifierUtilisateur extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {
            this.getServletContext().getRequestDispatcher(Pages.LISTE_UTILISATEURS.serveur()).forward(request, response);
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
            String role = (String) request.getParameter("selectRole");
            role = StringCleaner.cleaner(role, 40);
            String poste = (String) request.getParameter("poste");
            poste = StringCleaner.cleaner(poste, 40);
            String mail = (String) request.getParameter("mail");
            mail = StringCleaner.cleaner(mail, 80);
            String nom = (String) request.getParameter("nom");
            nom = StringCleaner.cleaner(nom, 40);
            String prenom = (String) request.getParameter("prenom");
            prenom = StringCleaner.cleaner(prenom, 40);
            String identifiant = (String) request.getParameter("identifiant");
            identifiant = StringCleaner.cleaner(identifiant, 40);
            String mdp = (String) request.getParameter("mdp");


            mdp = StringCleaner.cleaner(mdp, 128);
            Utilisateur ut = new Utilisateur(identifiant, mail, nom, prenom, poste, new RoleDAO().recupererRole(role), 0);
            String verif= "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                            "  Erreur dans la modification" +
                            "</div>";

            AuthentificationManager authManager = new AuthentificationManager();
            String idAdmin = request.getParameter("idAdmin");
            String mdpAdmin = request.getParameter("mdpAdmin");
            if(authManager.modifierUtilisateur(ut, mdp, idAdmin, mdpAdmin)){
                verif = "<div class=\"alert alert-success text-center\" role=\"alert\">\n" +
                        "  Utilisateur bien modifié !\n" +
                        "</div>";
                 new LogDAO().log(LogType.MODIFICATION_UTILISATEUR, "Modification de l'utilisateur "+ut.getIdentifiant(), utilisateur);
            }
            else {
                verif = "<div class=\"alert alert-danger text-center\" role=\"alert\">\n" +
                        "  Erreur de modification !\n" +
                        "</div>";
                new LogDAO().log(LogType.MODIFICATION_UTILISATEUR, "Erreur de modification de l'utilisateur "+ut.getIdentifiant(), utilisateur);
            }
            request.setAttribute("identifiant", identifiant);
            request.setAttribute("verif", verif);
            RequestDispatcher rd = request.getRequestDispatcher(Pages.UTILISATEUR.client());
            rd.forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
    }
}
