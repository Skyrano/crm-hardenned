package control;

import donnees.Commentaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireDAO extends DOAabs {
    private Connection connexion;
    private PreparedStatement recupCommentaireParEntreprise, recupCommentaireParContact, supprimerCommentaireFromId, ajouterCommentaireContact,
            ajouterCommentaireEntreprise, ajouterCommentaireEvenement, recupCommentairesParEvenement;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupCommentaireParEntreprise = connexion.prepareStatement("select * from commentaire where idEntreprise=?");
            recupCommentaireParContact = connexion.prepareStatement("select * from commentaire where contactId=?");
            supprimerCommentaireFromId = connexion.prepareStatement("delete from commentaire where (id = ?);");
            supprimerCommentaireFromId = connexion.prepareStatement("delete from commentaire where id=?");
            ajouterCommentaireContact = connexion.prepareStatement("insert into commentaire(date, intitule, commentaire, contactId) values (?, ?, ?, ?)");
            ajouterCommentaireEntreprise = connexion.prepareStatement("insert into commentaire(date, intitule, commentaire, idEntreprise) values (?, ?, ?, ?)");
            ajouterCommentaireEvenement = connexion.prepareStatement("insert into commentaire(date, intitule, commentaire, nomEvenement, dateEvenement) values (?, ?, ?, ?, ?)");
            recupCommentairesParEvenement = connexion.prepareStatement("select * from commentaire where nomEvenement=? and dateEvenement=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Commentaire> recupererCommentairesParContact(int idClient) {
        try {
            recupCommentaireParContact.setInt(1, idClient);
            ResultSet result = recupCommentaireParContact.executeQuery();
            List<Commentaire> commentaires = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                Date date = result.getDate("date");
                String intitule = result.getString("intitule");
                String commentaire = result.getString("commentaire");
                commentaires.add(new Commentaire(id, date, intitule, commentaire));
            }
            result.close();
            recupCommentaireParContact.close();
            connexion.close();
            return commentaires;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Commentaire> recupererCommentaireParEntreprise(double idEntreprise) {
        try {
            recupCommentaireParEntreprise.setDouble(1, idEntreprise);
            ResultSet result = recupCommentaireParEntreprise.executeQuery();
            List<Commentaire> commentaires = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                Date date = result.getDate("date");
                String intitule = result.getString("intitule");
                String commentaire = result.getString("commentaire");
                commentaires.add(new Commentaire(id, date, intitule, commentaire));
            }
            result.close();
            recupCommentaireParEntreprise.close();
            connexion.close();
            return commentaires;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ajouterCommentaireContact(String intitule, String commentaire, int contactId){
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            ajouterCommentaireContact.setDate(1, date);
            ajouterCommentaireContact.setString(2, intitule);
            ajouterCommentaireContact.setString(3, commentaire);
            ajouterCommentaireContact.setInt(4, contactId);
            ajouterCommentaireContact.executeUpdate();
            ajouterCommentaireContact.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean supprimerCommentaireFromId(int idCommentaire){
        try {
            supprimerCommentaireFromId.setInt(1, idCommentaire);
            supprimerCommentaireFromId.executeUpdate();
            supprimerCommentaireFromId.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }




    public void ajouterCommentaireEntreprise(String intitule, String commentaire, double entrepriseId){
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            ajouterCommentaireEntreprise.setDate(1, date);
            ajouterCommentaireEntreprise.setString(2, intitule);
            ajouterCommentaireEntreprise.setString(3, commentaire);
            ajouterCommentaireEntreprise.setDouble(4, entrepriseId);
            ajouterCommentaireEntreprise.executeUpdate();
            ajouterCommentaireEntreprise.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterCommentaireEvenement(String intitule, String commentaire, String nomEvenement, String dateEvenement){
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            ajouterCommentaireEvenement.setDate(1, date);
            ajouterCommentaireEvenement.setString(2, intitule);
            ajouterCommentaireEvenement.setString(3, commentaire);
            ajouterCommentaireEvenement.setString(4, nomEvenement);
            ajouterCommentaireEvenement.setDate(5, java.sql.Date.valueOf(dateEvenement));
            ajouterCommentaireEvenement.executeUpdate();
            ajouterCommentaireEvenement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Commentaire> recupererCommentairesParEvenement(String nom, Date date) {
        try {
            recupCommentairesParEvenement.setString(1, nom);
            recupCommentairesParEvenement.setDate(2, (java.sql.Date) date);
            ResultSet result = recupCommentairesParEvenement.executeQuery();
            List<Commentaire> commentaires = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                Date dateCom = result.getDate("date");
                String intitule = result.getString("intitule");
                String commentaire = result.getString("commentaire");
                commentaires.add(new Commentaire(id, dateCom, intitule, commentaire));
            }
            result.close();
            recupCommentairesParEvenement.close();
            connexion.close();
            return commentaires;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
