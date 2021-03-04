package services;

import control.ContactDAO;
import control.LogDAO;
import control.LogType;
import control.StringCleaner;
import donnees.Contact;
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
import java.util.List;


/**
 * La servlet d'accueil de l'application
 */
@WebServlet("/mail")
public class MailTo extends HttpServlet {

    /*
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            List<Contact> contacts = new ContactDAO().recupererTousContacts();
            String listing = "";
            for (Contact contact:contacts){
                listing+="<option value='"+ StringCleaner.cleaner(contact.getMail(), 80)+"'>"+StringCleaner.cleaner(contact.toString(), 100)+" &lt;"+StringCleaner.cleaner(contact.getMail(), 80)+"&gt;</option>\n";
            }
            String mailto = "<option value='' selected disabled hidden></option>";
            request.setAttribute("listing", listing);
            request.setAttribute("mailto", mailto);
            request.setAttribute("alert", "");
            this.getServletContext().getRequestDispatcher(Pages.MAIL.serveur()).forward(request, response);
        }
        else {
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        String alert = "";

        if (session.getAttribute("user") != null) {
            List<Contact> contacts = new ContactDAO().recupererTousContacts();
            String listing = "";
            for (Contact contact:contacts){
                listing+="<option value='"+StringCleaner.cleaner(contact.getMail(), 80)+"'>"+StringCleaner.cleaner(contact.toString(), 100)+" &lt;"+StringCleaner.cleaner(contact.getMail(), 80)+"&gt;</option>\n";
            }

            String NomPrenom = request.getParameter("NomPrenom");
            String mail = request.getParameter("mail");
            String mailto = "<option value='' selected disabled hidden></option>";
            if (NomPrenom != null && !NomPrenom.isEmpty() && mail != null && !mail.isEmpty()) {
                mail = StringCleaner.cleaner(mail, 80);
                NomPrenom = StringCleaner.cleaner(NomPrenom, 40);
                mailto = "<option selected value='"+mail+"'>"+NomPrenom+" &lt;"+mail+"&gt;</option>";
            }
            if (request.getParameter("contenuMail") != null && !request.getParameter("contenuMail").isEmpty()) {
                alert = "<div class=\"alert alert-success text-center\" role=\"alert\">\n" +
                        "  Mail envoyé !\n" +
                        "</div>";
                new LogDAO().log(LogType.ENVOI_MAIL, "Envoi d'un mail à "+NomPrenom, (Utilisateur) session.getAttribute("user"));
            }

            request.setAttribute("listing", listing);
            request.setAttribute("mailto", mailto);
            request.setAttribute("alert", alert);
            this.getServletContext().getRequestDispatcher(Pages.MAIL.serveur()).forward(request, response);
        }
        else {
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
        }
    }

}


