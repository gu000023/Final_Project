package com.example.androidlabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static AppCompatActivity parentActivity;
    public static Bundle dataFromActivity;
    private long id;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataFromActivity=getArguments();
        id=dataFromActivity.getLong("ID");
        View firstView=inflater.inflate(R.layout.fragment_details, container, false);
        //Message m = getItem(position);
        //View firstView = inflater.inflate((m.isSent()) ? R.layout.sender : R.layout.receiver, container, false);
        TextView t1=(TextView)firstView.findViewById(R.id.t1);
        t1.setText(dataFromActivity.getString("msg"));//settext setchecked //getarg is bundle
        TextView t2=(TextView)firstView.findViewById(R.id.t2);
        t2.setText("ID= "+id);
        CheckBox cb=(CheckBox)firstView.findViewById(R.id.checkBox2);
        cb.setChecked(dataFromActivity.getBoolean("issent"));

        //Bundle b=new Bundle();
        //b.putString("Message1",t1.getText().toString());
        //b.putString("Message2",t2.getText().toString());
        //b.putBoolean("issent",Message.issent);
        //Log.d("frag set arg","set arg");
        //new DetailsFragment().setArguments(b);

        Button hide=(Button)firstView.findViewById(R.id.button8);
        hide.setOnClickListener(clk->{
            Log.d("hide","hide");
            if (parentActivity.getSupportFragmentManager().findFragmentById(R.id.fl) != null) {
                parentActivity.finish();
            }
            else {
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
        });

        return firstView;
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
        parentActivity=(AppCompatActivity)context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
