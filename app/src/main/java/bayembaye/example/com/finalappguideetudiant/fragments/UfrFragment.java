package bayembaye.example.com.finalappguideetudiant.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bayembaye.example.com.finalappguideetudiant.Drivers.Driver;
import bayembaye.example.com.finalappguideetudiant.R;

public class UfrFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //Views
    private GridView gridView;
    private OnFragmentInteractionListener mListener;

    public UfrFragment() {
        // Required empty public constructor
    }

    public static UfrFragment newInstance(String param1, String param2) {
        UfrFragment fragment = new UfrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ufr, container, false);
        gridView = (GridView) root.findViewById(R.id.gridview_for_ufr);
        gridView.setAdapter(new UfrAdaptator(this.getContext(), R.layout.ufr_view, Driver.ListUFR));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Displayer fragment = new Displayer().newInstance(Driver.ImageListOfUFR[i], Driver.ID_TEXT[i], i);
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, fragment, Driver.DISPLAYER_TAG)
                        .addToBackStack(Driver.DISPLAYER_TAG)
                        .commit();
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

    public class UfrAdaptator extends ArrayAdapter<String> {

        public UfrAdaptator(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View newView = convertView;
            if (newView == null) {
                LayoutInflater ft = LayoutInflater.from(parent.getContext());
                newView = ft.inflate(R.layout.ufr_view, parent, false);
            }
            // after that identified of the view and set them
            TextView text = (TextView) newView.findViewById(R.id.text_ufr);
            ImageView image = (ImageView) newView.findViewById(R.id.image_ufr);
            TextView pup = (TextView) newView.findViewById(R.id.text_pup);

            //make the view on the screen
//            if (image != null && image.getDrawable() != null)
//                ((BitmapDrawable) image.getDrawable()).getBitmap().recycle();

            image.setImageResource(Driver.ImageListOfUFR[position]);
            text.setText(Driver.ListUFR[position]);
            pup.setText(Driver.Motcles[position]);
            return newView;
        }
    }
}
