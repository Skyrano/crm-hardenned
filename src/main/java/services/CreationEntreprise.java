package services;

import control.*;
import donnees.Adresse;
import donnees.Groupe;
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
import java.util.ArrayList;

@WebServlet("/creation-entreprise")
public class CreationEntreprise extends HttpServlet{

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

            double id;
            if(siretGrp == null){
                id = recupEntrepriseId(0.0);
                siretGrp = (double)-1;
            } else{
                id = recupEntrepriseId(siretGrp);
            }

            int nbEmploye = 0;
            if(!nombreEmploye.equals("")){
                nbEmploye = Integer.parseInt(nombreEmploye);
            }

            boolean check = new EntrepriseDAO().creationEntreprise(id, nomEntreprise, domaineEntreprise, nbEmploye, addrId, siretGrp, email); //ID POUR ENTREPRISE faire une requete pour récupérer les filliales d'un groupe
            if (check){
                new LogDAO().log(LogType.CREATION_ENTREPRISE, "Création d'une nouvelle entreprise", (Utilisateur) session.getAttribute("user"));
            } else {
                new LogDAO().log(LogType.CREATION_ENTREPRISE, "Échec de la création d'une nouvelle entreprise", (Utilisateur) session.getAttribute("user"));
            }
            String a = (String) session.getAttribute("nomContact");
            log(a);
            if(session.getAttribute("nomContact") == null) {
                try {
                    this.getServletContext().getRequestDispatcher(Pages.ACCUEIL.serveur()).forward(request,response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
            }
            else if (session.getAttribute("idContact") == null) {


                request.setAttribute("nom", "value='" + StringCleaner.cleaner(session.getAttribute("nomContact").toString(), 40) + "'");
                request.setAttribute("prenom", "value='" + StringCleaner.cleaner(session.getAttribute("prenom").toString(), 40) + "'");
                request.setAttribute("dateDeNaissance", "value='" + session.getAttribute("dateDeNaissance").toString() + "'");
                request.setAttribute("nomEntreprise", "value='" + StringCleaner.cleaner(nomEntreprise, 40) + "'");
                request.setAttribute("statut", "value='" + StringCleaner.cleaner(session.getAttribute("statut").toString(), 100) + "'");
                request.setAttribute("mail", "value='" + StringCleaner.cleaner(session.getAttribute("email").toString(), 80) + "'");
                request.setAttribute("indicateur", "value='" + session.getAttribute("indicateur").toString() + "'");
                request.setAttribute("numTel", "value='" + session.getAttribute("numTelephone").toString() + "'");

                session.removeAttribute("nom");
                session.removeAttribute("prenom");
                session.removeAttribute("dateDeNaissance");
                session.removeAttribute("entreprise");
                session.removeAttribute("statut");
                session.removeAttribute("indicateur");
                session.removeAttribute("numTelephone");


                try {
                    this.getServletContext().getRequestDispatcher(Pages.CREATION_CONTACT.serveur()).forward(request,response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
            }
            else {
                String idContactString = "";
                idContactString += ((String)session.getAttribute("idContact"));

                session.removeAttribute("idContact");
                request.setAttribute("nom", "value='" + StringCleaner.cleaner(session.getAttribute("nomContact").toString() , 40)+ "'");
                request.setAttribute("prenom", "value='" + StringCleaner.cleaner(session.getAttribute("prenom").toString(), 40) + "'");
                request.setAttribute("dateDeNaissance", "value='" + StringCleaner.cleaner(session.getAttribute("dateDeNaissance").toString(), 40) + "'");
                request.setAttribute("nomEntreprise", "value='" + StringCleaner.cleaner(nomEntreprise, 40) + "'");
                request.setAttribute("statut", "value='" + StringCleaner.cleaner(session.getAttribute("statut").toString(), 100) + "'");
                request.setAttribute("mail", "value='" + StringCleaner.cleaner(session.getAttribute("email").toString(), 80) + "'");
                request.setAttribute("indicateur", "value='" + session.getAttribute("indicateur").toString() + "'");
                request.setAttribute("numTel", "value='" + session.getAttribute("numTelephone").toString() + "'");

                session.removeAttribute("nom");
                session.removeAttribute("prenom");
                session.removeAttribute("dateDeNaissance");
                session.removeAttribute("entreprise");
                session.removeAttribute("statut");
                session.removeAttribute("indicateur");
                session.removeAttribute("numTelephone");


                request.setAttribute("listeMultipleEntreprise", "");

                request.setAttribute("idContact", idContactString);
                try {
                    this.getServletContext().getRequestDispatcher(Pages.MODIFIER_CONTACT.serveur()).forward(request, response);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }

            }




        } else {
            try {
                this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request,response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }


    private Double creationGroupe (HttpServletRequest request, String nomGroupe, double siretGroupe){
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

    private double recupEntrepriseId(double siretGroupe){
        ArrayList<Double> allId =  new EntrepriseDAO().recupAllIdEntreprise();
        double newId = siretGroupe *10;

        while (allId.contains(newId)){
            newId++;
        }
        return newId;
    }

}
