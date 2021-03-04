package services;

import control.*;
import donnees.Entreprise;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * La servlet d'accueil de l'application
 */
@WebServlet("/creation-evenement")
public class CreationEvenement extends HttpServlet {

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
                this.getServletContext().getRequestDispatcher(Pages.CREATION_EVENEMENT.serveur()).forward(request, response);
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
                String date = request.getParameter("date");

                SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dateTransfo = f1.parse(date);
                request.setAttribute("date", dateTransfo);

                String heure = request.getParameter("heure");
                String type = request.getParameter("type");
                String description = request.getParameter("description");
                String nmRue = (request.getParameter("numRue"));
                int numRue = 0;
                if (!"".equals(nmRue) && nmRue != null)
                    numRue = Integer.parseInt(nmRue);
                String rue = request.getParameter("rue");
                String cp = request.getParameter("codePostal");
                int codePostal = 0;
                if (!"".equals(nmRue) && nmRue != null)
                    codePostal = Integer.parseInt(cp);
                String ville = request.getParameter("ville");
                String pays = request.getParameter("pays");
                String retourCreation = "L'événement n'a pas pu être créé";
                if (!(nom == null || date == null || heure == null || type == null || description == null || numRue == 0 || ville == null || pays == null || codePostal == 0)) {
                    if (new EvenementDAO().creationEvenement(StringCleaner.cleaner(nom, 80), date, heure, StringCleaner.cleaner(type, 40), StringCleaner.cleaner(description, 1000), numRue, StringCleaner.cleaner(rue, 100), codePostal, StringCleaner.cleaner(ville, 50), StringCleaner.cleaner(pays, 30))) {
                        retourCreation = "L'événement a bien été créé";
                        new LogDAO().log(LogType.MODIFICATION_EVENEMENT, "modification de l'événement " + StringCleaner.cleaner(nom, 80), utilisateur);
                    }
                }
                request.setAttribute("retourCreation", retourCreation);

                RequestDispatcher rd = request.getRequestDispatcher("evenement");
                try {
                    rd.forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            }
        } catch (IOException | ServletException | ParseException e) {
            e.printStackTrace();
        }
    }

}


