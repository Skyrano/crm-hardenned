package donnees;

/**
 * @author Alistair Rameau
 */
public enum Pages {

    LOGIN("/WEB-INF/login.jsp","login"),
    LOGOUT("/WEB-INF/logout.jsp","logout"),
    ACCUEIL("/WEB-INF/accueil.jsp","accueil"),

    CONTACT("/WEB-INF/affichage-contact.jsp", "affichage-contact"),
    CREATION_CONTACT("/WEB-INF/creation-contact.jsp","creation-contact"),
    MODIFIER_CONTACT("/WEB-INF/modifier-contact.jsp","modifier-contact"),
    LISTE_CONTACTS("/WEB-INF/contacts.jsp","contacts"),
    RECHERCHER_CONTACT("/WEB-INF/rechercheContact.jsp","rechercherContact"),

    ENTREPRISE("/WEB-INF/affichage-entreprise.jsp","affichage-entreprise"),
    CREATION_ENTREPRISE("/WEB-INF/creation-entreprise.jsp","creation-entreprise"),
    CREATION_ENTREPRISE_FROM_CONTACT("","creation-entreprise-from-contact"),
    MODIFIER_ENTREPRISE("/WEB-INF/modifier-entreprise.jsp","modifier-entreprise"),
    LISTE_ENTREPRISES("/WEB-INF/entreprises.jsp","entreprises"),
    RECHERCHER_ENTREPRISE("/WEB-INF/rechercheEntreprise.jsp","rechercherEntreprise"),

    UTILISATEUR("/WEB-INF/utilisateur.jsp","afficherUtilisateur"),
    CREATION_UTILISATEUR("/WEB-INF/creation-utilisateur.jsp","creation-utilisateur"),
    MODIFIER_UTILISATEUR("","modifier-utilisateur"),
    SUPPRIMER_UTILISATEUR("","supprimerUtilisateur"),
    LISTE_UTILISATEURS("/WEB-INF/utilisateurs.jsp", "gestionUtilisateurs"),

    EVENEMENT("/WEB-INF/evenement.jsp","evenement"),
    CREATION_EVENEMENT("/WEB-INF/creation-evenement.jsp","creation-evenement"),
    MODIFIER_EVENEMENT("/WEB-INF/modification-evenement.jsp","modification-evenement"),
    EVENEMENT_AJOUT_UTILISATEUR("/WEB-INF/evenementAjoutUtilisateur.jsp","evenementAjoutUtilisateur"),
    LISTE_EVENEMENTS("/WEB-INF/evenements.jsp","evenements"),

    CREATION_ROLE("/WEB-INF/creation-role.jsp","creation-role"),
    GESTION_ROLES("/WEB-INF/roles.jsp","gestionRoles"),
    SUPPRIMER_ROLE("","supprimerRole"),

    COMMENTAIRE("/WEB-INF/commentaire.jsp","commentaire"),
    CATEGORIES("/WEB-INF/categories-managing.jsp","gestion-categories"),
    LISTE_LOGS("/WEB-INF/logs.jsp","logs"),
    STATISTIQUES("/WEB-INF/statistiques.jsp","statistiques"),
    MAIL("/WEB-INF/envoi-mail.jsp","mail"),

    ADMIN("/WEB-INF/admin-panel.jsp","admin"),
    PROFIL("/WEB-INF/profil.jsp","afficherProfil"),
    EXPORT("","export"),
    IMPORT("","import"),
    A_PROPOS("/WEB-INF/a-propos.jsp","a-propos"),

    DAO_PROPERTIES("META-INF/dao.properties","");

    private final String urlServeur;
    private final String urlClient;

    Pages(String urlServeur, String urlClient){
        this.urlServeur = urlServeur;
        this.urlClient = urlClient;
    }

    public String serveur() {
        return this.urlServeur;
    }

    public String client() {
        return this.urlClient;
    }
}
