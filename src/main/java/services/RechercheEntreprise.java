package services;

import control.EntrepriseDAO;
import control.StringCleaner;
import donnees.Entreprise;
import donnees.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@WebServlet("/rechercherEntreprise")
public class RechercheEntreprise extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            this.getServletContext().getRequestDispatcher(Pages.RECHERCHER_ENTREPRISE.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {

            String nomGroupe = request.getParameter("nomGroupe");
            nomGroupe = StringCleaner.cleaner(nomGroupe, 40);
            String domaine = request.getParameter("domaine");
            domaine = StringCleaner.cleaner(domaine, 40);
            String numSiret = request.getParameter("numSiret");
            String nom = request.getParameter("nom");
            nom = StringCleaner.cleaner(nom, 40);
            String categorie = request.getParameter("categorie");
            categorie = StringCleaner.cleaner(categorie, 80);
            String pays = request.getParameter("pays");
            pays = StringCleaner.cleaner(pays, 50);
            String codePostal = request.getParameter("codePostal");


            Integer cp = "".equals(codePostal) ? null : Integer.valueOf(codePostal);
            Double siret = "".equals(numSiret) ? null : Double.valueOf(numSiret);
            List<Entreprise> entreprises = new EntrepriseDAO().recupererEntrepriseParCritere(nom, domaine, siret, categorie, pays, cp, nomGroupe);

            String listing = "";
            if (entreprises != null) {
                listing =
                        "    <div class='table-responsive-md'>\n" +
                                "        <table class='table table-hover'>\n" +
                                "            <thead>\n" +
                                "            <tr>\n" +
                                "                <th scope='col'>Nom</th>\n" +
                                "                <th scope='col'>Ville</th>\n" +
                                "                <th scope='col'>Entreprise</th>\n" +
                                "                <th scope='col'></th>\n" +
                                "            </tr>\n" +
                                "            </thead>\n" +
                                "            <tbody>\n";

                for (Entreprise ent : entreprises) {

                    listing +=
                            "            <tr>\n" +
                                    "                <th scope='row'>" + StringCleaner.cleaner(ent.getNom(), 40) + "</th>\n" +
                                    "                <td>" + StringCleaner.cleaner(ent.getAdresse().getVille(), 50) + "</td>\n" +
                                    "                <td>" + StringCleaner.cleaner(ent.getDomaine(), 40) + "</td>\n" +
                                    "                <td><a href='"+Pages.ENTREPRISE.client()+"?idEntreprise=" + String.format("%.0f",ent.getIdentifiant()) + "' class='btn btn-success btn-sm' role='button'>Consulter</a></td>\n" +
                                    "            </tr>\n";
                }
                listing +=
                        "            </tbody>\n" +
                                "        </table>\n" +
                                "    </div>";
            }

            request.setAttribute("listing", listing);
            request.setAttribute("nomGroupe", nomGroupe);
            request.setAttribute("domaine", domaine);
            request.setAttribute("numSiret", numSiret);
            request.setAttribute("nom", nom);
            request.setAttribute("categorie", categorie);
            request.setAttribute("pays", pays);
            request.setAttribute("codePostal", codePostal);

            this.getServletContext().getRequestDispatcher(Pages.RECHERCHER_ENTREPRISE.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
    }
}
