package control;

import donnees.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO extends DOAabs{
    private Connection connexion;

    private PreparedStatement recupGroupe, ajouterGroupe, recupAllGroupe;


    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupGroupe = connexion.prepareStatement("select * from groupe where numSiret=?");
            recupAllGroupe = connexion.prepareStatement("select * from groupe");
            ajouterGroupe = connexion.prepareStatement("insert into groupe(numSiret, nom, domaine, idAdresse) values (?, ?, ?, ?)");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Groupe recupererGroupe(double numSiret, Entreprise entreprise) {
        Groupe groupe = new Groupe(numSiret);
        try {
            recupGroupe.setDouble(1, numSiret);
            ResultSet result = recupGroupe.executeQuery();
            result.next();
            String nom = result.getString("nom");
            String domaine = result.getString("domaine");
            List<Entreprise> filiales = new EntrepriseDAO().recupererFilialesGroupe(groupe, entreprise);
            groupe.setGroupe(nom, domaine, filiales, new AdresseDAO().recupererAdresse(result.getInt("idAdresse")));
            result.close();
            recupGroupe.close();
            connexion.close();
            return groupe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Groupe recupererGroupe(double numSiret) {
        Groupe groupe = new Groupe(numSiret);
        try {
            recupGroupe.setDouble(1, numSiret);
            ResultSet result = recupGroupe.executeQuery();
            result.next();
            String nom = result.getString("nom");
            String domaine = result.getString("domaine");
            int idAddr = result.getInt("idAdresse");
            result.close();
            recupGroupe.close();
            connexion.close();
            groupe.setGroupe(nom, domaine, new ArrayList<Entreprise>(), new AdresseDAO().recupererAdresse(idAddr));

            return groupe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int recupNbGroupe(){
        try {
            ResultSet result = recupAllGroupe.executeQuery();
            int nbGroupes = 0;
            while(result.next()){
                nbGroupes++;
            }
            result.close();
            recupAllGroupe.close();
            connexion.close();
            return nbGroupes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public boolean creationGroupe(double numSiret, String nom, String domaine, int idAdresse){
        try{
            ajouterGroupe.setDouble(1, numSiret);
            ajouterGroupe.setString(2, nom);
            ajouterGroupe.setString(3, domaine);
            ajouterGroupe.setInt(4, idAdresse);
            ajouterGroupe.executeUpdate();
            ajouterGroupe.close();
            connexion.close();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
