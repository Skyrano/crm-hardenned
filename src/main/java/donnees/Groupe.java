package donnees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Groupe implements Serializable {

    private final double numSiret;
    private String nom, domaine;
    private Adresse siegeSocial;
    private List<Entreprise> filiales;

    public Groupe(double numSiret, String nom, String domaine, Adresse siegeSocial) {
        this.numSiret = numSiret;
        this.nom = nom;
        this.domaine = domaine;
        this.filiales = new ArrayList<>();
        this.siegeSocial = siegeSocial;
    }

    public Groupe(double numSiret) {
        this.numSiret = numSiret;
        this.nom = null;
        this.domaine = null;
        this.filiales = new ArrayList<>();
    }

    public void setGroupe(String nom, String domaine, List<Entreprise> filiales, Adresse siegeSocial) {
        this.nom = nom;
        this.domaine = domaine;
        this.filiales = filiales;
        this.siegeSocial = siegeSocial;
    }

    public Groupe getCopy() {
        Groupe groupe = new Groupe(numSiret);
        groupe.setGroupe(nom, domaine, new ArrayList<>(filiales), siegeSocial.getCopy());
        return groupe;
    }

    public double getNumSiret() {
        return numSiret;
    }

    public Adresse getSiegeSocial() {
        return siegeSocial.getCopy();
    }

    public String getNom() {
        return nom;
    }

    public String getDomaine() {
        return domaine;
    }
}
