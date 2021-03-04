package control;

import donnees.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDAO extends DOAabs {

    private Connection connexion;
    private PreparedStatement recupEntrepriseMoins10, recupEntrepriseMoins50, recupEntrepriseMoins150, recupEntrepriseMoins500,
            recupEntreprisePlus500, recupEntreprise, checkEntrepriseExiste, recupAllIdEntreprise, recupEntreprises,
            recupEntreprisesParNom, ajouterEntreprise, modifierEntreprise, recupFiliales;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupEntrepriseMoins10 = connexion.prepareStatement("select count(*) from entreprise where nbEmployes < '10'");
            recupEntrepriseMoins50 = connexion.prepareStatement("select count(*) from entreprise where nbEmployes < '50' and nbEmployes > '10'");
            recupEntrepriseMoins150 = connexion.prepareStatement("select count(*) from entreprise where nbEmployes < '150' and nbEmployes > '50'");
            recupEntrepriseMoins500 = connexion.prepareStatement("select count(*) from entreprise where nbEmployes < '500' and nbEmployes > '150'");
            recupEntreprisePlus500 = connexion.prepareStatement("select count(*) from entreprise where nbEmployes > '500'");
            recupEntreprise = connexion.prepareStatement("select * from entreprise where id=?");
            checkEntrepriseExiste = connexion.prepareStatement("select * from entreprise where nom=? AND domaine=? AND nbEmployes=? AND idAdresse=? AND numSiret=? AND mailContact=?");
            recupAllIdEntreprise = connexion.prepareStatement("select id from entreprise");
            recupEntreprises = connexion.prepareStatement("select * from entreprise order by nom");
            recupEntreprisesParNom = connexion.prepareStatement("select * from entreprise where nom=?");
            modifierEntreprise = connexion.prepareStatement("update entreprise SET `nom` = ?, `domaine` = ?, `nbEmployes` = ?, `idAdresse` = ?, `numSiret` = ?, `mailContact` = ? WHERE (`id` = ?)");
            ajouterEntreprise = connexion.prepareStatement("insert into entreprise(id, nom, domaine, nbEmployes, idAdresse, numSiret, mailContact) values (?, ?, ?, ?, ?, ?, ?)");
            recupFiliales = connexion.prepareStatement("select * from entreprise where numSiret=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> recupTaillEntreprise() {
        ArrayList<Integer> listTaille = new ArrayList<>();
        try {
            ResultSet result = recupEntrepriseMoins10.executeQuery();
            result.next();
            listTaille.add(result.getInt(1));
            result = recupEntrepriseMoins50.executeQuery();
            result.next();
            listTaille.add(result.getInt(1));
            result = recupEntrepriseMoins150.executeQuery();
            result.next();
            listTaille.add(result.getInt(1));
            result = recupEntrepriseMoins500.executeQuery();
            result.next();
            listTaille.add(result.getInt(1));
            result = recupEntreprisePlus500.executeQuery();
            result.next();
            listTaille.add(result.getInt(1));

            result.close();
            recupEntrepriseMoins10.close();
            recupEntrepriseMoins50.close();
            recupEntrepriseMoins150.close();
            recupEntrepriseMoins500.close();
            recupEntreprisePlus500.close();
            connexion.close();

            return listTaille;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            listTaille.add(0);
            listTaille.add(0);
            listTaille.add(0);
            listTaille.add(0);
            listTaille.add(0);
            return listTaille;
        }
    }


    public List<Entreprise> recupererEntrepriseParCritere(String nom, String domaine, Double numSiret, String categorie, String pays, Integer codePostal, String nomGroupe) {
        ArrayList<Entreprise> entreprises = null;
        try {
            int i = 1;
            String requete = "select distinct id from entreprise e left join represente r on e.id = r.idEntreprise where ";
            if (!"".equals(nom)) {
                requete += "e.nom like ?";
                i++;
            }
            if (!"".equals(domaine)) {
                if (i > 1)
                    requete += " and ";
                requete += "e.domaine like ?";
                i++;
            }
            if (numSiret != null) {
                if (i > 1)
                    requete += " and ";
                requete += "e.numSiret = ?";
                i++;
            }
            if (!"".equals(categorie)) {
                if (i > 1)
                    requete += " and ";
                requete += "r.categorieNom like ?";
                i++;
            }
            if (!"".equals(nomGroupe)) {
                if (i > 1)
                    requete += " and ";
                requete += "e.numSiret in (select numSiret from groupe where nom like ?)";
                i++;
            }
            if (!"".equals(pays)) {
                if (i > 1)
                    requete += " and ";
                requete += "e.idAdresse in (select id from adresse where pays like ?)";
                i++;
            }
            if (codePostal != null) {
                if (i > 1)
                    requete += " and ";
                requete += "e.idAdresse in (select id from adresse where codePostal like ?)";
                i++;
            }

            PreparedStatement recupererEntrepriseParCritere = connexion.prepareStatement(requete);
            i = 1;
            if (!"".equals(nom)) {
                recupererEntrepriseParCritere.setString(i, "%" + nom + "%");
                i++;
            }
            if (!"".equals(domaine)) {
                recupererEntrepriseParCritere.setString(i, "%" + domaine + "%");
                i++;
            }
            if (numSiret != null) {
                recupererEntrepriseParCritere.setDouble(i, numSiret);
                i++;
            }
            if (!"".equals(categorie)) {
                recupererEntrepriseParCritere.setString(i, "%" + categorie + "%");
                i++;
            }
            if (!"".equals(pays)) {
                recupererEntrepriseParCritere.setString(i, "%" + pays + "%");
                i++;
            }
            if (codePostal != null) {
                recupererEntrepriseParCritere.setString(i, "%" + codePostal + "%");
                i++;
            }
            if (!"".equals(nomGroupe)) {
                recupererEntrepriseParCritere.setString(i, "%" + nomGroupe + "%");
                i++;
            }
            ResultSet result = recupererEntrepriseParCritere.executeQuery();
            entreprises = new ArrayList<>();
            ArrayList<Double> entrepriseId = new ArrayList<>();
            while (result.next()) {
                entrepriseId.add(result.getDouble("id"));
            }
            result.close();
            
            for (Double id:entrepriseId){
                entreprises.add(this.recupererEntreprisePrivate(id));
            }
            recupererEntrepriseParCritere.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entreprises;
    }

    private Entreprise recupererEntreprisePrivate(double identifiant) {
        Entreprise entreprise = new Entreprise(identifiant);
        try {
            recupEntreprise.setDouble(1, identifiant);
            ResultSet result = recupEntreprise.executeQuery();
            result.next();
            int nbEmployes = result.getInt("nbEmployes");
            String nom = result.getString("nom");
            String domaine = result.getString("domaine");
            String mailContact = result.getString("mailContact");
            int idAdresse = result.getInt("idAdresse");
            double nSiret = result.getDouble("numSiret");
            result.close();
            Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
            List<Contact> contacts = new ContactDAO().recupererContactParEntreprise(identifiant);
            Groupe groupe = new GroupeDAO().recupererGroupe(nSiret, entreprise);
            List<Categorie> categories = new CategorieDAO().recupererCategoriesParEntreprise(identifiant);
            List<Commentaire> commentaires = new CommentaireDAO().recupererCommentaireParEntreprise(identifiant);
            entreprise.setEntreprise(nbEmployes, nom, domaine
                    , mailContact, adresse, contacts
                    , groupe, categories, commentaires);

            return entreprise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Entreprise recupEntreprise(double id) {
        try {
            recupEntreprise.setDouble(1, id);
            ResultSet result = recupEntreprise.executeQuery();
            result.next();
            if (result.isFirst()) {
                String nom = result.getString("nom");
                String domaine = result.getString("domaine");
                int nbEmployes = result.getInt("nbEMployes");
                int idAdresse = result.getInt("idAdresse");
                double numSiret = result.getDouble("numSiret");
                String mailContact = result.getString("mailContact");
                result.close();
                recupEntreprise.close();
                connexion.close();
                return new Entreprise(id, nbEmployes, nom, domaine, mailContact, new AdresseDAO().recupererAdresse(idAdresse),
                        new ContactDAO().recupererContactParEntreprise(id), new GroupeDAO().recupererGroupe(numSiret),
                        new CategorieDAO().recupererCategoriesParEntreprise(id), new CommentaireDAO().recupererCommentaireParEntreprise(id));
            } else {
                result.close();
                recupEntreprise.close();
                connexion.close();
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    public ArrayList<Entreprise> recupEntrepriseParNom(String nom) {
        ArrayList<Entreprise> listeEntreprise = new ArrayList<>();
        try {
            recupEntreprisesParNom.setString(1, nom);
            ResultSet result = recupEntreprisesParNom.executeQuery();
            while (result.next()) {
                Double identifiant = result.getDouble("id");

                Entreprise entrepriseBuffer = new Entreprise(identifiant);

                int nbEmployes = result.getInt("nbEmployes");
                String domaine = result.getString("domaine");
                String mailContact = result.getString("mailContact");
                int idAdresse = result.getInt("idAdresse");
                double numSiret = result.getDouble("numSiret");
                Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
                List<Contact> contacts = new ContactDAO().recupererContactParEntreprise(identifiant);
                Groupe groupe = new GroupeDAO().recupererGroupe(numSiret, entrepriseBuffer);
                List<Categorie> categories = new CategorieDAO().recupererCategoriesParEntreprise(identifiant);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentaireParEntreprise(identifiant);
                entrepriseBuffer.setEntreprise(nbEmployes, nom, domaine, mailContact, adresse, contacts, groupe, categories, commentaires);
                listeEntreprise.add(entrepriseBuffer);
                result.next();
            }
            result.close();
            recupEntreprisesParNom.close();
            connexion.close();
            return listeEntreprise;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public ArrayList<Double> recupAllIdEntreprise() {
        ArrayList<Double> allId = new ArrayList<>();
        try {
            ResultSet result = recupAllIdEntreprise.executeQuery();
            result.next();
            while (!result.isAfterLast()) {
                allId.add(result.getDouble("id"));
                result.next();
            }
            result.close();
            recupAllIdEntreprise.close();
            connexion.close();
            return allId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }


    public boolean creationEntreprise(double id, String nom, String domaine, int nbEmployes, int idAdresse, Double numSiret, String mailContact) {
        try {
            checkEntrepriseExiste.setString(1, nom);
            checkEntrepriseExiste.setString(2, domaine);
            checkEntrepriseExiste.setInt(3, nbEmployes);
            checkEntrepriseExiste.setInt(4, idAdresse);
            checkEntrepriseExiste.setDouble(5, numSiret);
            checkEntrepriseExiste.setString(6, mailContact);
            ResultSet result = checkEntrepriseExiste.executeQuery();
            result.next();
            if (!result.isFirst()) {
                ajouterEntreprise.setDouble(1, id);
                ajouterEntreprise.setString(2, nom);
                ajouterEntreprise.setString(3, domaine);
                ajouterEntreprise.setInt(4, nbEmployes);
                ajouterEntreprise.setInt(5, idAdresse);
                ajouterEntreprise.setDouble(6, numSiret);
                ajouterEntreprise.setString(7, mailContact);
                ajouterEntreprise.executeUpdate();
            }
            result.close();
            checkEntrepriseExiste.close();
            ajouterEntreprise.close();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierEntreprise(String nom, String domaine, int nbEmployes, int idAdresse, Double numSiret, String mailContact, double idEntreprise) {
        try {
            modifierEntreprise.setString(1, nom);
            modifierEntreprise.setString(2, domaine);
            modifierEntreprise.setInt(3, nbEmployes);
            modifierEntreprise.setInt(4, idAdresse);
            modifierEntreprise.setDouble(5, numSiret);
            modifierEntreprise.setString(6, mailContact);
            modifierEntreprise.setDouble(7, idEntreprise);
            modifierEntreprise.executeUpdate();
            modifierEntreprise.close();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Entreprise> listeEntreprises() {
        ArrayList<Entreprise> res = new ArrayList<>();
        try {
            ResultSet result = recupEntreprises.executeQuery();
            while (result.next()) {
                double identifiant = result.getDouble("id");
                Entreprise entreprise = new Entreprise(identifiant);
                int nbEmployes = result.getInt("nbEmployes");
                String nom = result.getString("nom");
                String domaine = result.getString("domaine");
                String mailContact = result.getString("mailContact");
                int idAdresse = result.getInt("idAdresse");
                int nSiret = result.getInt("numSiret");

                Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
                List<Contact> contacts = new ContactDAO().recupererContactParEntreprise(identifiant);
                Groupe groupe = new GroupeDAO().recupererGroupe(nSiret, entreprise);
                List<Categorie> categories = new CategorieDAO().recupererCategoriesParEntreprise(identifiant);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentaireParEntreprise(identifiant);
                entreprise.setEntreprise(nbEmployes, nom, domaine
                        , mailContact, adresse, contacts
                        , groupe, categories, commentaires);
                res.add(entreprise);
            }
            result.close();
            recupEntreprises.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public Entreprise recupererEntreprise(double identifiant) {
        Entreprise entreprise = new Entreprise(identifiant);
        try {
            recupEntreprise.setDouble(1, identifiant);
            ResultSet result = recupEntreprise.executeQuery();
            result.next();
            int nbEmployes = result.getInt("nbEmployes");
            String nom = result.getString("nom");
            String domaine = result.getString("domaine");
            String mailContact = result.getString("mailContact");
            int idAdresse = result.getInt("idAdresse");
            double nSiret = result.getDouble("numSiret");
            result.close();
            recupEntreprise.close();
            connexion.close();
            Adresse adresse = new AdresseDAO().recupererAdresse(idAdresse);
            List<Contact> contacts = new ContactDAO().recupererContactParEntreprise(identifiant);
            Groupe groupe = new GroupeDAO().recupererGroupe(nSiret, entreprise);
            List<Categorie> categories = new CategorieDAO().recupererCategoriesParEntreprise(identifiant);
            List<Commentaire> commentaires = new CommentaireDAO().recupererCommentaireParEntreprise(identifiant);
            entreprise.setEntreprise(nbEmployes, nom, domaine
                    , mailContact, adresse, contacts
                    , groupe, categories, commentaires);

            return entreprise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entreprise> recupererFilialesGroupe(Groupe groupe, Entreprise entreprise) {
        try {
            recupFiliales.setDouble(1, groupe.getNumSiret());
            ResultSet result = recupFiliales.executeQuery();
            List<Entreprise> filiales = new ArrayList<>();
            while (result.next()) {
                if (entreprise != null && result.getDouble("id") == entreprise.getIdentifiant()) {
                    filiales.add(entreprise);
                } else {
                    double identifiant = result.getDouble("id");
                    int nbEmployes = result.getInt("nbEmployes");
                    String nom = result.getString("nom");
                    String domaine = result.getString("domaine");
                    String mailContact = result.getString("mailContact");
                    Adresse adresse = new AdresseDAO().recupererAdresse(result.getInt("idAdresse"));
                    List<Contact> contacts = new ContactDAO().recupererContactParEntreprise(identifiant);
                    List<Categorie> categories = new CategorieDAO().recupererCategoriesParEntreprise(identifiant);
                    List<Commentaire> commentaires = new CommentaireDAO().recupererCommentaireParEntreprise(identifiant);
                    filiales.add(new Entreprise(identifiant, nbEmployes, nom, domaine
                            , mailContact, adresse, contacts
                            , groupe, categories, commentaires));
                }
            }
            result.close();
            recupFiliales.close();
            connexion.close();
            return filiales;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}