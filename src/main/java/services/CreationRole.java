package services;


import control.LogDAO;
import control.LogType;
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

@WebServlet("/creation-role")
public class CreationRole extends HttpServlet {

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
            this.getServletContext().getRequestDispatcher(Pages.CREATION_ROLE.serveur()).forward(request, response);
        }
        else
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

            String nom = request.getParameter("nom");
            String ecritureChaine = request.getParameter("ecriture");
            boolean ecriture = "ecriture".equals("ecriture");
            String creation = "";

            if (nom != null) {
                nom = StringCleaner.cleaner(nom, 40);
                Role r = new Role(nom, ecriture);
                if (new RoleDAO().creerRole(r)) {
                    creation = "Votre rôle a bien été créé";
                    new LogDAO().log(LogType.AJOUT_LOG, "création role "+r.getNom(), utilisateur);
                }
                else
                    creation = "Nous n'avons pas pu créer votre rôle";
            }

            request.setAttribute("creation", creation);
            request.setAttribute("nom", "value='" + StringCleaner.cleaner(nom, 40) + "'");

            this.getServletContext().getRequestDispatcher(Pages.CREATION_ROLE.serveur()).forward(request, response);
        }else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
    }
}
