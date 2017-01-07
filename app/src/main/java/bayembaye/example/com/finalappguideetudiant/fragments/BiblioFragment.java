package bayembaye.example.com.finalappguideetudiant.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.Drivers.Livre;
import bayembaye.example.com.finalappguideetudiant.R;

public class BiblioFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Livre> listLivre = null;
    private ListView listView ;
    private String ARG = "LIVRE";

    public BiblioFragment() {
        // Required empty public constructor
    }
    public BiblioFragment newInstance(String list){
       BiblioFragment fragment =  new BiblioFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG,list);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            String livres = bundle.getString(ARG);
         listLivre = Driver.chargeurJSONVFile(livres);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_biblio, container, false);
        listView  = (ListView)root.findViewById(R.id.list_livre);
        if (listLivre != null)
        listView.setAdapter(new LivreAdapteur(getContext(),R.layout.row_livre,listLivre));
        else
            Snackbar.make(getActivity().findViewById(android.R.id.content),"Les Livres ne sont pas" +
                    " disponibles",Snackbar.LENGTH_LONG).show();
        return root;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public class LivreAdapteur extends ArrayAdapter<Livre>{

        public LivreAdapteur(Context context, int resource,List<Livre> livres) {
            super(context, resource,livres);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View root = convertView;
            if (root ==null){
                LayoutInflater ft = LayoutInflater.from(parent.getContext());
                root = ft.inflate(R.layout.row_livre,parent,false);
            }
            //the setter of the views
            TextView title = (TextView)root.findViewById(R.id.livre_titre);
            TextView auteur = (TextView)root.findViewById(R.id.livre_auteur);
            TextView edition = (TextView)root.findViewById(R.id.edition_livre);
            //make the value of the views
            title.setText(title.getText().toString() +" "+ listLivre.get(position).getTitle());
            auteur.setText(auteur.getText().toString() + " " +listLivre.get(position).getAuteur());
            edition.setText(edition.getText().toString() + " " + listLivre.get(position).getEditeur());
            return  root;
        }
    }
}
