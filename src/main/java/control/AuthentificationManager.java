package control;

import donnees.Pages;
import donnees.Role;
import donnees.Utilisateur;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Alistair Rameau
 */
public class AuthentificationManager {

    private final int ITERATIONS = 25119;
    private final int KEY_LENGTH = 512;

    private Connection connexion;
    private PreparedStatement connectUtilisateur, ajoutUtilisateur, modifUtilisateur, modifMdp, authentification, roleUtilisateur,
            checkCompteur, resetCompteur, incrementCompteur, supprUtilisateur, supprLog, supprEstPresent, recupUtilisateursParEvenement,
            recupUtilisateurs, recupUtilisateur, recuputilisateurPasPresentAEvenement;

    public static String generateRandomString(int length) {
        StringBuilder stringB = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = new SecureRandom().nextInt(62);
            if (c <= 9) {
                stringB.append(String.valueOf(c));
            } else if (c < 36) {
                stringB.append((char) ('a' + c - 10));
            } else {
                stringB.append((char) ('A' + c - 36));
            }
        }
        return stringB.toString();
    }

    public AuthentificationManager() {
        try {
            Properties proprietes = new Properties();
            InputStream fichierProprietes = this.getClass().getClassLoader().getResourceAsStream(Pages.DAO_PROPERTIES.serveur());
            try {
                proprietes.load(fichierProprietes);
                Class.forName(proprietes.getProperty("driver"));
                connexion = DriverManager.getConnection(proprietes.getProperty("url"), proprietes.getProperty("nomutilisateur"), proprietes.getProperty("motdepasse"));
                this.initStatements();
                //log(LogType.CONNEXION_DAO, "Connexion à la BDD par le DAO", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Utilisateur connexion(String identifiant, String mdp) {
        try {
            if (authentification(identifiant, mdp)) {
                this.connectUtilisateur.setString(1, identifiant);
                ResultSet result = this.connectUtilisateur.executeQuery();
                result.next();
                String id = result.getString("identifiant");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                String poste = result.getString("poste");
                int compteurErreur = result.getInt("compteurErreur");
                Role role = new RoleDAO().recupererRole(result.getString("nomRole"));
                result.close();
                connectUtilisateur.close();
                connexion.close();
                return new Utilisateur(id, mail, nom, prenom, poste, role, compteurErreur);
            } else {
                connexion.close();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String changementMdp(String identifiant, String ancienMdp, String nouveauMdp, String confirmMdp) {
        if (!"".equals(nouveauMdp) && !"".equals(confirmMdp)) {
            if (nouveauMdp.equals(confirmMdp))
                if (!"".equals(ancienMdp)) {
                    if (nouveauMdp.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")) {
                        if (authentification(identifiant, ancienMdp)) {
                            if (modifierMdp(identifiant, nouveauMdp))
                                return "<div class='alert alert-success text-center' role='alert'>Mot de passe modifié avec succès !</div>";
                            else
                                return "<div class='alert alert-danger text-center' role='alert'>Erreur dans la modification</div>";
                        } else
                            return "<div class='alert alert-danger text-center' role='alert'>Ancien mot de passe incorrect</div>";

                    } else {
                        return "<div class='alert alert-secondary text-center' role='alert'>Le mot de passe n'est pas assez fort</div>";
                    }
                } else
                    return "<div class='alert alert-secondary text-center' role='alert'>Veuillez entrer votre ancien mot de passe</div>";
            else
                return "<div class='alert alert-danger text-center' role='alert'>Les deux mots de passe ne correspondent pas</div>";
        } else
            return "";
    }

    public boolean creerUtilisateur(Utilisateur utilisateur, String mdp, String adminId, String adminMdp) {
        try {
            if (authentificationAdmin(adminId, adminMdp)) {
                ajoutUtilisateur.setString(1, utilisateur.getIdentifiant());
                ajoutUtilisateur.setString(2, hashPassword(mdp));
                ajoutUtilisateur.setString(3, utilisateur.getMail());
                ajoutUtilisateur.setString(4, utilisateur.getPrenom());
                ajoutUtilisateur.setString(5, utilisateur.getNom());
                ajoutUtilisateur.setString(6, utilisateur.getPoste());
                ajoutUtilisateur.setString(7, utilisateur.getRole().getNom());
                ajoutUtilisateur.executeUpdate();
                ajoutUtilisateur.close();
                connexion.close();
                return true;
            } else {
                connexion.close();
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modifierUtilisateur(Utilisateur utilisateur, String mdp, String adminId, String adminMdp) {
        try {
            if (authentificationAdmin(adminId, adminMdp)) {
                modifUtilisateur.setString(1, utilisateur.getMail());
                modifUtilisateur.setString(2, utilisateur.getPrenom());
                modifUtilisateur.setString(3, utilisateur.getNom());
                modifUtilisateur.setString(4, utilisateur.getPoste());
                modifUtilisateur.setString(5, utilisateur.getRole().getNom());
                modifUtilisateur.setString(6, utilisateur.getIdentifiant());
                modifUtilisateur.executeUpdate();
                modifUtilisateur.close();
                if (mdp != null && !mdp.isEmpty())
                    return modifierMdp(utilisateur.getIdentifiant(), mdp);
                else{
                    connexion.close();
                    return true;
                }
            } else {
                connexion.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerUtilisateur(String id, String adminId, String adminMdp) {
        try {
            if (authentificationAdmin(adminId, adminMdp)) {
                supprLog.setString(1, id);
                supprLog.executeUpdate();
                supprEstPresent.setString(1, id);
                supprEstPresent.executeUpdate();
                supprUtilisateur.setString(1, id);
                supprUtilisateur.executeUpdate();
                supprLog.close();
                supprEstPresent.close();
                supprUtilisateur.close();
                connexion.close();
                return true;
            } else {
                connexion.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Utilisateur recupererUtilisateurParID(String identifiant) {
        Utilisateur utilisateur = null;
        try {
            recupUtilisateur.setString(1, identifiant);
            ResultSet result = recupUtilisateur.executeQuery();
            result.next();
            String mail = result.getString("mail");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            String poste = result.getString("poste");
            String role = result.getString("nomRole");
            result.close();
            recupUtilisateur.close();
            connexion.close();
            Role r = new RoleDAO().recupererRole(role);
            utilisateur = new Utilisateur(identifiant, mail, nom, prenom, poste, r,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public List<Utilisateur> recupererUtilisateursParEvenement(String nom, Date date) {
        try {
            recupUtilisateursParEvenement.setString(1, nom);
            recupUtilisateursParEvenement.setDate(2, (java.sql.Date) date);
            ResultSet result = recupUtilisateursParEvenement.executeQuery();
            List<Utilisateur> utilisateurs = new ArrayList<>();
            while (result.next()) {
                String id = result.getString("identifiant");
                String mail = result.getString("mail");
                String nomU = result.getString("nom");
                String prenom = result.getString("prenom");
                String poste = result.getString("poste");

                Role role = new RoleDAO().recupererRole(result.getString("nomRole"));
                utilisateurs.add(new Utilisateur(id, mail, nomU, prenom, poste, role,0));
            }
            result.close();
            recupUtilisateursParEvenement.close();
            connexion.close();
            return utilisateurs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Utilisateur> recuputilisateurPasPresentAEvenement(String nom, Date date) {
        try {
            recuputilisateurPasPresentAEvenement.setString(1, nom);
            recuputilisateurPasPresentAEvenement.setDate(2, (java.sql.Date) date);
            ResultSet result = recuputilisateurPasPresentAEvenement.executeQuery();
            List<Utilisateur> utilisateurs = new ArrayList<>();
            ArrayList<String> roles = new ArrayList<>();

            while (result.next()) {
                String identifiant = result.getString("identifiant");
                String mail = result.getString("mail");
                String nomResult = result.getString("nom");
                String prenom = result.getString("prenom");
                String poste = result.getString("poste");
                roles.add(result.getString("nomRole"));

                Utilisateur u = new Utilisateur(identifiant, mail, nomResult, prenom, poste,0);
                utilisateurs.add(u);
            }
            result.close();
            recuputilisateurPasPresentAEvenement.close();
            connexion.close();
            int i = 0;
            for(Utilisateur u : utilisateurs){
                u.setRole(new RoleDAO().recupererRole(roles.get(i)));
                ++i;
            }
            return utilisateurs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Utilisateur> recupererUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            ResultSet result = recupUtilisateurs.executeQuery();
            ArrayList<String> roles = new ArrayList<>();
            while (result.next()) {
                String identifiant = result.getString("identifiant");
                String mail = result.getString("mail");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                String poste = result.getString("poste");
                String nomRole =result.getString("nomRole");
                roles.add(nomRole);
                Utilisateur u = new Utilisateur(identifiant, mail, nom, prenom, poste,0);
                utilisateurs.add(u);
            }
            result.close();
            recupUtilisateurs.close();
            connexion.close();
            int i = 0;
            for(Utilisateur u : utilisateurs){
                u.setRole(new RoleDAO().recupererRole(roles.get(i)));
                ++i;
            }
        } catch (SQLException e) {
            e.printStackTrace();// return null;
        }
        return utilisateurs;
    }

    private void initStatements() {
        try {
            connectUtilisateur = connexion.prepareStatement("select * from utilisateur where identifiant=?");
            ajoutUtilisateur = connexion.prepareStatement("insert into utilisateur (identifiant, mdp, mail, prenom, nom, poste, nomRole, compteurErreur) VALUES (?,?,?,?,?,?,?,0)");
            modifUtilisateur = connexion.prepareStatement("update utilisateur set mail = ?, prenom = ?, nom=?, poste = ?, nomRole = ? where identifiant = ? ");
            modifMdp = connexion.prepareStatement("update utilisateur set mdp = ? where identifiant = ?");
            authentification = connexion.prepareStatement("select mdp from utilisateur where identifiant=?");
            roleUtilisateur = connexion.prepareStatement("select nomRole from utilisateur where  identifiant=?");
            checkCompteur = connexion.prepareStatement("select compteurErreur from utilisateur where identifiant=?");
            resetCompteur = connexion.prepareStatement("update utilisateur set compteurErreur=0 where identifiant=?");
            incrementCompteur = connexion.prepareStatement("update utilisateur set compteurErreur=compteurErreur+1 where identifiant=?");
            supprUtilisateur = connexion.prepareStatement("delete from utilisateur where identifiant=?");
            supprLog = connexion.prepareStatement("delete from log where idUtilisateur = ?");
            supprEstPresent = connexion.prepareStatement("delete from estpresenta where utilisateurIdentifiant=?");
            recupUtilisateursParEvenement = connexion.prepareStatement("select * from utilisateur as u, estpresenta e where u.identifiant=e.utilisateurIdentifiant and e.evenementNom=? and e.evenementDate=?");
            recupUtilisateurs = connexion.prepareStatement("select * from utilisateur");
            recupUtilisateur = connexion.prepareStatement("select * from utilisateur where identifiant=?");
            recuputilisateurPasPresentAEvenement = connexion.prepareStatement("select * from crm.utilisateur where identifiant not in (select utilisateurIdentifiant from crm.estpresenta where evenementNom=? and evenementDate=?) order by nom");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean authentification(String identifiant, String mdp) {
        if (identifiant != null && !identifiant.isEmpty() && !"Système".equals(identifiant) && mdp != null && !mdp.isEmpty()) {
            if (checkCompteurErreur(identifiant)) {
                try {
                    this.authentification.setString(1, identifiant);
                    ResultSet result = this.authentification.executeQuery();
                    result.next();
                    String expectedhash = result.getString("mdp");
                    if (expectedhash != null && !expectedhash.isEmpty() && checkPassword(mdp, expectedhash)) {
                        result.close();
                        resetCompteurErreur(identifiant);
                        return true;
                    } else {
                        result.close();
                        incrementerCompteurErreur(identifiant);
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else
                return false;
        } else
            return false;
    }

    private boolean authentificationAdmin(String identifiant, String mdp) {
        try {
            if (authentification(identifiant, mdp)) {
                this.roleUtilisateur.setString(1, identifiant);
                ResultSet result = this.roleUtilisateur.executeQuery();
                result.next();
                String role = result.getString("nomRole");
                result.close();
                roleUtilisateur.close();
                if ("admin".equals(role))
                    return true;
                else
                    return false;
            } else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean modifierMdp(String identifiant, String mdp) {
        try {
            if (identifiant != null && !identifiant.isEmpty() && mdp != null && !mdp.isEmpty()) {
                modifMdp.setString(1, hashPassword(mdp));
                modifMdp.setString(2, identifiant);
                modifMdp.executeUpdate();
                modifMdp.close();
                connexion.close();
                return true;
            } else {
                connexion.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkCompteurErreur(String identifiant) {
        int compteurErreur = Integer.MAX_VALUE;
        try {
            if (identifiant != null && !identifiant.isEmpty()) {
                checkCompteur.setString(1, identifiant);
                ResultSet result = checkCompteur.executeQuery();
                result.next();
                compteurErreur = result.getInt("compteurErreur");
                result.close();
                checkCompteur.close();
            }

            if (compteurErreur > 5) {
                return false;
            } else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetCompteurErreur(String identifiant) {
        try {
            if (identifiant != null && !identifiant.isEmpty()) {
                resetCompteur.setString(1, identifiant);
                resetCompteur.executeUpdate();
                resetCompteur.close();
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean incrementerCompteurErreur(String identifiant) {
        try {
            if (identifiant != null && !identifiant.isEmpty()) {
                incrementCompteur.setString(1, identifiant);
                incrementCompteur.executeUpdate();
                incrementCompteur.close();
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password) {
        byte[] salt = generateSalt();
        StringBuilder stringB = new StringBuilder();
        stringB.append(bytesToHexString(salt));
        stringB.append(":");
        stringB.append(bytesToHexString(hashHexString(password.toCharArray(), salt)));
        return stringB.toString();
    }

    private byte[] hashHexString(char[] hexstring, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(hexstring, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(hexstring, Character.MIN_VALUE); // détruit le mot de passe reçu
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    private boolean checkPassword(String password, String expectedhash) {
        String salt = expectedhash.split(":")[0];
        String hash = expectedhash.split(":")[1];
        return checkHash(password.toCharArray(), hexStringToBytes(salt), hexStringToBytes(hash));
    }

    private boolean checkHash(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hashHexString(password, salt);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    private byte[] generateSalt() {
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String bytesToHexString(byte[] bytes) {
        String hex = new BigInteger(1, bytes).toString(16);
        int paddingLength = (bytes.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private byte[] hexStringToBytes(String hexstring) {
        byte[] bytes = new byte[hexstring.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexstring.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
