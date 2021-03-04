package services;

import control.StringCleaner;
import donnees.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/creation-entreprise-from-contact")
public class CreationEntrepriseFromContact extends HttpServlet {

    /*
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        if (session.getAttribute("user") != null) {
            try {
                this.getServletContext().getRequestDispatcher(Pages.CREATION_ENTREPRISE.serveur()).forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {

            session.setAttribute("nomContact", StringCleaner.cleaner( request.getParameter("nom"), 40));
            session.setAttribute("prenom", StringCleaner.cleaner(request.getParameter("prenom"), 40));
            session.setAttribute("dateDeNaissance", request.getParameter("dateDeNaissance"));
            session.setAttribute("entreprise", StringCleaner.cleaner(request.getParameter("entreprise"), 40));
            session.setAttribute("statut", StringCleaner.cleaner(request.getParameter("statut"), 100));
            session.setAttribute("email", StringCleaner.cleaner(request.getParameter("email"), 80));
            session.setAttribute("indicateur", request.getParameter("indicateur"));
            session.setAttribute("numTelephone", request.getParameter("numTelephone"));
            session.setAttribute("idContact", request.getParameter("idContact"));

            request.setAttribute("entreprise", "value='"+StringCleaner.cleaner(request.getParameter("entreprise"), 40)+"' ");


            try {
                this.getServletContext().getRequestDispatcher(Pages.CREATION_ENTREPRISE.serveur()).forward(request,response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }


        } else {
            try {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }

    }
}