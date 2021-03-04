package control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DOAabs {

    private Connection connexion;
    private String url, utilisateur, mdp;

    protected DOAabs() {
        try {
            Properties proprietes = new Properties();
            InputStream fichierProprietes = this.getClass().getClassLoader().getResourceAsStream("META-INF/dao.properties");
            try {
                proprietes.load(fichierProprietes);
                Class.forName(proprietes.getProperty("driver"));
                url = proprietes.getProperty("url");
                utilisateur = proprietes.getProperty("nomutilisateur");
                mdp = proprietes.getProperty("motdepasse");
                connexion = DriverManager.getConnection(url, utilisateur, mdp);
                this.initStatement();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected Connection getConnexion(){return this.connexion;}
    protected abstract void initStatement();

}
