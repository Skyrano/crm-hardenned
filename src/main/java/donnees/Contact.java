package donnees;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Contact implements HasCategories, Serializable {

    private final int identifiant;
    private final String mail, nom, prenom, numTel, statut;
    private final Date dateNaissance;
    private final List<Categorie> categories;
    private final List<Commentaire> commentaires;
    private Entreprise entreprise;

    public Contact(int identifiant, String mail, String nom, String prenom, Date dateNaissance, String numTel, String statut, List<Categorie> categories, Entreprise entreprise, List<Commentaire> commentaires) {
        this.identifiant = identifiant;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.statut = statut;
        this.categories = categories;
        this.commentaires = commentaires;
        this.entreprise = entreprise;
    }

    public Contact(int identifiant, String mail, String nom, String prenom, Date dateNaissance, String numTel, String statut, List<Categorie> categories, List<Commentaire> commentaires) {
        this.identifiant = identifiant;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.statut = statut;
        this.categories = categories;
        this.commentaires = commentaires;
    }

    public Contact(int identifiant, String mail, String nom, String prenom, Date dateNaissance, String numTel, String statut) {
        this.identifiant = identifiant;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.statut = statut;
        categories = new ArrayList<>();
        commentaires = new ArrayList<>();
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public String getMail() {
        return mail;
    }

    public String getNom() {
        return nom;
    }

    public String toString() {
        StringBuilder retour = new StringBuilder();
        retour.append(nom + " ");
        retour.append(prenom);
        return retour.toString();
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        if (dateNaissance != null) return (Date) dateNaissance.clone();
        return null ;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getStatut() {
        return statut;
    }

    public List<Categorie> getCategories() {
        return new ArrayList<>(categories);
    }

    public List<Commentaire> getCommentaires() {
        return new ArrayList<>(commentaires);
    }

    public Entreprise getEntreprise() {
        if (entreprise != null)
            return entreprise.getCopy();
        else
            return null;
    }

    public void setEntreprise(Entreprise ent) {
        this.entreprise = ent;
    }

    public void ajouterCategorie(Categorie cat) {
        this.categories.add(cat);
    }

    public void ajouterCommentaire(Commentaire com) {
        this.commentaires.add(com);
    }

}
