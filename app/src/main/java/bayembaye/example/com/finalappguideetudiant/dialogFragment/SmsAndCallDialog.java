package bayembaye.example.com.finalappguideetudiant.dialogFragment;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import bayembaye.example.com.finalappguideetudiant.R;


/**
 * Created by bayembaye on 03/12/2016.
 */

public class SmsAndCallDialog extends DialogFragment {

    private int tel;
    private String ARG = "tel";
    public SmsAndCallDialog newIntence(int tel){
        SmsAndCallDialog smsAndCallDialog = new SmsAndCallDialog();
        Bundle agr = new Bundle();
        agr.putInt(ARG,tel);
        smsAndCallDialog.setArguments(agr);
        return smsAndCallDialog;
    }
    public SmsAndCallDialog(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            this.tel = getArguments().getInt(ARG);
        }
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sms_or_call_layout,container,false);
         ImageButton sms = (ImageButton)root.findViewById(R.id.sms_button);
        ImageButton call = (ImageButton)root.findViewById(R.id.call_button);
        Button cancel = (Button)root.findViewById(R.id.button_cancel_sms_call);


        //set the listens
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = String.valueOf(tel);
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:"+numero));
//                smsIntent.setType("vnd.android-dir/mms-sms");
                startActivity(smsIntent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telephone = String.valueOf(tel);// Votre numéro de téléphone
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + telephone));
                startActivity(callIntent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
                return;
            }
        });

        return root;
    }


}
