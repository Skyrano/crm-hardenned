package services;

import control.AuthentificationManager;
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

@WebServlet("/afficherProfil")
public class AfficherProfil extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        if (session.getAttribute("user") != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            request.setAttribute("role", StringCleaner.cleaner(utilisateur.getRole().getNom(), 40));
            request.setAttribute("poste", StringCleaner.cleaner(utilisateur.getPoste(), 40));
            request.setAttribute("identifiant", StringCleaner.cleaner(utilisateur.getIdentifiant(), 40));
            request.setAttribute("mail", StringCleaner.cleaner(utilisateur.getMail(), 80));
            request.setAttribute("nom", StringCleaner.cleaner(utilisateur.getNom(), 40));
            request.setAttribute("prenom", StringCleaner.cleaner(utilisateur.getPrenom(), 40));
            Role roleUtilisateur = utilisateur.getRole();
            String btn = "";
            String role = "";
            if (roleUtilisateur != null)
                role = roleUtilisateur.getNom();
            request.setAttribute("role", role);
            this.getServletContext().getRequestDispatcher(Pages.PROFIL.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        if (session.getAttribute("user") != null) {


            String role = request.getParameter("role");
            String poste = request.getParameter("poste");
            String identifiant = request.getParameter("identifiant");
            String mail = request.getParameter("mail");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String nouvMdp = request.getParameter("nouvMdp");
            String mdpConfirm = request.getParameter("mdpConfirm");
            String ancienMdp = request.getParameter("ancienMdp");

            String retourMdp = "";
            AuthentificationManager authManager = new AuthentificationManager();
            retourMdp = authManager.changementMdp(identifiant, ancienMdp, nouvMdp, mdpConfirm);

            request.setAttribute("retourMdp", retourMdp);
            request.setAttribute("role", StringCleaner.cleaner(role, 40));
            request.setAttribute("poste", StringCleaner.cleaner(poste, 40));
            request.setAttribute("identifiant", StringCleaner.cleaner(identifiant, 40));
            request.setAttribute("mail", StringCleaner.cleaner(mail, 80));
            request.setAttribute("nom", StringCleaner.cleaner(nom, 40));
            request.setAttribute("prenom", StringCleaner.cleaner(prenom, 40));
            this.getServletContext().getRequestDispatcher(Pages.PROFIL.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur());
    }


}
