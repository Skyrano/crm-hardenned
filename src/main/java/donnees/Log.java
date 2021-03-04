package donnees;

import java.io.Serializable;
import java.sql.Date;

public class Log implements Serializable {

    private final int identifiant;
    private final Date date;
    private final String type, contenu;
    private final Utilisateur utilisateurConcerne;

    public Log(int identifiant, Date date, String type, String contenu, Utilisateur utilisateurConcerne) {
        this.identifiant = identifiant;
        this.date = date;
        this.type = type;
        this.contenu = contenu;
        this.utilisateurConcerne = utilisateurConcerne;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public String getType() {
        return type;
    }

    public String getContenu() {
        return contenu;
    }

    public Utilisateur getUtilisateurConcerne() {
        return utilisateurConcerne.getCopy();
    }
}
