package donnees;

import java.io.Serializable;

public class Role implements Serializable  {

    private final String nom;
    private final boolean accesEcriture;

    public Role(String nom, boolean accesEcriture) {
        this.nom = nom;
        this.accesEcriture = accesEcriture;
    }

    public Role getCopy() {
        return new Role(nom, accesEcriture);
    }

    public String getNom() {
        return nom;
    }

    public boolean getAccesEcriture() {
        return accesEcriture;
    }
}
