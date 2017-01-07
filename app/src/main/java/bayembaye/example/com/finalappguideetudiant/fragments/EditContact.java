package bayembaye.example.com.finalappguideetudiant.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.Drivers.UtilsContacts;
import bayembaye.example.com.finalappguideetudiant.Persistances.DataBaseFunctions;
import bayembaye.example.com.finalappguideetudiant.R;
public class EditContact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOM = "nom";
    private static final String ARG_DESC = "desc";
    private static final String ARG_TEL = "tel";
    private static final String ARG_MAIL = "mail";



    // TODO: Rename and change types of parameters
    private String nomPara;
    private String descPara;
    private String mailPara;
    private int telPara ;
    private boolean edit = false;
    private EditText name;
    private EditText desc;
    private EditText mail;
    private EditText tel;
    private Button action;


    private OnFragmentInteractionListener mListener;

    public static EditContact newInstance(String nomPara,String descPara,String mailPara,int telPara) {
        EditContact fragment = new EditContact();
        Bundle args = new Bundle();
        args.putString(ARG_NOM,nomPara);
        args.putString(ARG_DESC,descPara);
        args.putString(ARG_MAIL,mailPara);
        args.putInt(ARG_TEL,telPara);
        fragment.setArguments(args);
        return fragment;
    }
    public EditContact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            telPara = bundle.getInt(ARG_TEL);
            nomPara = bundle.getString(ARG_NOM);
            descPara = bundle.getString(ARG_DESC);
            mailPara = bundle.getString(ARG_MAIL);
            edit = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_contact, container, false);
         name  = (EditText)root.findViewById(R.id.edi_name);
         desc = (EditText)root.findViewById(R.id.edit_desc);
         mail = (EditText)root.findViewById(R.id.edi_mail);
          tel = (EditText)root.findViewById(R.id.edit_tel);
       action = (Button)root.findViewById(R.id.action_save);

        //now we make the action click

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                        .setMessage("Saugarder les Changements:")
                        .setCancelable(true)
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataBaseFunctions db = new DataBaseFunctions(getContext());
                                String nametext = name.getText().toString();
                                String descText = desc.getText().toString();
                                String mailText = mail.getText().toString();
                                int telText = Integer.parseInt(tel.getText().toString());
                                if (edit){
                                if (db.editUtils(new UtilsContacts(nametext,telText,descText,mailText)))
                                    Snackbar.make(getActivity().findViewById(android.R.id.content),"MAJ :Réussie!",Snackbar.LENGTH_SHORT).show();
                                else Snackbar.make(getActivity().findViewById(android.R.id.content),"MAJ: Echec",Snackbar.LENGTH_SHORT).show();
                                db.close();
                                }else {
                                   db.addUtil(new UtilsContacts(nametext,telText,descText,mailText));
                                }
                                UtilsContactsFragment fragmentg =  new UtilsContactsFragment().newInstance(1);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main,fragmentg, Driver.UTILS_FRAGMENT_TAG)
                                .commit();
                            }
                        })
                        .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                alert.create()
                     .show();
            }
        });
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
