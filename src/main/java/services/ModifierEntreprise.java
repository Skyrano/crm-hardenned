package services;

import control.*;
import donnees.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


@WebServlet("/modifier-entreprise")
public class ModifierEntreprise extends HttpServlet {

    /*
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {

            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");

            String idEntreprise = request.getParameter("idEntreprise");

            Entreprise entreprise = new EntrepriseDAO().recupEntreprise(Double.parseDouble(idEntreprise));

            request.setAttribute("siret", "value='" +String.format("%.0f",entreprise.getGroupeParent().getNumSiret())+"'");
            request.setAttribute("nomGroupe", "value='" + StringCleaner.cleaner(entreprise.getGroupeParent().getNom(), 40)+"'");
            request.setAttribute("domaineGroupe", "value='" +StringCleaner.cleaner(entreprise.getGroupeParent().getDomaine(), 40)+"'");
            request.setAttribute("numRueGroupe", "value='" +entreprise.getGroupeParent().getSiegeSocial().getNumeroRue()+"'");
            request.setAttribute("rueGroupe", "value='" +StringCleaner.cleaner(entreprise.getGroupeParent().getSiegeSocial().getRue(), 100)+"'");
            request.setAttribute("codePostalGroupe", "value='" +entreprise.getGroupeParent().getSiegeSocial().getCodePostal()+"'");
            request.setAttribute("villeGroupe", "value='" +StringCleaner.cleaner(entreprise.getGroupeParent().getSiegeSocial().getVille(), 50)+"'");
            request.setAttribute("paysGroupe", "value='" +StringCleaner.cleaner(entreprise.getGroupeParent().getSiegeSocial().getPays(), 30)+"'");

            request.setAttribute("nomEntreprise","value='" +StringCleaner.cleaner(entreprise.getNom(), 40)+"'");
            request.setAttribute("domaineEntreprise", "value='" + StringCleaner.cleaner(entreprise.getDomaine(), 40)+"'");
            request.setAttribute("nbEmploye", "value='" +entreprise.getNbEmployes()+"'");
            request.setAttribute("numRueEntreprise", "value='" +entreprise.getAdresse().getNumeroRue()+"'");
            request.setAttribute("rueEntreprise", "value='" +StringCleaner.cleaner(entreprise.getAdresse().getRue(), 100)+"'");
            request.setAttribute("codePostalEntreprise", "value='" +entreprise.getAdresse().getCodePostal()+"'");
            request.setAttribute("villeEntreprise", "value='" +StringCleaner.cleaner(entreprise.getAdresse().getVille(), 50)+"'");
            request.setAttribute("paysEntreprise", "value='" +StringCleaner.cleaner(entreprise.getAdresse().getPays(), 30)+"'");

            request.setAttribute("email", "value='" +StringCleaner.cleaner(entreprise.getMailContact(), 80)+"'");

            try {
                this.getServletContext().getRequestDispatcher(Pages.MODIFIER_ENTREPRISE.serveur()).forward(request,response);
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

            String idEntrepriseString = request.getParameter("idEntreprise");
            double idEntreprise = 0.0;
            if (idEntrepriseString != null)
                idEntreprise = Double.parseDouble(idEntrepriseString);

            String siretGroupe = request.getParameter("siret");
            siretGroupe = siretGroupe.replace(" ", "");
            siretGroupe = siretGroupe.replace("-", "");

            boolean entrepriseEstNewGroupe = false;
            Double siretGrp = null;
            if(!siretGroupe.equals("")){
                siretGrp = Double.parseDouble(siretGroupe);
                Groupe groupe = new GroupeDAO().recupererGroupe(siretGrp);
                if(groupe == null){

                    String nomGroupe = request.getParameter("nomGroupe");

                    if (!nomGroupe.equals("")){
                        nomGroupe = StringCleaner.cleaner(nomGroupe, 40);
                        siretGrp = this.creationGroupe(request, nomGroupe, siretGrp);

                    } else{
                        entrepriseEstNewGroupe = true;
                    }
                }
            } else {
                siretGrp = null;
            }

            String numRueEntreprise = request.getParameter("numRueEntreprise");
            int numRueEnt = 0;
            if(!numRueEntreprise.equals("")){
                numRueEnt = Integer.parseInt(numRueEntreprise);
            }
            String rueEntreprise = request.getParameter("rueEntreprise");
            rueEntreprise = StringCleaner.cleaner(rueEntreprise, 100);
            String codePostalEntreprise = request.getParameter("codePostalEntreprise");
            int cdPostalEnt = 0;
            if(!codePostalEntreprise.equals("")){
                cdPostalEnt = Integer.parseInt(codePostalEntreprise);
            }
            String villeEntreprise = request.getParameter("villeEntreprise");
            villeEntreprise = StringCleaner.cleaner(villeEntreprise, 50);
            String paysEntreprise = request.getParameter("paysEntreprise");
            paysEntreprise = StringCleaner.cleaner(paysEntreprise, 30);


            Adresse adresseEntreprise = new AdresseDAO().recupererAdresse(paysEntreprise, villeEntreprise, cdPostalEnt, rueEntreprise, numRueEnt);
            if(adresseEntreprise == null){
                if(new AdresseDAO().creationAdresse(paysEntreprise, villeEntreprise, cdPostalEnt, rueEntreprise, numRueEnt)){
                    adresseEntreprise = new AdresseDAO().recupererAdresse(paysEntreprise, villeEntreprise, cdPostalEnt, rueEntreprise, numRueEnt);
                }
            }

            String nomEntreprise = request.getParameter("nomEntreprise");
            nomEntreprise = StringCleaner.cleaner(nomEntreprise, 40);
            String domaineEntreprise = request.getParameter("domaineEntreprise");
            domaineEntreprise = StringCleaner.cleaner(domaineEntreprise, 40);
            String nombreEmploye = request.getParameter("nbEmploye");
            String email = request.getParameter("email");
            email = StringCleaner.cleaner(email, 80);



            int addrId;
            if (adresseEntreprise == null){
                addrId = 1;
            } else {
                addrId = adresseEntreprise.getIdentifiant();
            }

            if(entrepriseEstNewGroupe){
                if (new GroupeDAO().creationGroupe(siretGrp, nomEntreprise, domaineEntreprise, addrId)){
                    siretGrp = new GroupeDAO().recupererGroupe(siretGrp).getNumSiret();
                }
            }


            int nbEmploye = 0;
            if(!nombreEmploye.equals("")){
                nbEmploye = Integer.parseInt(nombreEmploye);
            }

            boolean check = new EntrepriseDAO().modifierEntreprise(nomEntreprise, domaineEntreprise, nbEmploye, addrId, siretGrp, email, idEntreprise); //ID POUR ENTREPRISE faire une requete pour récupérer les filliales d'un groupe
            if (check){
                new LogDAO().log(LogType.MODIFICATION_ENTREPRISE, "Modification d'une entreprise", (Utilisateur) session.getAttribute("user"));
            } else {
                new LogDAO().log(LogType.MODIFICATION_ENTREPRISE, "Échec de la modification d'une entreprise", (Utilisateur) session.getAttribute("user"));
            }

            RequestDispatcher rd = request.getRequestDispatcher(Pages.ENTREPRISE.client());
            try {
                rd.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
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


    private Double creationGroupe ( HttpServletRequest request, String nomGroupe, double siretGroupe){
        String domaineGroupe = request.getParameter("domaineGroupe");
        domaineGroupe = StringCleaner.cleaner(domaineGroupe, 40);
        String numRueGroup = request.getParameter("numRueGroup");
        int numRueGrp = 0;
        if(!numRueGroup.equals("")){
            numRueGrp = Integer.parseInt(numRueGroup);
        }
        String rueGroupe = request.getParameter("rueGroupe");
        rueGroupe = StringCleaner.cleaner(rueGroupe, 100);
        String codePostalGroupe = request.getParameter("codePostalGroupe");
        int cdPostal = 0;
        if(!codePostalGroupe.equals("")){
            cdPostal = Integer.parseInt(codePostalGroupe);
        }
        String villeGroupe = request.getParameter("villeGroupe");
        villeGroupe = StringCleaner.cleaner(villeGroupe, 50);
        String paysGroupe = request.getParameter("paysGroupe");
        paysGroupe = StringCleaner.cleaner(paysGroupe, 30);

        Adresse adresseGroupe = new AdresseDAO().recupererAdresse(paysGroupe, villeGroupe, cdPostal, rueGroupe, numRueGrp);
        if(adresseGroupe == null){
            if(new AdresseDAO().creationAdresse(paysGroupe, villeGroupe, cdPostal, rueGroupe, numRueGrp)){
                adresseGroupe = new AdresseDAO().recupererAdresse(paysGroupe, villeGroupe, cdPostal, rueGroupe, numRueGrp);
            }
        }

        if (new GroupeDAO().creationGroupe(siretGroupe, nomGroupe, domaineGroupe, adresseGroupe.getIdentifiant())){
            return new GroupeDAO().recupererGroupe(siretGroupe).getNumSiret();
        }else {
            return null;
        }
    }

}
