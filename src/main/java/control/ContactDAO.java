package control;

import donnees.Categorie;
import donnees.Commentaire;
import donnees.Contact;
import donnees.Entreprise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends DOAabs {
    private Connection connexion;
    private PreparedStatement recupTousContacts, recupContacts, recupContactparId, recupContactParEntreprise, recupNbContactSansEntreprise,
            ajouterContactSansId, recupContactsParEvenement, ajouterContact, recupContact, ajouterContactAvecId, recupDerniersContacts,
            dissocierEntrepriseContact, supprimerRelationCategorieContact, supprimerContactDunEvenement, modifierContact;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupTousContacts = connexion.prepareStatement("select * from contact order by nom");
            recupContacts = connexion.prepareStatement("select * from contact order by nom,prenom");
            recupContactparId = connexion.prepareStatement("select * from contact where id=?");
            recupContactParEntreprise = connexion.prepareStatement("select * from contact where idEntreprise=? order by nom,prenom");
            ajouterContactSansId = connexion.prepareStatement("insert into contact(mail, nom, prenom, dateNaissance, numTel, statut, idEntreprise) VALUES (?, ?, ?, ?, ?, ?, ?);");
            recupContactsParEvenement = connexion.prepareStatement("select * from contact as c, participea as p where c.id=p.contactId and p.evenementNom=? and p.evenementDate=?");
            ajouterContact = connexion.prepareStatement("insert into contact(id, mail, nom, prenom, dateNaissance, numTel, statut, idEntreprise) values (?,?,?,?,?,?,?,?)");
            recupNbContactSansEntreprise = connexion.prepareStatement("select count(*) from contact where idEntreprise = '-1'");
            recupContact = connexion.prepareStatement("select * from contact order by nom, prenom");
            recupDerniersContacts = connexion.prepareStatement("select * from contact order by id DESC limit 3");
            dissocierEntrepriseContact = connexion.prepareStatement("update contact set idEntreprise = '-1' where (id = ?)");
            supprimerRelationCategorieContact = connexion.prepareStatement("delete from appartienta where (categorieNom = ?) and (contactId = ?)");
            supprimerContactDunEvenement = connexion.prepareStatement("delete from participea where contactId=? and evenementNom=? and evenementDate=?");
            modifierContact = connexion.prepareStatement("update contact SET `mail` = ?, `nom` = ?, `prenom` = ?, `dateNaissance` = ?, `numTel` = ?, `statut` = ?, `idEntreprise` = ? WHERE (`id` = ?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> listeContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            ResultSet result = recupContact.executeQuery();
            ArrayList<Entreprise> entreprises = new ArrayList<>();
            ArrayList<Double> idEntreprise = new ArrayList<>();
            while (result.next()) {
                int identifiant = result.getInt("id");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                Date dateNaissance = result.getDate("dateNaissance");
                String numTel = result.getString("numTel");
                String statut = result.getString("statut");
                idEntreprise.add(result.getDouble("idEntreprise"));
                Contact c = new Contact(identifiant, mail, nom, prenom, dateNaissance, numTel, statut);
                contacts.add(c);

            }
            result.close();
            recupContact.close();
            connexion.close();
            int i = 0;
            for (Contact c : contacts) {
                List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(c.getIdentifiant());
                for (Categorie cat : categories)
                    c.ajouterCategorie(cat);
                Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(idEntreprise.get(i));
                c.setEntreprise(entreprise);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(c.getIdentifiant());
                for (Commentaire com : commentaires)
                    c.ajouterCommentaire(com);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return contacts;
    }

    public List<Contact> recupererTousContacts() {
        try {
            ResultSet result = recupTousContacts.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            ArrayList<Double> entreprises = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                Date date = result.getDate("dateNaissance");
                String numTel = result.getString("numTel");
                String statut = result.getString("statut");
                Double idEntreprise = result.getDouble("idEntreprise");
                entreprises.add(idEntreprise);
                contacts.add(new Contact(id, mail, nom, prenom, date, numTel, statut));
            }
            result.close();
            recupTousContacts.close();
            connexion.close();

            int i = 0;
            for (Contact c : contacts) {
                List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(c.getIdentifiant());
                for (Categorie cat : categories)
                    c.ajouterCategorie(cat);
                Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(entreprises.get(i));
                c.setEntreprise(entreprise);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(c.getIdentifiant());
                for (Commentaire com : commentaires)
                    c.ajouterCommentaire(com);
                i++;
            }

            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Contact recupContactParId(int id) {
        try {
            recupContactparId.setInt(1, id);
            ResultSet result = recupContactparId.executeQuery();
            result.next();
            String mail = result.getString("mail");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            Date dateNaissance = result.getDate("dateNaissance");
            String numTel = result.getString("numTel");
            String statut = result.getString("statut");
            List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(id);
            Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(result.getDouble("idEntreprise"));
            List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(id);
            result.close();
            recupContactparId.close();
            connexion.close();
            return new Contact(id, mail, nom, prenom, dateNaissance, numTel, statut, categories, entreprise, commentaires);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean creationContact(String mail, String nom, String prenom, Date dateNaissance, String numTel, String statut, Double idEntreprise) {

        try {
            ajouterContactSansId.setString(1, mail);
            ajouterContactSansId.setString(2, nom);
            ajouterContactSansId.setString(3, prenom);
            ajouterContactSansId.setDate(4, dateNaissance);
            ajouterContactSansId.setString(5, numTel);
            ajouterContactSansId.setString(6, statut);
            ajouterContactSansId.setDouble(7, idEntreprise);
            ajouterContactSansId.executeUpdate();
            ajouterContactSansId.close();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dissocierEntrepriseContact(int idContact) {
        try {
            dissocierEntrepriseContact.setInt(1, idContact);
            dissocierEntrepriseContact.executeUpdate();
            dissocierEntrepriseContact.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean supprimerRelationCategorieContact(int idContact, String nomCategorie) {
        try {
            supprimerRelationCategorieContact.setString(1, nomCategorie);
            supprimerRelationCategorieContact.setInt(2, idContact);
            supprimerRelationCategorieContact.executeUpdate();
            supprimerRelationCategorieContact.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public List<Contact> recupererContactsParEvenement(String nom, Date date) {
        try {
            recupContactsParEvenement.setString(1, nom);
            recupContactsParEvenement.setDate(2, (java.sql.Date) date);
            ResultSet result = recupContactsParEvenement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            ArrayList<Double> entreprises = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String mail = result.getString("mail");
                String nomC = result.getString("nom");
                String prenom = result.getString("prenom");
                Date dateC = result.getDate("dateNaissance");
                String numTel = result.getString("numTel");
                String statut = result.getString("statut");
                Double entrepriseId = result.getDouble("idEntreprise");
                entreprises.add(entrepriseId);
                contacts.add(new Contact(id, mail, nomC, prenom, dateC, numTel, statut));
            }
            result.close();
            recupContactsParEvenement.close();
            connexion.close();
            int i = 0;
            for (Contact c : contacts) {
                List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(c.getIdentifiant());
                for (Categorie cat : categories)
                    c.ajouterCategorie(cat);
                Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(entreprises.get(i));
                c.setEntreprise(entreprise);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(c.getIdentifiant());
                for (Commentaire com : commentaires)
                    c.ajouterCommentaire(com);
                i++;
            }
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean supprimerContactFromEvenement(int contactId, String evenementNom, java.sql.Date dateEvenement) {
        try {
            supprimerContactDunEvenement.setInt(1, contactId);
            supprimerContactDunEvenement.setString(2, evenementNom);
            supprimerContactDunEvenement.setDate(3, dateEvenement);
            supprimerContactDunEvenement.executeUpdate();
            supprimerContactDunEvenement.close();
            connexion.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public List<Contact> derniersContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            ResultSet result = recupDerniersContacts.executeQuery();
            ArrayList<Double> entreprises = new ArrayList<>();
            while (result.next()) {
                int identifiant = result.getInt("id");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                Date dateNaissance = result.getDate("dateNaissance");
                String numTel = result.getString("numTel");
                String statut = result.getString("statut");
                double idEnt = result.getDouble("idEntreprise");
                entreprises.add(idEnt);
                Contact c = new Contact(identifiant, mail, nom, prenom, dateNaissance, numTel, statut);
                contacts.add(c);
            }
            result.close();
            recupDerniersContacts.close();
            connexion.close();
            int i = 0;
            for (Contact c : contacts) {
                List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(c.getIdentifiant());
                for (Categorie cat : categories)
                    c.ajouterCategorie(cat);
                Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(entreprises.get(i));
                c.setEntreprise(entreprise);
                List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(c.getIdentifiant());
                for (Commentaire com : commentaires)
                    c.ajouterCommentaire(com);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return contacts;
    }


    public List<Contact> recupererContactParCritere(String nom, String prenom, java.sql.Date dateNaissance, String numTel, String mail, String categorie, String statut, String entreprise) {
        ArrayList<Contact> contacts = null;
        try {
            int i = 1;
            String requete = "select distinct id from contact c left join appartienta a on c.id = a.contactID where ";
            if (!"".equals(nom)) {
                requete += "c.nom like ?";
                i++;
            }
            if (!"".equals(prenom)) {
                if (i > 1)
                    requete += " and ";
                requete += "c.prenom like ?";
                i++;
            }
            if (!"".equals(mail)) {
                if (i > 1)
                    requete += " and ";
                requete += "c.mail like ?";
                i++;
            }
            if (!"".equals(numTel)) {
                if (i > 1)
                    requete += " and ";
                requete += "c.numTel like ?";
                i++;
            }
            if (!"".equals(statut)) {
                if (i > 1)
                    requete += " and ";
                requete += "c.statut like ?";
                i++;
            }
            if (!"".equals(categorie)) {
                if (i > 1)
                    requete += " and ";
                requete += "a.categorieNom like ?";
                i++;
            }
            if (!"".equals(entreprise)) {
                if (i > 1)
                    requete += " and ";
                requete += "c.idEntreprise in (select id from entreprise where nom like ?)";
                i++;
            }
            if (dateNaissance != null) {
                if (i > 1)
                    requete += " and ";
                requete += "c.dateNaissance like ?";
                i++;
            }
            PreparedStatement recupererContactParCritere = connexion.prepareStatement(requete);
            i = 1;
            if (!"".equals(nom)) {
                recupererContactParCritere.setString(i, "%" + nom + "%");
                i++;
            }
            if (!"".equals(prenom)) {
                recupererContactParCritere.setString(i, "%" + prenom + "%");
                i++;
            }
            if (!"".equals(mail)) {
                recupererContactParCritere.setString(i, "%" + mail + "%");
                i++;
            }
            if (!"".equals(numTel)) {

                recupererContactParCritere.setString(i, "%" + "" + "%");
                i++;
            }
            if (!"".equals(statut)) {
                recupererContactParCritere.setString(i, "%" + statut + "%");
                i++;
            }
            if (!"".equals(categorie)) {
                recupererContactParCritere.setString(i, "%" + categorie + "%");
                i++;
            }
            if (!"".equals(entreprise)) {

                recupererContactParCritere.setString(i, "%" + entreprise + "%");
                i++;
            }
            if (dateNaissance != null) {
                recupererContactParCritere.setDate(i, dateNaissance);
            }
            ResultSet result = recupererContactParCritere.executeQuery();
            contacts = new ArrayList<>();
            ArrayList<Integer> listContactId = new ArrayList<>();
            while (result.next()) {
                listContactId.add(result.getInt("id"));
            }
            result.close();
            for (int id:listContactId){
                contacts.add(this.recupContactParIdPrivate(id));
            }

            recupererContactParCritere.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    private Contact recupContactParIdPrivate(int id) {
        try {
            recupContactparId.setInt(1, id);
            ResultSet result = recupContactparId.executeQuery();
            result.next();
            String mail = result.getString("mail");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            Date dateNaissance = result.getDate("dateNaissance");
            String numTel = result.getString("numTel");
            String statut = result.getString("statut");
            List<Categorie> categories = new CategorieDAO().recupererCategorieParContact(id);
            Entreprise entreprise = new EntrepriseDAO().recupererEntreprise(result.getDouble("idEntreprise"));
            List<Commentaire> commentaires = new CommentaireDAO().recupererCommentairesParContact(id);
            result.close();
            return new Contact(id, mail, nom, prenom, dateNaissance, numTel, statut, categories, entreprise, commentaires);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public boolean ajouterContact(Contact contact) {
        try {
            ajouterContactAvecId = connexion.prepareStatement("insert into contact(id, mail, nom, prenom, dateNaissance, numTel, statut, idEntreprise) values (?,?,?,?,?,?,?,?)");
            ajouterContactAvecId.setInt(1, contact.getIdentifiant());
            ajouterContactAvecId.setString(2, contact.getMail());
            ajouterContactAvecId.setString(3, contact.getNom());
            ajouterContactAvecId.setString(4, contact.getPrenom());
            ajouterContactAvecId.setDate(5, new java.sql.Date(contact.getDateNaissance().getTime()));
            ajouterContactAvecId.setString(6, contact.getNumTel());
            ajouterContactAvecId.setString(7, contact.getStatut());
            if (contact.getEntreprise() == null)
                ajouterContactAvecId.setString(8, null);
            else
                ajouterContactAvecId.setDouble(8, contact.getEntreprise().getIdentifiant());
            ajouterContactAvecId.executeUpdate();
            ajouterContactAvecId.close();
            connexion.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public int recupNbContactSansEntreprise() {
        try {
            ResultSet result = recupNbContactSansEntreprise.executeQuery();
            result.next();
            int res = result.getInt(1);
            result.close();
            recupNbContactSansEntreprise.close();
            connexion.close();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public boolean modifierContact(String mail, String nom, String prenom, Date dateNaissance, String numTel, String statut, Double idEntreprise, int idContact){

        try{
            modifierContact.setString(1, mail);
            modifierContact.setString(2, nom);
            modifierContact.setString(3, prenom);
            modifierContact.setDate(4, dateNaissance);
            modifierContact.setString(5, numTel);
            modifierContact.setString(6, statut);
            modifierContact.setDouble(7, idEntreprise);
            modifierContact.setDouble(8, idContact);
            modifierContact.executeUpdate();
            modifierContact.close();
            connexion.close();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Contact> recupererContactParEntreprise(double idEntreprise) {
        try {
            recupContactParEntreprise.setDouble(1, idEntreprise);
            ResultSet result = recupContactParEntreprise.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                Date date = result.getDate("dateNaissance");
                String numTel = result.getString("numTel");
                String statut = result.getString("statut");
                contacts.add(new Contact(id, mail, nom, prenom, date, numTel, statut, new CategorieDAO().recupererCategorieParContact(id), new CommentaireDAO().recupererCommentairesParContact(id)));
            }
            result.close();
            recupContactParEntreprise.close();
            connexion.close();
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
