package control;
import donnees.Log;
import donnees.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogDAO extends DOAabs {
    private Connection connexion;

    private PreparedStatement ajouterLog, supprLog, recupLogs;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            ajouterLog = connexion.prepareStatement("insert into log(date, type, contenu, idUtilisateur) values (?, ?, ?, ?)");
            supprLog = connexion.prepareStatement("delete from log where idUtilisateur = ?");
            recupLogs = connexion.prepareStatement("select * from log order by date desc");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Log> listeLogs() {
        ArrayList<Log> logs = new ArrayList<>();
        try {
            ResultSet result = recupLogs.executeQuery();
            while (result.next()) {
                int identifiant = result.getInt("id");
                Date date = result.getDate("date");
                String type = result.getString("type");
                String contenu = result.getString("contenu");
                String idUtilisateur = result.getString("idUtilisateur");
                Utilisateur user = new AuthentificationManager().recupererUtilisateurParID(idUtilisateur);
                Log l = new Log(identifiant, date, type, contenu, user);
                logs.add(l);
                result.close();
                recupLogs.close();
                connexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return logs;
    }

    public void log(LogType type, String contenu, Utilisateur utilisateur) {
        try {
            long millis = System.currentTimeMillis();
            java.sql.Timestamp date = new java.sql.Timestamp(millis);
            ajouterLog.setTimestamp(1, date);
            ajouterLog.setString(2, type.toString());
            ajouterLog.setString(3, contenu);
            if (utilisateur != null)
                ajouterLog.setString(4, utilisateur.getIdentifiant());
            else
                ajouterLog.setString(4, "Système");
            ajouterLog.executeUpdate();
            ajouterLog.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
