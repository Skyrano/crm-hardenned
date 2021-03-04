package services;

import control.ContactDAO;
import control.EntrepriseDAO;
import control.GroupeDAO;
import donnees.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@WebServlet("/statistiques")
public class AfficherStatistiques extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {

            int groupesDifferents = new GroupeDAO().recupNbGroupe();
            int entreprisesDifferentes = new EntrepriseDAO().listeEntreprises().size();
            int contactsDifferents = new ContactDAO().listeContacts().size();
            int contactsSansEntreprise = new ContactDAO().recupNbContactSansEntreprise();
            float contactParEntrepriseMoyenne = (contactsDifferents-contactsSansEntreprise)/entreprisesDifferentes;
            ArrayList<Integer> listeTaille = new EntrepriseDAO().recupTaillEntreprise();
            int entrepriseMoinsDe10 = listeTaille.get(0);
            int entrepriseMoinsDe50 = listeTaille.get(1);
            int entrepriseMoinsDe150 = listeTaille.get(2);
            int entrepriseMoinsDe500 = listeTaille.get(3);
            int entreprisePlusDe500 = listeTaille.get(4);

            request.setAttribute("groupesDifferents", Integer.toString(groupesDifferents));
            request.setAttribute("entreprisesDifferentes", Integer.toString(entreprisesDifferentes));
            request.setAttribute("contactsDifferents", Integer.toString(contactsDifferents));
            request.setAttribute("contactsSansEntreprise", Integer.toString(contactsSansEntreprise));
            request.setAttribute("contactParEntrepriseMoyenne", Float.toString(contactParEntrepriseMoyenne));
            request.setAttribute("entrepriseMoinsDe10", Integer.toString(entrepriseMoinsDe10));
            request.setAttribute("entrepriseMoinsDe50", Integer.toString(entrepriseMoinsDe50));
            request.setAttribute("entrepriseMoinsDe150", Integer.toString(entrepriseMoinsDe150));
            request.setAttribute("entrepriseMoinsDe500", Integer.toString(entrepriseMoinsDe500));
            request.setAttribute("entreprisePlusDe500", Integer.toString(entreprisePlusDe500));

            this.getServletContext().getRequestDispatcher(Pages.STATISTIQUES.serveur()).forward(request,response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);

    }

}
