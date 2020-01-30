package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeSet;

public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Message> send_receive = new ArrayList<>();
    //ArrayList<Object> receive = new ArrayList<>();
    private Button btn;
    private Button btn1;
    private BaseAdapter adapter = new myListAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView listview = (ListView) findViewById(R.id.lv);
        //send
        btn = (Button) findViewById(R.id.button5);

        btn.setOnClickListener((v) -> {
            EditText textTyped = (EditText) findViewById(R.id.editText7);
            String msg = textTyped.getText().toString();
            send_receive.add(new Message(msg, true));
            new myListAdapter().notifyDataSetChanged();
            textTyped.setText("");
        });

        //
        //receive
        btn1 = (Button) findViewById(R.id.button6);
        btn1.setOnClickListener((a) -> {
            EditText textTyped1 = (EditText) findViewById(R.id.editText7);
            String msg1 = textTyped1.getText().toString();
            send_receive.add(new  Message(msg1, false));
            //new myListAdapter1().notifyDataSetChanged();
            //BaseAdapter adapter1 = new myListAdapter1();
            //listview.setAdapter(new myListAdapter1());

            new myListAdapter().notifyDataSetChanged();
            //textTyped1.getText().clear();
            textTyped1.setText("");
        });

        listview.setAdapter(adapter);
        //

        listview.setLongClickable(true);

        listview.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(ChatRoomActivity.this)
                    .setTitle("Delete")
                    .setMessage("Do you " +
                            "want to delete this?" + "\nThe selected row is " + (position+1) + "\nThe database id is " + new myListAdapter().getItemId(position))
                    .setCancelable(true)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            send_receive.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .show();
            return true;
        });
        //listview.setAdapter(adapter);
    }

    class myListAdapter extends BaseAdapter {

        public int getCount() {
            return send_receive.size();
        }

        public Message getItem(int position) {
            return send_receive.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            Message m = getItem(position);
            View newView = inflater.inflate((m.isSent()) ? R.layout.sender : R.layout.receiver, parent, false);
            TextView textView = newView.findViewById(R.id.mess);
                textView.setText(m.getMessage());
                return newView;
        }

        public long getItemId(int position) {
            return (long) position;
        }
    }

}
