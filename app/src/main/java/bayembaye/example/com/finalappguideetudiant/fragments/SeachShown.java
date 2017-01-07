package bayembaye.example.com.finalappguideetudiant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.R;

public class SeachShown extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";


    private ListView listFound;
    private ArrayList<String> list;

    private OnFragmentInteractionListener mListener;

    public SeachShown() {
        // Required empty public constructor
    }

    public static SeachShown newInstance(ArrayList<String> listfound) {
        SeachShown fragment = new SeachShown();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, listfound);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.list = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_seach_shown, container, false);
        listFound = (ListView)root.findViewById(R.id.list_view_show_search);
        if (list  != null){
        listFound.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,list));
        listFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Driver.mean(list.get(i),getContext());
                if (i== -1)
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"il faut etre plus  explicite",Snackbar.LENGTH_SHORT).show();
                else {
                    ItemShower shower = new ItemShower().newInstance(id, "Resulat de la recherche");
                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.content_main, shower, Driver.ITEMSHOWER_TAG)
                            .commit();
                }
            }
        });
        }
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
