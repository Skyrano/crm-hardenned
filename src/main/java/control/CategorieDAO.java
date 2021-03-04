package control;

import donnees.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO extends DOAabs {
    private Connection connexion;

    private PreparedStatement recupCategorieParEntreprise, recupCategories, recupCategoriesDesEntreprises, recupCategoriesDesContacts,
            recupCategorieParContact, supprimerRelationCategorieEntreprise, ajouterCategorie, supprimerCategorie, ajouterAppartientA,
            ajouterRepresente, supprimerAppartientA, supprimerRepresente;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupCategorieParEntreprise = connexion.prepareStatement("select * from represente where idEntreprise=? order by categorieNom");
            recupCategories = connexion.prepareStatement("select * from categorie order by nom");
            recupCategoriesDesEntreprises = connexion.prepareStatement("select categorieNom from represente group by categorieNom");
            recupCategoriesDesContacts = connexion.prepareStatement("select categorieNom from appartienta group by categorieNom");
            recupCategorieParContact = connexion.prepareStatement("select * from appartientA where contactId=? order by categorieNom");
            supprimerRelationCategorieEntreprise = connexion.prepareStatement("delete from represente where (categorieNom = ?) and (idEntreprise = ?)");
            ajouterCategorie = connexion.prepareStatement("insert into categorie(nom) values (?)");
            supprimerCategorie = connexion.prepareStatement("delete from categorie where nom=?");
            ajouterAppartientA = connexion.prepareStatement("insert into appartienta(contactId, categorieNom) values (?, ?)");
            ajouterRepresente = connexion.prepareStatement("insert into represente(idEntreprise, categorieNom) values (?, ?)");
            supprimerAppartientA = connexion.prepareStatement("delete from appartienta where contactId=? AND categorieNom=?");
            supprimerRepresente = connexion.prepareStatement("delete from represente where idEntreprise=? AND categorieNom=?");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Categorie> recupererCategories() {
        try {
            ResultSet result = recupCategories.executeQuery();
            List<Categorie> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(new Categorie(result.getString("nom")));
            }
            result.close();
            recupCategories.close();
            connexion.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Categorie> recupererCategorieParContact(int idClient) {
        try {
            recupCategorieParContact.setInt(1, idClient);
            ResultSet result = recupCategorieParContact.executeQuery();
            List<Categorie> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(new Categorie(result.getString("categorieNom")));
            }
            result.close();
            recupCategorieParContact.close();
            connexion.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public List<Categorie> recupererCategoriesParEntreprise(double idEntreprise) {
        try {
            recupCategorieParEntreprise.setDouble(1, idEntreprise);
            ResultSet result = recupCategorieParEntreprise.executeQuery();
            List<Categorie> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(new Categorie(result.getString("categorieNom")));
            }
            result.close();
            recupCategorieParEntreprise.close();
            connexion.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void ajouterCategorie(String nom) {
        try {
            ajouterCategorie.setString(1, nom);
            ajouterCategorie.executeUpdate();
            ajouterCategorie.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void supprimerCategorie(String nom){
        try {
            supprimerCategorie.setString(1, nom);
            supprimerCategorie.executeUpdate();
            supprimerCategorie.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterAppartientA(int contactId, String categorieNom){
        try {
            ajouterAppartientA.setInt(1, contactId);
            ajouterAppartientA.setString(2, categorieNom);
            ajouterAppartientA.executeUpdate();
            ajouterAppartientA.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterRepresente(double idEntreprise, String categorieNom){
        try {
            ajouterRepresente.setDouble(1, idEntreprise);
            ajouterRepresente.setString(2, categorieNom);
            ajouterRepresente.executeUpdate();
            ajouterRepresente.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerAppartientA(int contactId, String categorieNom){
        try {
            supprimerAppartientA.setInt(1, contactId);
            supprimerAppartientA.setString(2, categorieNom);
            supprimerAppartientA.executeUpdate();
            supprimerAppartientA.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerRepresente(double idEntreprise, String categorieNom){
        try {
            supprimerRepresente.setDouble(1, idEntreprise);
            supprimerRepresente.setString(2, categorieNom);
            supprimerRepresente.executeUpdate();
            supprimerRepresente.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> recupNomCategoriesDesEntreprises() {
        try {
            ResultSet result = recupCategoriesDesEntreprises.executeQuery();
            List<String> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(result.getString("categorieNom"));
            }
            result.close();
            recupCategoriesDesEntreprises.close();
            connexion.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> recupNomCategoriesDesContacts() {
        try {
            ResultSet result = recupCategoriesDesContacts.executeQuery();
            List<String> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(result.getString("categorieNom"));
            }
            result.close();
            recupCategoriesDesContacts.close();
            connexion.close();
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean supprimerRelationCategorieEntreprise(double idEntreprise, String nomCategorie){
        try {
            supprimerRelationCategorieEntreprise.setString(1, nomCategorie);
            supprimerRelationCategorieEntreprise.setDouble(2, idEntreprise);
            supprimerRelationCategorieEntreprise.executeUpdate();
            supprimerRelationCategorieEntreprise.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
