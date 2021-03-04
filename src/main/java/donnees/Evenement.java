package donnees;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Evenement implements Serializable {

    private final String nom, type, description;
    private final Date date;
    private final Time heure;
    private final List<Utilisateur> utilisateursPresents;
    private final Adresse adresse;
    private final List<Commentaire> commentaires;
    private final List<Contact> contactsPresents;

    public Evenement(String nom, String type, String description, Date date, Time heure, List<Utilisateur> utilisateursPresents, Adresse adresse, List<Contact> contactsPresents, List<Commentaire> commentaires) {
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.heure = heure;
        this.date = date;
        this.utilisateursPresents = utilisateursPresents;
        this.adresse = adresse;
        this.commentaires = commentaires;
        this.contactsPresents = contactsPresents;
    }


    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public String getDateString(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(date);
    }

    public String getHeureString(){
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        if (heure != null)
            return f.format(heure);
        else
            return "";
    }

    public List<Utilisateur> getUtilisateursPresents() {
        return new ArrayList<>(utilisateursPresents);
    }

    public Adresse getAdresse() {
        return adresse.getCopy();
    }

    public List<Commentaire> getCommentaires() {
        return new ArrayList<>(commentaires);
    }

    public List<Contact> getContactsPresents() {
        return new ArrayList<>(contactsPresents);
    }
}
