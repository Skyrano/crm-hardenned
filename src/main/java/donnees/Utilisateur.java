package donnees;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private final String identifiant;
    private final String mail;
    private final String nom;
    private final String prenom;
    private final String poste;
    private final int compteurErreur;

    private Role role;

    public Utilisateur(String identifiant, String mail, String nom, String prenom, String poste, int compteurErreur) {
        this.identifiant = identifiant;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.compteurErreur = compteurErreur;
    }

    public Utilisateur(String identifiant, String mail, String nom, String prenom, String poste, Role role, int compteurErreur) {
        this.identifiant = identifiant;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.role = role;
        this.compteurErreur = compteurErreur;
    }
    public Utilisateur getCopy() {
        return new Utilisateur(identifiant, mail, nom, prenom, poste, role.getCopy(), compteurErreur);
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMail() {
        return mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPoste() {
        return poste;
    }

    public Role getRole() {
        return role.getCopy();
    }

    public int getCompteurErreur() {
        return compteurErreur;
    }

    public String toString(){
        StringBuilder retour = new StringBuilder();
        retour.append(nom + " ");
        retour.append(prenom);
        return retour.toString();
    }

    public void setRole(Role r){
        this.role = r;
    }
}