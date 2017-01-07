package bayembaye.example.com.finalappguideetudiant.Drivers;

/**
 * Created by bayembaye on 28/12/2016.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class LoadFromExternalDB {
    private URL url;
    private HttpURLConnection connection = null;

    public LoadFromExternalDB(URL url) {
        this.url = url;
    }
    private void etablirConnexion() {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setReadTimeout(10000);
            connection.connect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
    }
    public String livreFromServer(){
        etablirConnexion();
        String returnvalue = null;
        if (connection == null)
            return null;

        InputStream stream = null;
        try {
            stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream,Driver.formatGetter));
            if (reader == null)
                Log.d("SVR","le reader ne fonctionne pas !");
            else
            Log.d("SVR","le reader est non null !");
            String line = "";
            while (( line = reader.readLine())!=null){
                returnvalue += line;
            }
            reader.close();
            stream.close();
        } catch (IOException e) {
            Log.d("SERVERTAG","la recuperation au niveau du serveur a echouer");
        }finally {
            connection.disconnect();
        }
        //lire les donnees recuperer dans le serveur
        return  (returnvalue == null) ? null:returnvalue;
    }
}
