package donnees;

import java.io.Serializable;

public class Categorie implements Serializable {

    private final String nom;

    public Categorie(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }
}
