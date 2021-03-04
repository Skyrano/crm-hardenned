package control;

import donnees.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO extends DOAabs {
    private Connection connexion;
    private PreparedStatement recupEvenements, recupEvenement, recupUtilisateursParEvenement, recupProchainsEvenementsENSIBS,
            recupProchainsEvenementsUtilisateur, ajouterEvenement, modifierEvenement, supprimerUtilisateurDunEvenement,
            recuputilisateurPasPresentAEvenement, supprEstPresent, ajouterUtilisateurEvenement, ajouterContactEvenement;
    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupEvenements = connexion.prepareStatement("select * from evenement order by date");
          recupEvenement = connexion.prepareStatement("select * from evenement where nom=? and date=?");
            recupProchainsEvenementsENSIBS = connexion.prepareStatement("select * from evenement where date>=curdate() order by date limit 3");
            recupProchainsEvenementsUtilisateur = connexion.prepareStatement("select * from evenement as a, estpresenta b where b.utilisateurIdentifiant=? and a.nom=b.evenementNom and a.date=b.evenementDate and a.date>=curdate() order by a.date limit 3");
            ajouterUtilisateurEvenement = connexion.prepareStatement("insert into estpresenta(utilisateurIdentifiant, evenementNom, evenementDate) values (?, ?, ?)");
            ajouterContactEvenement = connexion.prepareStatement("insert into participea(contactId, evenementNom, evenementDate) values (?, ?, ?)");
            ajouterEvenement = connexion.prepareStatement("insert into evenement(nom, date, heure, type, description, idAdresse) values (?, ?, ?, ?, ? , ?)");
            modifierEvenement = connexion.prepareStatement("update evenement set heure=?, type=?, description=?, idAdresse=? where nom=? and date=?");
            supprimerUtilisateurDunEvenement = connexion.prepareStatement("delete from estpresenta where utilisateurIdentifiant=? and evenementNom=? and evenementDate=?");
           supprEstPresent = connexion.prepareStatement("delete from estpresenta where utilisateurIdentifiant=?");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public List<Evenement> listeEvenements() {
        ArrayList<Evenement> evenements = new ArrayList<>();
        try {
            ResultSet result = recupEvenements.executeQuery();

            while (result.next()) {
                String nom = result.getString("nom");
                Date date = result.getDate("date");
                Time heure = result.getTime("heure");
                String type = result.getString("type");
                String description = result.getString("description");
                int idAdresse = result.getInt("idAdresse");
                List<Utilisateur> utilisateursPresents = new AuthentificationManager().recupererUtilisateursParEvenement(nom, date);
                Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
                List<Contact> contactsPresents = new ContactDAO().recupererContactsParEvenement(nom, date);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParEvenement(nom, date);
                Evenement e = new Evenement(nom, type, description, date, heure, utilisateursPresents, adresse, contactsPresents, commentaires);
                evenements.add(e);
            }
            result.close();
            recupEvenements.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return evenements;
    }

    public boolean supprimerUtilisateurFromEvenement(String identifiant, String evenementNom, java.sql.Date dateEvenement){
        try {
            supprimerUtilisateurDunEvenement.setString(1, identifiant);
            supprimerUtilisateurDunEvenement.setString(2, evenementNom);
            supprimerUtilisateurDunEvenement.setDate(3, dateEvenement);
            supprimerUtilisateurDunEvenement.executeUpdate();
            supprimerUtilisateurDunEvenement.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Evenement recupererEvenement(String nom, Date date) {
        try {
            recupEvenement.setString(1, nom);
            recupEvenement.setDate(2, (java.sql.Date) date);
            ResultSet result = recupEvenement.executeQuery();

            result.next();
            String nomE = result.getString("nom");
            Date dateE = result.getDate("date");
            Time heure = result.getTime("heure");
            String type = result.getString("type");
            String description = result.getString("description");
            int idAdresse = result.getInt("idAdresse");
            List<Utilisateur> utilisateursPresents = new AuthentificationManager().recupererUtilisateursParEvenement(nom, date);
            Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
            List<Contact> contactsPresents = new ContactDAO().recupererContactsParEvenement(nom, date);
            List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParEvenement(nom, date);
            Evenement e = new Evenement(nomE, type, description, dateE, heure, utilisateursPresents, adresse, contactsPresents, commentaires);
            result.close();
            recupEvenement.close();
            connexion.close();
            return e;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Evenement> prochainsEvenementsENSIBS() {
        ArrayList<Evenement> evenements = new ArrayList<>();
        try {
            ResultSet result = recupProchainsEvenementsENSIBS.executeQuery();


            while (result.next()) {
                String nom = result.getString("nom");
                Date date = result.getDate("date");
                Time heure = result.getTime("heure");
                String type = result.getString("type");
                String description = result.getString("description");
                int idAdresse = result.getInt("idAdresse");
                List<Utilisateur> utilisateursPresents = new AuthentificationManager().recupererUtilisateursParEvenement(nom, date);
                Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
                List<Contact> contactsPresents = new ContactDAO().recupererContactsParEvenement(nom, date);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParEvenement(nom, date);
                Evenement e = new Evenement(nom, type, description, date, heure, utilisateursPresents, adresse, contactsPresents, commentaires);
                evenements.add(e);
            }
            result.close();
            recupProchainsEvenementsENSIBS.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return evenements;
    }

    public List<Evenement> prochainsEvenementsUtilisateur(String id) {
        ArrayList<Evenement> evenements = new ArrayList<>();
        try {
            recupProchainsEvenementsUtilisateur.setString(1, id);
            ResultSet result = recupProchainsEvenementsUtilisateur.executeQuery();

            while (result.next()) {
                String nom = result.getString("nom");
                Date date = result.getDate("date");
                Time heure = result.getTime("heure");
                String type = result.getString("type");
                String description = result.getString("description");
                int idAdresse = result.getInt("idAdresse");
                List<Utilisateur> utilisateursPresents = new AuthentificationManager().recupererUtilisateursParEvenement(nom, date);
                Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
                List<Contact> contactsPresents = new ContactDAO().recupererContactsParEvenement(nom, date);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParEvenement(nom, date);
                Evenement e = new Evenement(nom, type, description, date, heure, utilisateursPresents, adresse, contactsPresents, commentaires);
                evenements.add(e);
            }
            result.close();
            recupProchainsEvenementsUtilisateur.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return evenements;
    }



    public boolean creationEvenement(String nom, String date, String heure, String type, String description, int num, String rue, int codePostal, String ville, String pays) {
        int idAdresse;
        try {
            idAdresse = new AdresseDAO().checkAdressePourEvenement(num, rue, codePostal, ville, pays);

            ajouterEvenement.setString(1, nom);
            SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dateTransfo = f1.parse(date);
            ajouterEvenement.setDate(2, new java.sql.Date(dateTransfo.getTime()));
            String[] temps = heure.split(":");
            int heures = Integer.parseInt(temps[0]);
            int minutes = Integer.parseInt(temps[1]);
            ajouterEvenement.setTime(3, new Time(heures, minutes, 0));
            ajouterEvenement.setString(4, type);
            ajouterEvenement.setString(5, description);
            ajouterEvenement.setInt(6, idAdresse);
            ajouterEvenement.executeUpdate();

            ajouterEvenement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }


    public boolean modificationEvenement(String nom, String date, String heure, String type, String description, int num, String rue, int codePostal, String ville, String pays){
        int idAdresse;
        try {
            idAdresse = new AdresseDAO().checkAdressePourEvenement(num, rue, codePostal, ville, pays);

            String[] temps = heure.split(":");
            int heures = Integer.parseInt(temps[0]);
            int minutes = Integer.parseInt(temps[1]);
            modifierEvenement.setTime(1, new Time(heures,minutes,0));
            modifierEvenement.setString(2, type);
            modifierEvenement.setString(3, description);
            modifierEvenement.setInt(4, idAdresse);
            modifierEvenement.setString(5, nom);
            SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dateTransfo = f1.parse(date);
            modifierEvenement.setDate(6, new java.sql.Date(dateTransfo.getTime()));
            modifierEvenement.executeUpdate();

            modifierEvenement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public Boolean ajouterUtilisateurAEvenement(String utilisateurIdentifiant, String evenementNom, java.sql.Date evenementDate){
        try {
            ajouterUtilisateurEvenement.setString(1, utilisateurIdentifiant);
            ajouterUtilisateurEvenement.setString(2, evenementNom);
            ajouterUtilisateurEvenement.setDate(3, evenementDate);
            ajouterUtilisateurEvenement.executeUpdate();
            ajouterUtilisateurEvenement.close();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean ajouterContactAEvenement(String contactId, String evenementNom, java.sql.Date evenementDate){
        try {
            ajouterContactEvenement.setString(1, contactId);
            ajouterContactEvenement.setString(2, evenementNom);
            ajouterContactEvenement.setDate(3, evenementDate);
            ajouterContactEvenement.executeUpdate();
            ajouterContactEvenement.close();
            connexion.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
