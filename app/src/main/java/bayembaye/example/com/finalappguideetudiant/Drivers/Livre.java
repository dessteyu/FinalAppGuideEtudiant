package bayembaye.example.com.finalappguideetudiant.Drivers;

/**
 * Created by bayembaye on 27/12/2016.
 */

public class Livre {
    private int id;
    private String title;
    private String auteur;
    private String editeur;
    public Livre(int id,String auteur,String title,String editeur)
    {
        this.title = title;
        this.editeur = editeur;
        this.auteur = auteur;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getEditeur() {
        return editeur;
    }
}
