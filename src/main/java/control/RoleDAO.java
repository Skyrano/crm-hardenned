package control;

import donnees.Role;
import donnees.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DOAabs {
    private Connection connexion;

    private PreparedStatement recupRole, recupRoles, ajoutRole, supprRole, supprRoleUtilisateur, recupNbUParRole;

    @Override
    protected void initStatement() {
        connexion = this.getConnexion();
        try {
            recupRole = connexion.prepareStatement("select * from role where nom=?");
            recupRoles = connexion.prepareStatement("select * from role");
            ajoutRole = connexion.prepareStatement("insert into role(nom, ecriture) values (?, ?)");
            supprRole = connexion.prepareStatement("delete from role where nom=?");
            supprRoleUtilisateur = connexion.prepareStatement("update utilisateur set nomRole='user' where identifiant=?");
            recupNbUParRole = connexion.prepareStatement("select COUNT(*) as nombre from utilisateur where nomRole = ?");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Role recupererRole(String nomRole) {
        try {
            recupRole.setString(1, nomRole);
            ResultSet result = recupRole.executeQuery();
            result.next();
            String nom = result.getString("nom");
            boolean ec = result.getBoolean("ecriture");
            result.close();
            recupRole.close();
            connexion.close();
            return new Role(nom,ec);
        } catch (SQLException e) {
            e.printStackTrace();// return null;
        }
        return null;
    }

    public List<Role> recupererRoles() {
        List<Role> roles = null;
        try {
            roles = new ArrayList<>();
            ResultSet result = recupRoles.executeQuery();
            while (result.next()) {
                roles.add(new Role(result.getString("nom"), result.getBoolean("ecriture")));
            }
            result.close();
            recupRoles.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
    public boolean creerRole(Role r) {
        try {
            ajoutRole.setString(1, r.getNom());
            ajoutRole.setBoolean(2, r.getAccesEcriture());
            ajoutRole.executeUpdate();
            ajoutRole.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int recupNbUtilisateursParRole(String role) {
        try {
            recupNbUParRole.setString(1, role);
            ResultSet result = recupNbUParRole.executeQuery();
            result.next();
            int res = result.getInt("nombre");
            result.close();
            recupNbUParRole.close();
            connexion.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public boolean supprimerRoleUtilisateur(Utilisateur u) {
        try {
            supprRoleUtilisateur.setString(1, u.getIdentifiant());
            supprRoleUtilisateur.executeUpdate();
            supprRoleUtilisateur.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean supprimerRole(String nom) {
        try {
            for (Utilisateur u : new AuthentificationManager().recupererUtilisateurs()) {
                if (nom.equals(u.getRole().getNom()))
                    if (!supprimerRoleUtilisateur(u))
                        return false;
            }
            supprRole.setString(1, nom);
            supprRole.executeUpdate();
            supprRole.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
