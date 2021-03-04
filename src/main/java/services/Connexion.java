package services;

import donnees.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * La servlet d'accueil de l'application
 */
@WebServlet("/login")
public class Connexion extends HttpServlet {

    /*
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        try {
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

}


