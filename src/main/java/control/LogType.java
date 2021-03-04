package control;

public enum LogType {
    CONNEXION_UTILISATEUR("Connexion Utilisateur"),
    DECONNEXION_UTILISATEUR("Déconnexion Utilisateur"),
    CONNEXION_DAO("Connexion DAO"),
    AJOUT_UTILISATEUR("Ajout Utilisateur"),
    MODIFICATION_UTILISATEUR("Modification Utilisateur"),
    SUPPRESSION_UTILISATEUR("Suppression Utilisateur"),
    CREATION_ENTREPRISE("Creation Entreprise"),
    MODIFICATION_ENTREPRISE("Modification Entreprise"),
    SUPPRESSION_ENTREPRISE("Suppression Entreprise"),
    CREATION_CONTACT("Creation Contact"),
    MODIFICATION_CONTACT("Modification Contact"),
    SUPPRESSION_CONTACT("Suppression Contact"),
    CREATION_CATEGORIE("Creation Catégorie"),
    SUPPRESSION_CATEGORIE("Suppression Catégorie"),
    ERREUR_CATEGORIE("Erreur Suppression catégorie"),
    CREATION_EVENEMENT("Creation Événement"),
    MODIFICATION_EVENEMENT("Modification Événement"),
    SUPPRESSION_EVENEMENT("Suppression Événement"),
    CREATION_COMMENTAIRE("Creation Commentaire"),
    SUPPRESSION_COMMENTAIRE("Suppression Commentaire"),
    AJOUT_LOG("Ajout d'un log"),
    SUPPRESSION_ROLE("Suppression d'un rôle"),
    MODIFICATION_ROLE("Suppression d'un rôle"),
    IMPORT_CSV("Import d'un CSV"),
    EXPORT_CSV("Export vers CSV"),
    ENVOI_MAIL("Envoi Mail");

    private final String text;

    LogType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
