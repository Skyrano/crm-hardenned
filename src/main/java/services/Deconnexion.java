package services;

import control.LogDAO;
import control.LogType;
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

/**
 * La servlet de deconnexion de l'application
 */
@WebServlet("/logout")
public class Deconnexion extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (utilisateur != null) {
            new LogDAO().log(LogType.DECONNEXION_UTILISATEUR, "Deconnexion de l'utilisateur", utilisateur);
            session.setAttribute("user", null);
            session.invalidate();

            session = request.getSession(false);
            if (session != null) {
                new LogDAO().log(LogType.DECONNEXION_UTILISATEUR, "Problème de deconnexion de l'utilisateur", utilisateur);
                try {
                    String message = "<div class='alert alert-danger text-center' role='alert'>\n" +
                            "               <p class=\"lead\">Problème lors de la déconnexion !</p>\n" +
                            "         </div>\n" +
                            "         <hr class=\"my-4\">\n";
                    request.setAttribute("message", message);
                    this.getServletContext().getRequestDispatcher(Pages.LOGOUT.serveur()).forward(request, response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
            }

            try {
                String message = "<div class='alert alert-sucess text-center' role='alert'>\n" +
                        "               <p class=\"lead\">Vous avez bien été déconnecté.</p>\n" +
                        "         </div>\n" +
                        "         <hr class=\"my-4\">\n" +
                        "         <p>Pour vous reconnecter cliquez sur le bouton ci-dessous.</p>\n" +
                        "         <a class=\"btn btn-ensibs btn-md\" href='"+Pages.LOGIN.client()+"' role=\"button\">Retour</a>";
                request.setAttribute("message", message);
                this.getServletContext().getRequestDispatcher(Pages.LOGOUT.serveur()).forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }
}
