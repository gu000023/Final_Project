package com.example.finalproject;
/**
 * Fragment show news detail
 * Author : Yun Zhu
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BbcFragment extends Fragment {

    private Bundle dataToPass;
    private AppCompatActivity parentActivity;
    SQLiteDatabase db;

    public BbcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataToPass = getArguments();

        View result = inflater.inflate(R.layout.fragment_bbc, container, false);
        TextView title = (TextView) result.findViewById(R.id.bbc_tv2);
        title.setText(dataToPass.getString(bbc_main.SUBJECT));
        TextView date = (TextView) result.findViewById((R.id.bbc_tv3));
        date.setText(dataToPass.getString(bbc_main.DATE));

        TextView intro = (TextView) result.findViewById((R.id.bbc_tv4));
        intro.setText(dataToPass.getString(bbc_main.INTRO));

        TextView url = (TextView) result.findViewById((R.id.bbc_tv5));
        url.setText(dataToPass.getString(bbc_main.URL));
        // Inflate the layout for this fragment
        Button btn_fav = (Button) result.findViewById(R.id.bbc_bt4);
        if (btn_fav != null) {
            btn_fav.setOnClickListener(click -> {

                Intent goToFav = new Intent(getActivity(), bbc_fav.class);
                startActivityForResult(goToFav, 30);
            });
        }

        Button btn_back_main = (Button) result.findViewById(R.id.bbc_bt3);
        if (btn_back_main != null) {
            btn_back_main.setOnClickListener(click -> {

                Intent goToMain = new Intent(getActivity(), bbc_main.class);
                startActivityForResult(goToMain, 30);
            });
        }

        BbcOpener opener = new BbcOpener((BbcFragment.super.getActivity()));
        db = opener.getWritableDatabase();

        Button saveTofav = (Button) result.findViewById(R.id.bbc_bt8);
        if (saveTofav != null) {
            saveTofav.setOnClickListener(click -> {
                boolean includedDB = false;
                String[] dbColumns = {BbcOpener.COL_ID, BbcOpener.COL_TITLE, BbcOpener.COL_INTRO, BbcOpener.COL_DATE, BbcOpener.COL_URL};
                Cursor results = db.query(false, BbcOpener.TABLE_NAME, dbColumns, null, null, null, null, null, null);

                int titleIndex = results.getColumnIndex(BbcOpener.COL_TITLE);
                String newsTitle = dataToPass.getString(bbc_main.SUBJECT);
                results.moveToFirst();

                    ContentValues bbcValues = new ContentValues();
                    bbcValues.put(BbcOpener.COL_TITLE, dataToPass.getString(bbc_main.SUBJECT));
                    bbcValues.put(BbcOpener.COL_INTRO, dataToPass.getString(bbc_main.INTRO));
                    bbcValues.put(BbcOpener.COL_DATE, dataToPass.getString(bbc_main.DATE));
                    bbcValues.put(BbcOpener.COL_URL, dataToPass.getString(bbc_main.URL));
                    long id = db.insert(BbcOpener.TABLE_NAME, null, bbcValues);

                    Toast.makeText(getActivity(), this.getString(R.string.toast_db_save), Toast.LENGTH_SHORT).show();
            });
        }
            return result;
        }

}
