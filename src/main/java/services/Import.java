package services;

import control.ContactDAO;
import control.EntrepriseDAO;
import control.LogDAO;
import control.LogType;
import donnees.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

@WebServlet("/import")
@MultipartConfig
public class Import extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {
            for (Part fichier : request.getParts()) {
                String nomFichier = getFileName(fichier);
                boolean estAjoute = false;
                String extension = nomFichier.split("\\.")[1];
                if ("csv".equals(extension)){
                    InputStream inputFichier = fichier.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputFichier));
                    String ligne = reader.readLine();
                    estAjoute = true;
                    while (ligne != null) {
                        String[] ligneCSV = ligne.split(";");
                        if (!ligneCSV[0].equals("Identifiant")) {
                            int identifiant = Integer.valueOf(ligneCSV[0]);
                            String test = ligneCSV[4];
                            try {
                                java.sql.Date naissanceSQL = java.sql.Date.valueOf(ligneCSV[4]);
                                Entreprise entreprise = null;
                                if (ligneCSV.length == 8 && ligneCSV[7] != null) {
                                    entreprise = new EntrepriseDAO().recupererEntreprise(Double.valueOf(ligneCSV[7]));
                                }
                                Contact contact = new Contact(identifiant, ligneCSV[1], ligneCSV[2], ligneCSV[3], naissanceSQL,
                                        ligneCSV[5], ligneCSV[6], new ArrayList<Categorie>(), entreprise, new ArrayList<Commentaire>());
                                if (!new ContactDAO().ajouterContact(contact))
                                    estAjoute = false;
                            }catch(IllegalArgumentException e){
                                request.setAttribute("erreurFormat", true);
                                break;
                            }
                        }
                        ligne = reader.readLine();
                    }
                }
                if(estAjoute)
                    new LogDAO().log(LogType.IMPORT_CSV, "import d'un fichier csv", utilisateur);
                request.getSession().setAttribute("dbAjout", estAjoute);
            }
            this.getServletContext().getRequestDispatcher(Pages.ADMIN.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
    }

    private String getFileName(final Part pPart) {
        for (String lContent : pPart.getHeader("content-disposition")
                .split(";")) {
            if (lContent.trim().startsWith("filename")) {
                return lContent.substring(lContent.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getExtension(final Part pPart) {
        for (String lContent : pPart.getHeader("content-disposition")
                .split(";")) {
            if (lContent.trim().startsWith("filename")) {
                return lContent.substring(lContent.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
