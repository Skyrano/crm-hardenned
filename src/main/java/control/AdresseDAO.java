package control;

import donnees.Adresse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresseDAO extends DOAabs {
    private Connection connexion;

    private PreparedStatement recupAdresse, recupAdresseToGetId, verifAdresse, ajouterAdresse, derniereAdresse;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupAdresse = connexion.prepareStatement("select * from adresse where id =?");
            recupAdresseToGetId = connexion.prepareStatement("select * from adresse where numRue =? AND rue =? AND ville =? AND codePostal =? AND pays =?");
            verifAdresse = connexion.prepareStatement("select * from adresse where pays=? and ville=? and codePostal=? and rue=? and numRue=?");
            ajouterAdresse = connexion.prepareStatement("insert into adresse(pays, ville, codePostal, rue, numRue) values (?, ?, ?, ?, ?)");
            derniereAdresse = connexion.prepareStatement("select max(id) from adresse");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public Adresse recupererAdresse(int id) {
        try {
            recupAdresse.setInt(1, id);
            ResultSet result = recupAdresse.executeQuery();
            result.next();
            int identifiant = result.getInt("id");
            int numeroRue = result.getInt("numRue");
            int codePostal = result.getInt("codePostal");
            String pays = result.getString("pays");
            String ville = result.getString("ville");
            String rue = result.getString("rue");
            result.close();
            recupAdresse.close();
            connexion.close();
            return new Adresse(identifiant, numeroRue, pays, ville, codePostal, rue);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Adresse recupererAdresse(String pays, String ville, int codePostal, String rue, int numRue){
        try {
            recupAdresseToGetId.setInt(1, numRue);
            recupAdresseToGetId.setString(2, rue);
            recupAdresseToGetId.setString(3, ville);
            recupAdresseToGetId.setInt(4, codePostal);
            recupAdresseToGetId.setString(5, pays);
            ResultSet result = recupAdresseToGetId.executeQuery();
            result.next();
            int id = result.getInt("id");

            if (result.isFirst()) {
                result.close();
                recupAdresseToGetId.close();
                connexion.close();
                return (new Adresse(id, numRue, pays, ville, codePostal, rue));
            } else{
                result.close();
                recupAdresseToGetId.close();
                connexion.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean creationAdresse(String pays, String ville, int codePostal, String rue, int numRue){
        try{
            ajouterAdresse.setString(1, pays);
            ajouterAdresse.setString(2, ville);
            ajouterAdresse.setInt(3, codePostal);
            ajouterAdresse.setString(4, rue);
            ajouterAdresse.setInt(5, numRue);
            ajouterAdresse.executeUpdate();
            ajouterAdresse.close();
            connexion.close();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int checkAdressePourEvenement(int num, String rue, int codePostal, String ville, String pays){
        int idAdresse;
        try {
            verifAdresse.setString(1, pays);
            verifAdresse.setString(2, ville);
            verifAdresse.setInt(3, codePostal);
            verifAdresse.setString(4, rue);
            verifAdresse.setInt(5, num);
            ResultSet result = verifAdresse.executeQuery();
            if (result.next()){
                idAdresse = result.getInt("id");
            }
            else{
                ajouterAdresse.setString(1,pays);
                ajouterAdresse.setString(2,ville);
                ajouterAdresse.setInt(3,codePostal);
                ajouterAdresse.setString(4,rue);
                ajouterAdresse.setInt(5,num);
                ajouterAdresse.executeUpdate();
                ResultSet resultDeux = derniereAdresse.executeQuery();
                resultDeux.next();
                idAdresse = resultDeux.getInt(1);
                resultDeux.close();
            }
            result.close();
            verifAdresse.close();
            ajouterAdresse.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return idAdresse;
    }
}
