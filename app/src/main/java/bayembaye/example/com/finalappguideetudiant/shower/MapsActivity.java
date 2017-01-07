package bayembaye.example.com.finalappguideetudiant.shower;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.Drivers.LieuUtils;
import bayembaye.example.com.finalappguideetudiant.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final int RESQUESTCODE = 2325;
    private static final int REQUESTINTERNET = 9001;
    private GoogleMap mMap;
    private SparseIntArray ErrorString;
    private final float ZOOM = 14.0f;
    private Button button_map;
    private EditText mapSearch;
    private static int countBackPress = 0;
    private Polyline line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_container);
        button_map = (Button) findViewById(R.id.button_goto);
        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    localiserLieu(view);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Erreur dans la recherche ou pas d'internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mapSearch = (EditText) findViewById(R.id.edit_map);
        //TODO:make the request permission for the fine my location
        ErrorString = new SparseIntArray();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap == null){
            Snackbar.make(findViewById(android.R.id.content),"mettez à jour google play services!",Snackbar.LENGTH_LONG).show();
            return;
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }else {
            requestAppPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},R.string.location_permission,RESQUESTCODE);
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                mMap.setMyLocationEnabled(true);
            else
//                Toast.makeText(this,"la permission n'est pas accorder pour vous localisez",Toast.LENGTH_SHORT).show();
                Snackbar.make(this.findViewById(android.R.id.content),"Voulez vous accorder les persmissions ? ",Snackbar.LENGTH_INDEFINITE)
                        .setAction("ENABLE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO: call the sitting activity for enable the permissions
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(intent);
                            }
                        }).show();
        }

        for(LieuUtils lieu : Driver.LIST_LIEUX_UTILS)
            setMarkers(lieu);

    }
    private void setMarkers(LieuUtils lieu){
        MarkerOptions options = new MarkerOptions()
                .title(lieu.getNom())
                .position(new LatLng(lieu.getLatitude(),lieu.getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_myplaces))
                .draggable(true);
        if (lieu.getNom().length()>0)
            options.snippet(lieu.getNom());
            mMap.addMarker(options);
        //aller directement dans la ville de thies
        gotoLacation(Driver.THIES_VILLE.getLongitude(),Driver.THIES_VILLE.getLatitude(),ZOOM);
    }
    private void addLines(LatLng l1,LatLng l2){
        if (line != null) {
            line.remove();
            line = null;
        }
        PolylineOptions options = new PolylineOptions();
        options.add(l1);
        options.add(l2);
        line = mMap.addPolyline(options);
    }
    private double distancebetween(LatLng l1, LatLng l2){
        double distancec = Math.sqrt(Math.pow((l1.latitude - l2.latitude),2) + Math.pow((l1.longitude - l2.longitude),2));
        return distancec;
    }

    private void localiserLieu(View view) throws IOException {

        hideKeyBoard(view);

        String searchText = mapSearch.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<android.location.Address> list = geocoder.getFromLocationName(searchText,1);
        android.location.Address address = list.get(0);
        String location = address.getLocality();

        if (!location.isEmpty())
            Toast.makeText(this,location,Toast.LENGTH_SHORT).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLacation(lng,lat,ZOOM);


    }
    private void hideKeyBoard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void gotoLacation(double lng, double lat, float cameraValue){
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, cameraValue);
        mMap.moveCamera(update);
    }
    //the reques permission code for the fine location and map view
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults){
            permissionCheck  += permission;
        }
        if ((grantResults.length > 0 ) && permissionCheck == PackageManager.PERMISSION_GRANTED){
            onRequestGrant(requestCode);
        }else {
            Snackbar.make(this.findViewById(android.R.id.content),ErrorString.get(requestCode),Snackbar.LENGTH_LONG)
                    .setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: call the sitting activity for enable the permissions
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+getPackageName()));
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        }
                    }).show();
        }
    }
    public void requestAppPermission(final String[] requestPermissions, final int stringId,
                                     final int requestCode){
        ErrorString.put(stringId,requestCode);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showPermissionRequest = false;
        for (final String permission : requestPermissions){
            permissionCheck =  permissionCheck + ContextCompat.checkSelfPermission(this,permission);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                showPermissionRequest = false;
            else {
                showPermissionRequest = shouldShowRequestPermissionRationale(permission);
            }
            if (permissionCheck != PackageManager.PERMISSION_GRANTED){
                if (showPermissionRequest){
                    Snackbar.make(findViewById(android.R.id.content),stringId,Snackbar.LENGTH_INDEFINITE)
                            .setAction("GRANT", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(requestPermissions,requestCode);
                                    }else ActivityCompat.requestPermissions(MapsActivity.this,requestPermissions,requestCode);
                                }
                            });
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        this.requestPermissions(requestPermissions,requestCode);
                    }else
                        ActivityCompat.requestPermissions(this,requestPermissions,requestCode);
                }
            }else
                onRequestGrant(requestCode);
        }

    }

    private void onRequestGrant(int requestCode) {
        Snackbar.make(findViewById(android.R.id.content),R.string.location_permission,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //on recupere backpress pour revenir au premier endroit dans l'activité


    @Override
    public void onBackPressed() {
        if(countBackPress == 1){
            countBackPress = 0;
            finish();
        }else {
            gotoLacation(Driver.THIES_VILLE.getLongitude(),Driver.THIES_VILLE.getLatitude(),ZOOM);
            Snackbar.make(findViewById(android.R.id.content),"Click encore une fois pour retourner!",Snackbar.LENGTH_LONG)
                    .show();
            countBackPress++;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
