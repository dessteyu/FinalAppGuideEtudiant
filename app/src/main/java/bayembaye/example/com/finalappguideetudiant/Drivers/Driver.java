package bayembaye.example.com.finalappguideetudiant.Drivers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


import bayembaye.example.com.finalappguideetudiant.R;

/**
 * Created by bayembaye on 08/11/2016.
 */

public class Driver {
    public static final String
            //Pour le package fragement
    DISPLAYER_TAG = "DISPLAYER_TAG",
    ITEMSHOWER_TAG = "ITEMSHOWER_TAG",
    SERARCH_SHOW_TAG = "SERARCH_SHOW_TAG",
    UFR_FRAGMENT = "UFR_FRAGMENT",
    UTILS_FRAGMENT_TAG =  "UTILS_FRAGMENT_TAG",
    LIVRE_TAG = "LIVRE_TAG";
    // cette tag va permettre de d'appeler les dernier fragment ajouter au file

    public final static String formatGetter = "iso-8859-1";
    public static String TAG = null;

    public static String[] ListUFR = {
            "UFR SANTE",
            "UFR SET",
            "UFR SES",
            "UFR SI",
            "ENSA",
            "UIT",
            "ISFAR"
    },Motcles = {
            "Reviez-vous de soigner les maux de ce monde? aviez un troussoux de medcin lors que vous " +
                    "etait gamine? Oui Alors cette formation est pour vous!",
            "Etes crieux ? Vous interssez-vous de savoir comment sa marche ? Etes vous un Geek?" +
                    "Oui! Alors Cette UFR s\'atisfera surement vous attentes",
            "Vous avez le sens des affaires. Vous aimez les defits. Vous revez de perser un jour " +
                    "le monde du bisness. Alors qu\'attendz-vous?",
            "Vous voulais etres ingennieur en genie civil ou plus encore ? " +
                    "Passer le concours car vous en avez surement les moins",
            "Le maitier d\'agronome et l\'un des plus priser dans ce pays." +
                    " Et le secteur  reste a parfaire.",
            "Tu veux consevoir, crier des project, etre dans le terrain, " +
                    "participer a des stages partout dans le Senegal?",
            "Le maitier d\'agronome et l\'un des plus priser dans ce pays." +
                    " Et le secteur  reste a parfaire."
    }
    ,TITLES_LISTES = {
            "Les offres de formation à l\'UFR SANTE",
            "Les offres de formation à l\'UFR SET",
            "Les offres de formation à l\'UFR SES",
            "Les offres de formation à l\'UFR SI",
            "Les offres de formation à l\'ENSA",
            "Les offres de formation à l\'IUT",
            "Les offres de formation à l\'ISFAR"
    },
            OTHER_TITLES ={
                    "Présentation de L\'Université de Thiès",
                    "Modalité d\'inscription pour nationalité Sénégalaise",
                    "Modalité d\'inscription pour nationalité Etrangère",
                    "Les offres de services sociaux de L\'université",
                    "La réforme LMD (pour « Licence-Master-Doctorat »)",
                    "Logment et Restauration"
            };
    public static int[] ImageListOfUFR = {
        R.drawable.ufrsante,
        R.drawable.ufr_set,
        R.drawable.ufr_ses,
        R.drawable.ufr_si,
        R.drawable.ensa7,
        R.drawable.iut,
        R.drawable.isfar,
        R.drawable.univ,
        R.drawable.univ
    },ID_TEXT = {
        R.string.sante_text,
            R.string.text_set,
            R.string.ses_text,
            R.string.si_text,
            R.string.ensa_text,
            R.string.ept_text,
            R.string.isfar_text,
    },
    TEXT = {
            R.string.def_note,
            R.string.cred_sys,
            R.string.def_univ,
            R.string.soc,
            R.string.insc_etr,
            R.string.insc_sen,
            R.string.restauration,
            R.string.validation
    };
   public static   UtilsContacts[]UTILS_CONTACT_LIST =
    {
        new UtilsContacts("UFR SANTE",775724546,"CHEF DE LA SCOLAITE MADAME ELIZABETh SAGNA","sante-univ-thies.sn"),
        new UtilsContacts("UFR SET",339510717," ","set-univ-thies.sn"),
        new UtilsContacts("UFR SES",775080596,"Madama CLEMENTINE KING CHEF DE LA SCOLARITE","ses-univ-thies.sn"),
        new UtilsContacts("UFR SI",339510451," ","si-univ-thies.sn"),
        new UtilsContacts("ENSA",339395926," ","ensa-univ-thies.sn"),
        new UtilsContacts("IUT",339511312," ","uit-univ-thies.sn"),
        new UtilsContacts("ISFAR",339391900," ","isfar-univ-thies.sn"),
        new UtilsContacts("SCOLARITE CENTRALE",775607128,"RESPONSABLE DU BAI0","univ-thies.sn"),
        new UtilsContacts("RECTORAT",339397600," ","univ-thies.sn")
    };
    public static UtilsContacts[] LIST_AMICALES = {
        new UtilsContacts("AERT-THIES",773099787,"Abdou Aziz Diop","Président de l'amicale"),
        new UtilsContacts("AERP-THIES",773460990,"Membre du Bureau","")
    };
public final static LieuUtils[] LIST_LIEUX_UTILS= {
        new LieuUtils("UT",14.765570, -16.891099),
        new LieuUtils("UFR SANTE",14.792538, -16.935993),
        new LieuUtils("ENSA",14.765632, -16.891142),
        new LieuUtils("ISFAR",14.695937, -16.469622),
        new LieuUtils("ECOBANK",14.797494, -16.925575),
        new LieuUtils("SI",14.787047, -16.941103),
        new LieuUtils("UIT",14.790569, -16.930959)
    };

   public static LieuUtils THIES_VILLE = new LieuUtils("Thiès Ville",14.791425, -16.927616);


    //the function for charging le libraire books
    public static List<Livre> chargeurJSONVFile(String list){
     if (list==null)
         return null;
        List<Livre> listLivre =  new ArrayList<>();
        String tabjson [] = list.split(";");
        for (String item: tabjson) {
            try {
                JSONObject json = new JSONObject(item);
                int id = json.getInt("id");
                String titre = json.getString("titre");
                String auteur = json.getString("auteur");
                String edition =json.getString("edition");
                listLivre.add(new Livre(id,auteur,titre,edition));
            } catch (JSONException e) {
                Log.d("JSON","the convertion doesn't work");
            }
        }
        return (listLivre == null)? null : listLivre;
    }
    public static String[] retourList(String lis){
        return (lis == null)? null : lis.split(" ");
    }
    public static int mean(String search,Context context){
        List<String> list = new ArrayList<>();
        for (int id : ID_TEXT){
            list.add(context.getResources().getString(id));
        }
       for(int i = 0; i <list.size(); i++){
           if (list.get(i).equals(search))
               return ID_TEXT[i];
       }
         list = new ArrayList<>();
        for (int id : TEXT){
            list.add(context.getResources().getString(id));
        }
        for(int i = 0; i <list.size(); i++){
            if (list.get(i).equals(search))
                return TEXT[i];
        }
        return -1;
    }


}
