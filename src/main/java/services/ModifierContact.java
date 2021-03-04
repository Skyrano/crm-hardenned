package services;

import control.*;
import donnees.Contact;
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
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/modifier-contact")
public class ModifierContact extends HttpServlet{



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

            String idContactString = request.getParameter("idContact");
            int idContact = 0;
            if (idContactString != null)
                idContact = Integer.parseInt(idContactString);

            Contact contact = new ContactDAO().recupContactParId(idContact);
            Date dateNaissance = contact.getDateNaissance();
            String dateNaissancee = "";
            if (dateNaissance != null) {
                String jour = "" + dateNaissance.toLocalDate().getDayOfMonth();
                if (dateNaissance.toLocalDate().getDayOfMonth() < 10) {
                    jour = "0" + dateNaissance.toLocalDate().getDayOfMonth();
                }
                String mois = "" + dateNaissance.toLocalDate().getMonthValue();

                if (dateNaissance.toLocalDate().getMonthValue() < 10) {
                    mois = "0" + dateNaissance.toLocalDate().getMonthValue();
                }
                dateNaissancee = jour + "/" + mois + "/" + dateNaissance.toLocalDate().getYear();
            }

            String contactNumTel = contact.getNumTel();
            String indicateur = "";
            String numTel = "";

            if (contactNumTel.split(" ").length == 1){
                numTel = contactNumTel;
            } else if (contactNumTel.split(" ").length == 2){
                indicateur = contactNumTel.split(" ")[0];
                numTel = contactNumTel.split(" ")[1];
            }


            request.setAttribute("nom", "value='" + StringCleaner.cleaner(contact.getNom(), 40) + "'");
            request.setAttribute("prenom", "value='" +StringCleaner.cleaner( contact.getPrenom(), 40) + "'");
            request.setAttribute("dateDeNaissance", "value='" + dateNaissancee + "'");
            request.setAttribute("nomEntreprise", "value='" + StringCleaner.cleaner(contact.getEntreprise().getNom(), 40) + "'");
            request.setAttribute("statut", "value='" + StringCleaner.cleaner(contact.getStatut(), 40) + "'");
            request.setAttribute("mail", "value='" + StringCleaner.cleaner(contact.getMail(), 80) + "'");
            request.setAttribute("indicateur", "value='" + indicateur + "'");
            request.setAttribute("numTel", "value='" + numTel + "'");
            request.setAttribute("listeMultipleEntreprise", "");
            request.setAttribute("idContact", idContactString);
            try {
                this.getServletContext().getRequestDispatcher(Pages.MODIFIER_CONTACT.serveur()).forward(request, response);
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

            String idContactString = request.getParameter("idContact");
            int idContact = 0;
            if (idContactString != null)
                idContact = Integer.parseInt(idContactString);
            String nomContact = request.getParameter("nom");
            nomContact = StringCleaner.cleaner(nomContact, 40);
            String prenomContact = request.getParameter("prenom");
            prenomContact = StringCleaner.cleaner(prenomContact, 40);

            String dateNaissanceContactCheck = request.getParameter("dateDeNaissance");
            String dateNaissanceContact = dateNaissanceContactCheck.replaceAll("/","-");
            if(!dateNaissanceContact.equals("")) {
                String[] arrOfStr = dateNaissanceContact.split("-");
                if (arrOfStr.length == 3){
                    dateNaissanceContact = arrOfStr[2] + "-" + arrOfStr[1] + "-" + arrOfStr[0];
                }
            }

            Date dateNaissance = null;
            try{
                dateNaissance = Date.valueOf(dateNaissanceContact);
            } catch(Exception e){

            }

            String statut = request.getParameter("statut");
            statut = StringCleaner.cleaner(statut, 100);
            String email = request.getParameter("email");
            email = StringCleaner.cleaner(email, 80);
            String numTelephone = "";
            String numeroTelephone = request.getParameter("numTelephone");
            String indicateur = request.getParameter("indicateur");
            if (!numeroTelephone.equals("") && indicateur.equals("")) {
                numTelephone = numeroTelephone;
            }else if (!numeroTelephone.equals("") && !indicateur.equals("")){
                numeroTelephone = numeroTelephone.replaceAll(" ", "");
                numeroTelephone = numeroTelephone.replaceAll("\\.", "");

                if (indicateur.charAt(0) != '+') {
                    indicateur = "+" + indicateur;
                }
                indicateur = indicateur.replaceAll(" ", "");

                numTelephone = indicateur + " " + numeroTelephone;
            }

            String entreprise = request.getParameter("entreprise");
            entreprise = StringCleaner.cleaner(entreprise, 40);

            ArrayList<Entreprise> listeEntreprisePourUnNom = new EntrepriseDAO().recupEntrepriseParNom(entreprise);
            if (listeEntreprisePourUnNom == null) {
                boolean check = new ContactDAO().modifierContact(email, nomContact, prenomContact, dateNaissance, numTelephone, "", (double)-1, idContact);
                if (check){
                    new LogDAO().log(LogType.MODIFICATION_CONTACT, "Modification d'un contact", (Utilisateur) session.getAttribute("user"));
                } else {
                    new LogDAO().log(LogType.MODIFICATION_CONTACT, "Échec de la modification d'un contact", (Utilisateur) session.getAttribute("user"));
                }

                request.setAttribute("idContact", idContactString);
                RequestDispatcher rd = request.getRequestDispatcher(Pages.CONTACT.client());
                try {
                    rd.forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (listeEntreprisePourUnNom.size() == 1){
                boolean check = new ContactDAO().modifierContact(email, nomContact, prenomContact, dateNaissance, numTelephone, statut, listeEntreprisePourUnNom.get(0).getIdentifiant(), idContact);
                if (check){
                    new LogDAO().log(LogType.MODIFICATION_CONTACT, "Modification d'un contact", (Utilisateur) session.getAttribute("user"));
                } else {
                    new LogDAO().log(LogType.MODIFICATION_CONTACT, "Échec de la modification d'un contact", (Utilisateur) session.getAttribute("user"));
                }

                request.setAttribute("idContact", idContactString);
                RequestDispatcher rd = request.getRequestDispatcher(Pages.CONTACT.client());
                try {
                    rd.forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (listeEntreprisePourUnNom.size() > 1){
                String radio = request.getParameter("SelectEntrepriseRadio");
                if(radio == null) {
                    String multipleEntrprise = "" +
                            "    <div class='form-row' style='display: initial'>\n" +
                            "       <label>Il existe plusieurs entreprise avec ce nom, veuillez choisir l'entreprise voulue ci-dessous</label>\n";
                    for (int i = 0; i < listeEntreprisePourUnNom.size(); i++) {
                        multipleEntrprise +=
                                "       <div class='form-row'>\n" +
                                        "            <div class='form-group col-md-4'>\n" +
                                        "                <label>" + StringCleaner.cleaner(listeEntreprisePourUnNom.get(i).getNom(), 40) + "</label>\n" +
                                        "             </div>\n" +
                                        "             <div class='form-group col-md-3'>\n" +
                                        "                 <label>" + StringCleaner.cleaner(listeEntreprisePourUnNom.get(i).getDomaine(), 40) + "</label>\n" +
                                        "             </div>\n" +
                                        "             <div class='form-group col-md-4'>\n" +
                                        "                 <label>" + StringCleaner.cleaner(listeEntreprisePourUnNom.get(i).getMailContact(), 80) + "</label>\n" +
                                        "             </div>\n" +
                                        "             <div class='form-group col-md-1'>\n" +
                                        "                 <input class='form-check-input' type='radio' name='SelectEntrepriseRadio' id='Entreprise"+i+"' value='"+i+"'>\n" +
                                        "             </div>\n" +
                                        "        </div>\n";
                    }
                    multipleEntrprise += "    </div>\n";

                    request.setAttribute("listeMultipleEntreprise", multipleEntrprise);
                    request.setAttribute("nom", "value='" + nomContact + "'");
                    request.setAttribute("prenom", "value='" + prenomContact + "'");
                    request.setAttribute("dateDeNaissance", "value='" + dateNaissanceContactCheck + "'");
                    request.setAttribute("nomEntreprise", "value='" + entreprise + "'");
                    request.setAttribute("statut", "value='" + statut + "'");
                    request.setAttribute("mail", "value='" + email + "'");
                    request.setAttribute("indicateur", "value='" + indicateur + "'");
                    request.setAttribute("numTel", "value='" + numeroTelephone + "'");


                    request.setAttribute("idContact", idContactString);
                    try {
                        this.getServletContext().getRequestDispatcher(Pages.MODIFIER_CONTACT.serveur()).forward(request, response);
                    } catch (IOException | ServletException e) {
                        e.printStackTrace();
                    }

                } else{


                    boolean check = new ContactDAO().modifierContact(email, nomContact, prenomContact, dateNaissance, numTelephone, statut, listeEntreprisePourUnNom.get(Integer.parseInt(radio)).getIdentifiant(), idContact);

                    if (check){
                        new LogDAO().log(LogType.MODIFICATION_CONTACT, "Modification d'un contact", (Utilisateur) session.getAttribute("user"));
                    } else {
                        new LogDAO().log(LogType.MODIFICATION_CONTACT, "Échec de la modification d'un contact", (Utilisateur) session.getAttribute("user"));
                    }

                    request.setAttribute("idContact", idContactString);
                    RequestDispatcher rd = request.getRequestDispatcher(Pages.CONTACT.client());
                    try {
                        rd.forward(request, response);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        this.getServletContext().getRequestDispatcher(Pages.MODIFIER_CONTACT.serveur()).forward(request, response);
                    } catch (IOException | ServletException e) {
                        e.printStackTrace();
                    }
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
}
