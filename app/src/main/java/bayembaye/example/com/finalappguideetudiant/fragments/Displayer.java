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

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.R;


public class Displayer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int id_photo;
    private int id_text;
    private int title_text;

    private OnFragmentInteractionListener mListener;

    public Displayer() {
        // Required empty public constructor
    }
    public static Displayer newInstance(int id_photo, int id_text,int title) {
        Displayer fragment = new Displayer();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id_photo);
        args.putInt(ARG_PARAM2, id_text);
        args.putInt(ARG_PARAM3,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id_photo = getArguments().getInt(ARG_PARAM1);
            this.id_text = getArguments().getInt(ARG_PARAM2);
            this.title_text = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_displayer, container, false);
        // Inflate the layout for this fragment
        TextView text = (TextView)root.findViewById(R.id.text_displayer);
        ImageView img = (ImageView)root.findViewById(R.id.image_dispalyer);
        TextView title = (TextView)root.findViewById(R.id.id_title);

        //make the view on the fragment
        text.setText(getResources().getText(this.id_text));
        title.setText(Driver.TITLES_LISTES[this.title_text]);
        img.setImageResource(this.id_photo);
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
}
