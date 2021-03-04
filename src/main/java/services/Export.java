package services;

import control.*;
import donnees.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

@WebServlet("/export")
public class Export extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur =(Utilisateur) session.getAttribute("user");
        if (utilisateur!= null && (utilisateur.getRole() != null && utilisateur.getRole().getNom().equals("admin")) ) {
            File export = new File("contacts.csv");
            export.createNewFile();
            PrintWriter writer = new PrintWriter(export);
            StringBuilder contact = new StringBuilder();
            contact.append("Identifiant;");
            contact.append("Mail;");
            contact.append("Nom;");
            contact.append("Prenom;");
            contact.append("Date de naissance;");
            contact.append("Numéro de téléphone;");
            contact.append("Statut;");
            contact.append("ID Entreprise");
            contact.append("Categories");
            contact.append("Commentaires");
            writer.println(contact.toString());
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(20);
            for (Contact cont : new ContactDAO().listeContacts()) {
                contact = new StringBuilder();
                contact.append(cont.getIdentifiant() + ";");
                contact.append(cont.getMail() + ";");
                contact.append(cont.getNom() + ";");
                contact.append(cont.getPrenom()).append(";");
                contact.append(cont.getDateNaissance() + ";");
                contact.append(cont.getNumTel() + ";");
                contact.append(cont.getStatut() + ";");
                if (cont.getEntreprise() != null)
                    contact.append(df.format(cont.getEntreprise().getIdentifiant()));
                List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(cont.getIdentifiant());
                String cat = "";
                for (int i = 0; i < categories.size(); ++i) {
                    cat += categories.get(i).getNom();
                    if (i != categories.size() - 1)
                        cat += ",";
                }
                contact.append(cat + ";");
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(cont.getIdentifiant());
                String comm = "";
                for (int i = 0; i < categories.size(); ++i) {
                    comm += categories.get(i).getNom();
                    if (i != categories.size() - 1)
                        comm += ",";
                }
                contact.append(comm + ";");
                writer.println(contact.toString());
            }
            writer.close();

            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(export.getAbsolutePath());
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) export.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", export.getName());
            response.setHeader(headerKey, headerValue);

            FileInputStream inputStream = new FileInputStream(export);
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            new LogDAO().log(LogType.EXPORT_CSV, "export de la base de données",utilisateur);
            writer.close();
            outStream.close();
            this.getServletContext().getRequestDispatcher(Pages.ADMIN.serveur()).forward(request, response);
        }
        else
            this.getServletContext().getRequestDispatcher(Pages.LOGIN.serveur()).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doGet(request, response);
    }
}
