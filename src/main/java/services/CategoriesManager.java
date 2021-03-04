package services;

import control.*;
import donnees.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alistair Rameau
 */
public class CategoriesManager {

    private List<Categorie> categoriesPossedee;
    private final List<Categorie> categoriesAll;

    private Boolean categorieNonSupprimee;

    CategoriesManager(HasCategories hasCategories, List<Categorie> categoriesAll) {
        if (hasCategories != null)
            this.categoriesPossedee = hasCategories.getCategories();
        this.categoriesAll = categoriesAll;
        this.categorieNonSupprimee = false;
    }

    public String premiereCategorie() {
        String result = "";
        if (categoriesPossedee != null && categoriesPossedee.size() > 0)
            return StringCleaner.cleaner(categoriesPossedee.get(0).getNom(), 80);
        return result;
    }

    public String categoriesPossedee(){
        String result = "";
        String stringOpts = "";
        if (categoriesPossedee != null && categoriesPossedee.size() > 1) {
            for (int i = 1; i < categoriesPossedee.size(); i ++) {
                stringOpts = "";
                for (Categorie categorie : categoriesAll){
                    if (!categorie.getNom().equals(categoriesPossedee.get(i).getNom()))
                        stringOpts+="<option value='"+StringCleaner.cleaner(categorie.getNom(), 80)+"'>"+StringCleaner.cleaner(categorie.getNom(), 80)+"</option> ";
                }
                result += "<div class='form-group d-flex'> <select class='form-control custom-select' id='select[]' name='categorieInput[]'> <option selected value='"+ StringCleaner.cleaner(categoriesPossedee.get(i).getNom(), 80)+"'>"+ StringCleaner.cleaner(categoriesPossedee.get(i).getNom(), 80)+"</option> "+stringOpts+" </select> <button class='btn btn-danger remove-categorie' type='button'>Supprimer</button> </div>";
            }
        }
        return result;
    }

    public void updateContact(Contact contact, String[] categoriesInput) {
        String newCategorie = "";
        Boolean present = false;
        ArrayList<String> alreadyComputed = new ArrayList<>();
        for (int i = 0; i < categoriesInput.length; i++) {
            newCategorie = categoriesInput[i];
            if (!newCategorie.isEmpty() && !alreadyComputed.contains(newCategorie)) {
                present = false;
                for (Categorie oldCategorie : categoriesPossedee) {
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    new CategorieDAO().ajouterAppartientA(contact.getIdentifiant(), newCategorie);
                    alreadyComputed.add(newCategorie);
                }
            }
        }

        alreadyComputed.clear();
        for (Categorie oldCategorie : categoriesPossedee) {
            if (!alreadyComputed.contains(oldCategorie.getNom())) {
                present = false;
                for (int i = 0; i < categoriesInput.length; i++) {
                    newCategorie = categoriesInput[i];
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    new CategorieDAO().supprimerAppartientA(contact.getIdentifiant(), StringCleaner.cleaner(oldCategorie.getNom(), 80));
                    alreadyComputed.add(StringCleaner.cleaner(oldCategorie.getNom(), 80));
                }
            }
        }
    }

    public void updateEntreprise(Entreprise entreprise, String[] categoriesInput) {
        String newCategorie = "";
        Boolean present = false;
        ArrayList<String> alreadyComputed = new ArrayList<>();
        for (int i = 0; i < categoriesInput.length; i++) {
            newCategorie = categoriesInput[i];
            if (!newCategorie.isEmpty() && !alreadyComputed.contains(newCategorie)) {
                present = false;
                for (Categorie oldCategorie : categoriesPossedee) {
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    new CategorieDAO().ajouterRepresente(entreprise.getIdentifiant(), newCategorie);
                    alreadyComputed.add(newCategorie);
                }
            }
        }

        alreadyComputed.clear();
        for (Categorie oldCategorie : categoriesPossedee) {
            if (!alreadyComputed.contains(oldCategorie.getNom())) {
                present = false;
                for (int i = 0; i < categoriesInput.length; i++) {
                    newCategorie = categoriesInput[i];
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    new CategorieDAO().supprimerRepresente(entreprise.getIdentifiant(), StringCleaner.cleaner(oldCategorie.getNom(), 80));
                    alreadyComputed.add(StringCleaner.cleaner(oldCategorie.getNom(), 80));
                }
            }
        }
    }

    public void updateCategories(String[] listeCategories, Utilisateur user) {
        categorieNonSupprimee = false;
        String newCategorie = "";
        Boolean present = false;
        ArrayList<String> alreadyComputed = new ArrayList<>();
        for (int i = 0; i < listeCategories.length; i++) {
            newCategorie = listeCategories[i].trim();
            if (!newCategorie.isEmpty() && !alreadyComputed.contains(newCategorie)) {
                present = false;
                for (Categorie oldCategorie : categoriesAll) {
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    new CategorieDAO().ajouterCategorie(StringCleaner.cleaner(newCategorie, 80));
                    new LogDAO().log(LogType.CREATION_CATEGORIE, "La catégorie "+StringCleaner.cleaner(newCategorie, 80)+" a été ajoutée ", user);
                    alreadyComputed.add(StringCleaner.cleaner(newCategorie, 80));
                }
            }
        }

        alreadyComputed.clear();
        for (Categorie oldCategorie : categoriesAll) {
            if (!alreadyComputed.contains(oldCategorie.getNom())) {
                present = false;
                for (int i = 0; i < listeCategories.length; i++) {
                    newCategorie = listeCategories[i].trim();
                    if (oldCategorie.getNom().equals(newCategorie)) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    List<String> categoriesContatcs = new CategorieDAO().recupNomCategoriesDesContacts();
                    List<String> categoriesEntreprises = new CategorieDAO().recupNomCategoriesDesEntreprises();
                    if (!categoriesContatcs.contains(oldCategorie.getNom()) && !categoriesEntreprises.contains(oldCategorie.getNom())) {
                        new CategorieDAO().supprimerCategorie(oldCategorie.getNom());
                        new LogDAO().log(LogType.SUPPRESSION_CATEGORIE, "La catégorie "+StringCleaner.cleaner(oldCategorie.getNom(), 80)+" a été supprimée ", user);
                    }
                    else
                        categorieNonSupprimee = true;
                    alreadyComputed.add(StringCleaner.cleaner(oldCategorie.getNom(), 80));
                }
            }
        }
    }

    public Boolean categoriesNonSupprimee() {
        return this.categorieNonSupprimee;
    }


}
