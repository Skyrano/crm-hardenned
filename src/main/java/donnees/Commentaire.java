package donnees;

import java.io.Serializable;
import java.sql.Date;

public class Commentaire implements Serializable {

    private final int id;
    private final Date date;
    private final String intitule, commentaire;

    public Commentaire(int id, Date date, String intitule, String commentaire) {
        this.id = id;
        this.date = date;
        this.intitule = intitule;
        this.commentaire = commentaire;
    }


    public int getId() {
        return id;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public String getIntitule() {
        return intitule;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
