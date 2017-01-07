package bayembaye.example.com.finalappguideetudiant.Drivers;

/**
 * Created by bayembaye on 28/11/2016.
 */

public class UtilsContacts {
    private String name;
    private String typecase;
    private int tel;
    private String mail;


    public UtilsContacts(String name,int tel,String typecase,String mail){
        this.name = name;
        this.tel = tel;
        this.typecase = typecase;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public int getTel() {
        return tel;
    }

    public String getTypecase() {
        return typecase;
    }

}
