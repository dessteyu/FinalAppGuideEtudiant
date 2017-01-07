package bayembaye.example.com.finalappguideetudiant.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.Drivers.UtilsContacts;
import bayembaye.example.com.finalappguideetudiant.Persistances.DataBaseFunctions;
import bayembaye.example.com.finalappguideetudiant.R;
import bayembaye.example.com.finalappguideetudiant.dialogFragment.SmsAndCallDialog;


public class UtilsContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final int RESQUESTCODE = 678904;


    // TODO: Rename and change types of parameters
    private int index  = -1;
    private UtilsContacts[]list = null;
    private DataBaseFunctions db = null;
    private SparseIntArray ErrorString;


    //views
    private ListView listView;

    private OnFragmentInteractionListener mListener;

    public UtilsContactsFragment() {
        // Required empty public constructor
    }

    public static UtilsContactsFragment newInstance(int index) {
        UtilsContactsFragment fragment = new UtilsContactsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }
        if (index!= -1) {
            if (index == 0)
                list = Driver.UTILS_CONTACT_LIST;
            else {
                db = new DataBaseFunctions(getContext());
                list = converTOTab(db.seletAll());
                db.close();
                db = null;
            }
        }
//        ErrorString = new SparseIntArray();
//        requestAppPermission(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS},
//                R.string.msgPermission,RESQUESTCODE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_utils_contacts,container,false);
        listView = (ListView)root.findViewById(R.id.list_utils_contacts);
        listView.setAdapter(new ContactsAdaptator(this.getContext(),R.layout.contact_util,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               android.app.FragmentTransaction manager = getActivity().getFragmentManager().beginTransaction();
                android.app.Fragment prev = getActivity().getFragmentManager().findFragmentByTag("SMSCALL");
                if (prev != null){
                    manager.remove(prev);
                }
                manager.addToBackStack(null);
                SmsAndCallDialog smsAndCallDialog = new SmsAndCallDialog().newIntence(list[i].getTel());
                smsAndCallDialog.show(manager,"SMSCALL");
            }
        });
        if(index!= 0){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int item, long l) {
                    final UtilsContacts utilsContacts = list[item];
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                            .setNegativeButton("SUPPRIMER ?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db = new DataBaseFunctions(getContext());
                                    if (db.delectUtil(utilsContacts.getTel())){
                                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                                utilsContacts.getName()+":supprimé!",Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setPositiveButton("MODIFIFIER ?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditContact editContact = new EditContact().newInstance(
                                            utilsContacts.getName(),utilsContacts.getTypecase(),utilsContacts.getMail(),utilsContacts.getTel()
                                    );
                                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.content_main,editContact,"EDIT_TAG")
                                            .commit();
                                }
                            })
                            .setMessage("Voullez-vous ");
                    alert.create()
                            .show();
                    return false;
                }
            });
            // work code for the app bar
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private UtilsContacts[] converTOTab(List<UtilsContacts> listcon){
        int len = listcon.size();
        UtilsContacts[] tab = new UtilsContacts[len];
        for (int i = 0 ; i< len; i++)
            tab[i] = listcon.get(i);
        return (tab == null) ? null : tab;
    }


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
            //TODO: display a message for the user
            Snackbar.make(getActivity().findViewById(android.R.id.content),ErrorString.get(requestCode),Snackbar.LENGTH_INDEFINITE)
                    .setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: call the sitting activity for enable the permissions
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+ getActivity().getPackageName()));
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
            permissionCheck =  permissionCheck + ContextCompat.checkSelfPermission(getActivity(),permission);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                showPermissionRequest = false;
            else {
                showPermissionRequest = shouldShowRequestPermissionRationale(permission);
            }
            if (permissionCheck != PackageManager.PERMISSION_GRANTED){
                if (showPermissionRequest){
                    Snackbar.make(getActivity().findViewById(android.R.id.content),stringId,Snackbar.LENGTH_INDEFINITE)
                            .setAction("GRANT", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        getActivity().requestPermissions(requestPermissions,requestCode);
                                    }else ActivityCompat.requestPermissions(getActivity().getParent(),requestPermissions,requestCode);
                                }
                            });
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        this.requestPermissions(requestPermissions,requestCode);
                    }else
                        ActivityCompat.requestPermissions(getActivity().getParent(),requestPermissions,requestCode);
                }
            }else
                onRequestGrant(requestCode);
        }

    }

    private void onRequestGrant(int requestCode) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),"Permissions aquises",Snackbar.LENGTH_SHORT).show();
    }


    public class ContactsAdaptator extends ArrayAdapter<UtilsContacts> {


        public ContactsAdaptator(Context context, int resource, UtilsContacts[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View  root = convertView;
            if (root ==null){
                LayoutInflater ft = LayoutInflater.from(parent.getContext());
                root = ft.inflate(R.layout.contact_util,parent,false);
            }
            //On instencie les views se trouvant dans fragment_utils_contacts
            ImageView img = (ImageView) root.findViewById(R.id.img_util_contact);
            TextView text_mail = (TextView)root.findViewById(R.id.email_utli);
            TextView text_tel = (TextView)root.findViewById(R.id.tel_util);
            TextView text_name = (TextView)root.findViewById(R.id.name_util);
            TextView text_desc = (TextView)root.findViewById(R.id.cas_util);


            //Mettre la view
            if(index == 0 )
            img.setImageResource(Driver.ImageListOfUFR[position]);
            else
            img.setImageResource(R.drawable.presantion);
            text_mail.setText(list[position].getMail());
            text_tel.setText(String.valueOf(list[position].getTel()));
            text_name.setText(list[position].getName());
            text_desc.setText(list[position].getTypecase());

            //On retourne le view.

            return root;
        }
    }
}
