package services;

import control.*;
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


/**
 * La servlet d'accueil de l'application
 */
@WebServlet("/modification-evenement")
public class ModificationEvenement extends HttpServlet {

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
                String nom = request.getParameter("nom");
                nom = nom.replace('+', ' ');
                nom = StringCleaner.cleaner(nom, 80);
                Date date = Date.valueOf(request.getParameter("date"));
                Evenement evenement = new EvenementDAO().recupererEvenement(nom, date);
                request.setAttribute("evenement", evenement);
                this.getServletContext().getRequestDispatcher(Pages.MODIFIER_EVENEMENT.serveur()).forward(request, response);
            } else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                nom = StringCleaner.cleaner(nom, 80);
                String date = request.getParameter("date");
                String heure = request.getParameter("heure");
                String type = request.getParameter("type");
                type = StringCleaner.cleaner(type, 40);
                String description = request.getParameter("description");
                String nmRue = (request.getParameter("numRue"));
                int numRue = 0;
                if (!"".equals(nmRue) && nmRue != null)
                    numRue = Integer.parseInt(nmRue);
                String rue = request.getParameter("rue");
                rue = StringCleaner.cleaner(rue, 100);
                String cp = request.getParameter("codePostal");
                int codePostal = 0;
                if (!"".equals(nmRue) && nmRue != null)
                    codePostal = Integer.parseInt(cp);
                String ville = request.getParameter("ville");
                ville = StringCleaner.cleaner(ville, 50);
                String pays = request.getParameter("pays");
                pays = StringCleaner.cleaner(pays, 30);
                String retourCreation = "L'événement n'a pas pu être modifié";
                if (!(nom == null || date == null || heure == null || type == null || description == null || numRue == 0 || ville == null || pays == null || codePostal == 0)) {
                    if (new EvenementDAO().modificationEvenement(nom, date, heure, type, description, numRue, rue, codePostal, ville, pays)) {
                        retourCreation = "L'événement a bien été modifié";
                        new LogDAO().log(LogType.MODIFICATION_EVENEMENT, "modification de l'événement " + nom, utilisateur);
                    }
                }
                request.setAttribute("retourCreation", retourCreation);
                 new EvenementDAO().modificationEvenement(nom, date, heure, type, description, numRue, rue, codePostal, ville, pays);
                this.getServletContext().getRequestDispatcher("/"+ Pages.LISTE_EVENEMENTS.client()).forward(request, response);
            } else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

}


