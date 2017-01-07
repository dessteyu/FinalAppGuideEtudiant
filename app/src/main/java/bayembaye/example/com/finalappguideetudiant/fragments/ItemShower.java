package bayembaye.example.com.finalappguideetudiant.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bayembaye.example.com.finalappguideetudiant.R;


public class ItemShower extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int idTextShower;
    private String idTitleSower;

    private OnFragmentInteractionListener mListener;

    public ItemShower() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemShower newInstance(int idTextShower, String idTitleSower) {
        ItemShower fragment = new ItemShower();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, idTextShower);
        args.putString(ARG_PARAM2, idTitleSower);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idTextShower = getArguments().getInt(ARG_PARAM1);
            idTitleSower = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_item_shower, container, false);
        //we find view for the shower screen
        TextView text = (TextView)root.findViewById(R.id.id_text_shower);
        ImageView img = (ImageView)root.findViewById(R.id.image_shower_);
        TextView title = (TextView)root.findViewById(R.id.id_title_shower);

        //make the view on the fragment
        img.setImageResource(R.drawable.univ);
        text.setText(idTextShower);
        title.setText(this.idTitleSower);
        return  root;
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
