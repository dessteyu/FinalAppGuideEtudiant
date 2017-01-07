package bayembaye.example.com.finalappguideetudiant.Drivers;

/**
 * Created by bayembaye on 11/12/2016.
 */

public class LieuUtils {
    private String nom;
    private double longitude;
    private double latitude;

    public LieuUtils(String nom, double latitude, double longitude) {

        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
