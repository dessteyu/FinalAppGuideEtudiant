package bayembaye.example.com.finalappguideetudiant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.Drivers.LoadFromExternalDB;
import bayembaye.example.com.finalappguideetudiant.Drivers.UtilsContacts;
import bayembaye.example.com.finalappguideetudiant.Persistances.DataBaseFunctions;
import bayembaye.example.com.finalappguideetudiant.fragments.BiblioFragment;
import bayembaye.example.com.finalappguideetudiant.fragments.Displayer;
import bayembaye.example.com.finalappguideetudiant.fragments.EditContact;
import bayembaye.example.com.finalappguideetudiant.fragments.ItemShower;
import bayembaye.example.com.finalappguideetudiant.fragments.SeachShown;
import bayembaye.example.com.finalappguideetudiant.fragments.UfrFragment;
import bayembaye.example.com.finalappguideetudiant.fragments.UtilsContactsFragment;
import bayembaye.example.com.finalappguideetudiant.shower.MapsActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,Displayer.OnFragmentInteractionListener,
        UfrFragment.OnFragmentInteractionListener,UtilsContactsFragment.OnFragmentInteractionListener,
        ItemShower.OnFragmentInteractionListener,SeachShown.OnFragmentInteractionListener,
        BiblioFragment.OnFragmentInteractionListener,EditContact.OnFragmentInteractionListener{
    private MaterialSearchView searchview;
    private ArrayList<String> listRecherchable = null;
    private LivreOnServer task = null;
    private ProgressBar progressBar;
    private String array = null;
    private static int BACKPRESS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRessource();
        setview();

        callUFR();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            lancerMap();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void lancerMap() {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (BACKPRESS == 1)
            {
                BACKPRESS = 0;
                finish();

            }else
            {
                BACKPRESS ++;
                callUFR();
                Snackbar.make(findViewById(android.R.id.content),"Encore un Clique !",Snackbar.LENGTH_SHORT).show();
            }
        }
        if (searchview.isSearchOpen()){
            searchview.closeSearch();
        }
        showProgress(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchview.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.id_presantation) {
        CallShow(R.string.def_univ,Driver.OTHER_TITLES[0]);
        }  else if (id == R.id.id_ufr) {
            callUFR();
        } else if (id == R.id.id_inscription) {

            AlertDialog.Builder noteLmd = new AlertDialog.Builder(this);
            noteLmd.setTitle("Inscription?");
            noteLmd.setItems(R.array.liste_nations, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int cahe = R.string.insc_sen;
                    if ( i==1){
                        cahe = R.string.insc_etr;
                    }
                    CallShow(cahe,Driver.OTHER_TITLES[i+1]);
                }
            });
            noteLmd.show();
        } else if (id == R.id.id_vie_soc) {
            CallShow(R.string.soc, Driver.OTHER_TITLES[3]);
        }else if (id == R.id.id_list_num){
            afficheContact(0);
        }else if(id == R.id.def_lmd){
            AlertDialog.Builder noteLmd = new AlertDialog.Builder(this);
            noteLmd.setTitle("Quoi sur LMD ?");
            noteLmd.setItems(R.array.notes_lmd, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String[] tab = getResources().getStringArray(R.array.notes_lmd);
                    switch (i){
                        case 0: CallShow(R.string.def_note,tab[i]);
                            break;
                        case 1: CallShow(R.string.cred_sys,tab[i]);
                            break;
                        case 2: CallShow(R.string.validation,tab[i]);
                            break;
                        default:
                            break;
                    }

                }
            });
            noteLmd.show();
        }else if (id == R.id.groupememt){
            afficheContact(1);
        }else if(id == R.id.lib){
            load();
        }else if(id == R.id.id_logement){
            CallShow(R.string.restauration,Driver.OTHER_TITLES[5]);
        }
        BACKPRESS = 0;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
//        showProgress(false);
        return true;
    }
    public void addContact(View view){
       EditContact edit = new EditContact();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_main,edit,"AJOUT_TAG").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //call the class ItemShow
    private void afficheContact(int index){
        UtilsContactsFragment utilsContactsFragment = new UtilsContactsFragment().newInstance(index);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main,utilsContactsFragment,Driver.UTILS_FRAGMENT_TAG)
                .commit();
    }
    private void CallShow(int id_text,String title){
        ItemShower shower = new ItemShower().newInstance(id_text,title);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main,shower,Driver.ITEMSHOWER_TAG)
                .commit();
    }
    private void callUFR(){
        UfrFragment ufr = new UfrFragment();
        android.support.v4.app.FragmentManager manager  = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main,ufr,Driver.UFR_FRAGMENT)
                .commit();
    }
    private void lib(String livre){
        BiblioFragment biblio = new BiblioFragment().newInstance(livre);
        android.support.v4.app.FragmentManager   manager  = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content_main,biblio,Driver.LIVRE_TAG)
                .commit();
    }
    private ArrayList<String> recherchableList(){
        ArrayList<String> list = new ArrayList<>();
        for (int index : Driver.ID_TEXT){
            list.add(getResources().getString(index));
        }
        for (int index : Driver.TEXT){
            list.add(getResources().getString(index));
        }
        for (String text: Driver.Motcles)
        list.add(text);
        for (String text : Driver.ListUFR)
        list.add(text);
        for (String  text: Driver.OTHER_TITLES)
            list.add(text);
        return list;
    }

    private void initializeRessource(){
        searchview  = (MaterialSearchView)findViewById(R.id.search_view);
        progressBar = (ProgressBar)findViewById(R.id.livre_progress);
        DataBaseFunctions db = new DataBaseFunctions(this);
        if (db.isNull()){
            for (UtilsContacts util : Driver.LIST_AMICALES){
                db.addUtil(util);
            }
        }
        db.close();
    }
    //this for the setter view of the screen
    private void setview(){
        listRecherchable = recherchableList();
        searchview.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                getSupportActionBar().hide();
                searchview.setVisibility(View.VISIBLE);
                searchview.showVoice(true);
            }

            @Override
            public void onSearchViewClosed() {
                searchview.setVisibility(View.GONE);
                searchview.showVoice(false);
                getSupportActionBar().show();
             }

        });
        searchview.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && ! newText.isEmpty()){
                    ArrayList<String> listfound = new ArrayList<String>();
                    for (String item : listRecherchable){
                        if (item.toLowerCase().contains(newText.toLowerCase()))
                            listfound.add(item);
                    }
                    SeachShown shown = new SeachShown().newInstance(listfound);
                    android.support.v4.app.FragmentManager manager  = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.content_main,shown,Driver.SERARCH_SHOW_TAG)
                            .commit();
                }else {
                }
                return true;
            }
        });
        searchview.setVoiceSearch(true);
    }
    /**
     * Recuperer la liste des livre dans le serveur Mysql
     */

    public class LivreOnServer extends AsyncTask<Void,Void,Boolean> {
        private String name = "http://utguideapp.comeze.com";


        public LivreOnServer() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            showProgress(true);
            try {
                URL url = new URL(name);
                array = new LoadFromExternalDB(url).livreFromServer();
            } catch (MalformedURLException e) {
                Log.d("URL","la convertion de l'url ne fonction pas!");
            }
            return (array == null)? false : true;
        }

        @Override
        protected void onPostExecute(final Boolean b) {
            super.onPostExecute(b);
            if (b){
                //showProgress(false);
                lib(array);
            }else {
             Snackbar.make(findViewById(android.R.id.content),"necessite la connexion internet!",Snackbar.LENGTH_SHORT).show();
                //showProgress(false);
                callUFR();
            }
        }
    }
    private void load(){
        if (task != null || array!= null){
            showProgress(true);
           lib(array);
        }else {
       showProgress(true);
            task = new LivreOnServer();
            task.execute((Void) null);
        }
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
