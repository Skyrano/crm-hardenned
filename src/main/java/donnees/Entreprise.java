package donnees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entreprise implements HasCategories, Serializable {

    private final double identifiant;
    private int nbEmployes;
    private String nom, domaine, mailContact;
    private Adresse adresse;
    private List<Contact> employes;
    private Groupe groupeParent;
    private List<Commentaire> commentaires;
    private List<Categorie> categories;

    public Entreprise(double identifiant, int nbEmployes, String nom, String domaine, String mailContact, Adresse adresse, List<Contact> employes, Groupe groupeParent, List<Categorie> categories, List<Commentaire> commentaires) {
        this.identifiant = identifiant;
        this.nbEmployes = nbEmployes;
        this.nom = nom;
        this.domaine = domaine;
        this.mailContact = mailContact;
        this.adresse = adresse;
        this.employes = employes;
        this.groupeParent = groupeParent;
        this.categories = categories;
        this.commentaires = commentaires;
        for(Contact c : employes){
            c.setEntreprise(this);
        }
    }


    public Entreprise(double identifiant) {
        this.identifiant = identifiant;
        this.nbEmployes = 0;
        this.nom = null;
        this.domaine = null;
        this.mailContact = null;
        this.adresse = null;
        this.employes = null;
        this.groupeParent = null;
        this.categories = null;
        this.commentaires = null;
    }


    public void setEntreprise(int nbEmployes, String nom, String domaine, String mailContact, Adresse adresse, List<Contact> employes, Groupe groupeParent, List<Categorie> categories, List<Commentaire> commentaires) {
        this.nbEmployes = nbEmployes;
        this.nom = nom;
        this.domaine = domaine;
        this.mailContact = mailContact;
        this.adresse = adresse;
        this.employes = employes;
        this.groupeParent = groupeParent;
        this.categories = categories;
        this.commentaires = commentaires;
        for(Contact c : employes){
            c.setEntreprise(this);
        }
    }

    public Entreprise getCopy() {
        return new Entreprise(identifiant, nbEmployes, nom, domaine, mailContact, adresse.getCopy(), new ArrayList<>(employes), groupeParent.getCopy(), new ArrayList<>(categories), new ArrayList<>(commentaires));
    }

    public List<Contact> getEmployes() {
        return new ArrayList<>(employes);
    }

    public double getIdentifiant() {
        return identifiant;
    }

    public long getLongIdentifiant() {
        return (long) identifiant;
    }

    public int getNbEmployes() {
        return nbEmployes;
    }

    public String getNom() {
        return nom;
    }

    public String getDomaine() {
        return domaine;
    }

    public String getMailContact() {
        return mailContact;
    }

    public Adresse getAdresse() {
        return adresse.getCopy();
    }

    public int getNbContacts(){
        return this.employes.size();
    }

    public Groupe getGroupeParent() {
        if (groupeParent != null) return groupeParent.getCopy();
        return null;
    }

    public List<Commentaire> getCommentaires() {
        return new ArrayList<>(commentaires);
    }

    public List<Categorie> getCategories() {
        return new ArrayList<>(categories);
    }

}
