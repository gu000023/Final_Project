package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import static com.example.androidlabs.CreateDb.COL_ID;
import static com.example.androidlabs.CreateDb.COL_SEND;
import static com.example.androidlabs.CreateDb.COL_RECEIVE;

public class ChatRoomActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener {
    ArrayList<Message> send_receive = new ArrayList<>();
    //ArrayList<Object> receive = new ArrayList<>();
    private Button btn;
    private Button btn1;
    private BaseAdapter adapter = new myListAdapter();
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //lab7 below
        boolean isPhone=findViewById(R.id.fragmentLocation)==null;
        ListView theList=findViewById(R.id.lv);

        theList.setOnItemClickListener((list,view,position,id)->{
            if(isPhone){
                //Intent gotoFragment=new Intent(ChatRoomActivity.this,DetailsFragment.class);
                Intent gotoFragment=new Intent(ChatRoomActivity.this,EmptyActivity.class);
                startActivity(gotoFragment);
                //EmptyActivity parent = (EmptyActivity) getActivity();
                //Intent backToFragmentExample = new Intent();
                //backToFragmentExample.putExtra(FragmentExample.ITEM_ID, dataFromActivity.getLong(FragmentExample.ITEM_ID ));

                //parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                //parent.finish(); //go back
                Log.d("isphone","isphone");
            }else{
                Log.d("istab","istab");
                FragmentManager fm=getSupportFragmentManager();
                DetailsFragment parent= new DetailsFragment();
                fm.beginTransaction().replace(R.id.fragmentLocation,parent).commit();
            }
        });
        ListView listview = (ListView) findViewById(R.id.lv);
        //send
        btn = (Button) findViewById(R.id.button5);
        //receive
        btn1 = (Button) findViewById(R.id.button6);
        EditText textTyped = (EditText) findViewById(R.id.editText7);

        loadDataFromDatabase();
        listview.setAdapter(adapter);


        btn.setOnClickListener((v) -> {

            String msg = textTyped.getText().toString();
            //send_receive.add(new Message(msg, true));


            ContentValues newRowValues=new ContentValues();
            newRowValues.put(CreateDb.COL_SEND, msg);
            long newId=db.insert(CreateDb.TABLE_NAME,null, newRowValues);
            Message msgObj=new Message(msg,true,newId);
            send_receive.add(msgObj);

            new myListAdapter().notifyDataSetChanged();
            textTyped.setText("");
        });

        //

        btn1.setOnClickListener((a) -> {
            EditText textTyped1 = (EditText) findViewById(R.id.editText7);
            String msg1 = textTyped1.getText().toString();
            //send_receive.add(new  Message(msg1, false));
            //new myListAdapter1().notifyDataSetChanged();
            //BaseAdapter adapter1 = new myListAdapter1();
            //listview.setAdapter(new myListAdapter1());

            //new myListAdapter().notifyDataSetChanged();
            //textTyped1.getText().clear();


            ContentValues newRowValues=new ContentValues();
            newRowValues.put(CreateDb.COL_RECEIVE, msg1);
            long newId1=db.insert(CreateDb.TABLE_NAME,null, newRowValues);
            Message msgObj1=new Message(msg1,false,newId1);
            send_receive.add(msgObj1);
            new myListAdapter().notifyDataSetChanged();
            textTyped1.setText("");
        });

        listview.setAdapter(adapter);
        //

        listview.setLongClickable(true);

        listview.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(ChatRoomActivity.this)
                    .setTitle("Delete")
                    .setMessage("Do you " +
                            "want to delete this?" + "\nThe selected row is " + (position+1) + "\nThe database id is " + id/*new myListAdapter().getItemId(position)*/)
                    .setCancelable(true)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //
                            //deleteMessage(send_receive.get((int) new myListAdapter().getItemId(position)));
                            //deleteMessage(send_receive.get(position));
                            deleteMessage((int) id);
                            //
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
            return send_receive.get(position).getId();
        }
    }

    private void loadDataFromDatabase()
    {

        CreateDb cd = new CreateDb(this);
        db = cd.getWritableDatabase();
        //db.execSQL("delete from SQLITE_SEQUENCE WHERE NAME='CreateDb.TABLE_NAME'");
        String[] columns = {COL_ID, COL_SEND, COL_RECEIVE};
        Cursor results = db.query(false, CreateDb.TABLE_NAME, columns, null, null, null, null, null, null);

        int sendIndex = results.getColumnIndex(COL_SEND);
        int receiveIndex = results.getColumnIndex(COL_RECEIVE);
        int idIndex = results.getColumnIndex(COL_ID);

        while (results.moveToNext()) {
            String send = results.getString(sendIndex);
            String receive = results.getString(receiveIndex);
            long id = results.getLong(idIndex);

            //if(!send.equals("")) {
                //send_receive.add(new Message(send, true));
           // }else{
                send_receive.add(send==null?new Message(receive, false,id):new Message(send, true,id));

            //}

            //db.execSQL("delete from 'CreateDb.TABLE_NAME'");
            printCursor(results,db.getVersion());
        }

    }

   // protected void deleteMessage(Message c)
   // {
   //     db.delete(CreateDb.TABLE_NAME, CreateDb.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    //}
   protected void deleteMessage(int index)
   {
       db.delete(CreateDb.TABLE_NAME, CreateDb.COL_ID + "= ?", new String[] {String.valueOf(index)});

       //String triggerSql = "DROP TRIGGER IF EXISTS user_zero_as_first_rowid";
       //db.execSQL(triggerSql);

       //db.execSQL("DELETE FROM '"+CreateDb.TABLE_NAME+"'");
       //db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + CreateDb.TABLE_NAME + "'");
       //db.execSQL("UPDATE sqlite_sequence SET seq = "+index+"where name= 'CreateDb.TABLE_NAME'");
   }

   private void printCursor(Cursor c, int version){
        Log.d("db ver",String.valueOf(version));
        Log.d("col#",String.valueOf(c.getColumnCount()));
       for(String name:c.getColumnNames()) {
           Log.d("colName", name);
       }
       long count = DatabaseUtils.queryNumEntries(db, CreateDb.TABLE_NAME);
       Log.d("#results",String.valueOf(count));

       //while (c.moveToNext()) {
           Log.d("each row", c.getString(c.getColumnIndex(COL_ID))+" "+c.getString(c.getColumnIndex(COL_SEND)) + " " + c.getString(c.getColumnIndex(COL_RECEIVE)));

       //}
   }

}
