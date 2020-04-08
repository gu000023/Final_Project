package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * The DetailsFragmentZhe class is a fragment contains detail information on tablet or phone
 * The information include image, text, url, hdurl
 * @author Zhe Lei
 * @version 1.0
 */
public class DetailsFragmentZhe extends Fragment {
    private static AppCompatActivity parentActivity;
    private Bitmap image = NASAImageInfoZhe.image;

    public DetailsFragmentZhe(){ }

    @Override
    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view object
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View first=inflater.inflate(R.layout.activity_details_fragment,container,false);
        Bundle dataFromActivty=getArguments();

        ImageView img = (ImageView) first.findViewById(R.id.img) ;
        img.setImageBitmap(image);

        TextView datefragment = (TextView) first.findViewById(R.id.dateText);
        datefragment.setText("DATE: " + dataFromActivty.getString("date"));

        TextView urlfragment = (TextView) first.findViewById(R.id.urlText);
        urlfragment.setText("URL: " + dataFromActivty.getString("url"));

        TextView hdurlfragment = (TextView) first.findViewById(R.id.hdurlText);
        hdurlfragment.setText("HDURL: " + dataFromActivty.getString("hdurl"));

        Button hide=(Button) first.findViewById(R.id.hideButton);
        hide.setOnClickListener(clk->{
            if(parentActivity.getSupportFragmentManager().findFragmentById(R.id.f1)!=null){
                parentActivity.finish();
            }else {
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
        });
        // Inflate the layout for this fragment
        return first;
    }


    /**
     * Called when a fragment is first attached to its context.
     * @param context Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity=(AppCompatActivity)context;
    }


}
