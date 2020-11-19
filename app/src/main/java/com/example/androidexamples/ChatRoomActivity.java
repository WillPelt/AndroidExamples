package com.example.androidexamples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {


    public ArrayList<Message> messages = new ArrayList<>();
    ListAdapter la = new myListAdapter();
    SQLiteDatabase db;

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_TYPE = "TYPE";
    public DetailsFragment dFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

       final ListView myList = (ListView) findViewById(R.id.lv);
        myList.setItemsCanFocus(false);
        myList.setAdapter(la);
        EditText et = findViewById(R.id.textGoesHere);
        Button send = findViewById(R.id.send);
        Button receive = findViewById(R.id.recieve);
        Boolean isTablet =  findViewById(R.id.fltablet) != null;




        myList.setOnItemClickListener( (list, view, pos, id) -> {
                    //Create a bundle to pass data to the new fragment
                    Bundle dataToPass = new Bundle();
                    dataToPass.putString(ITEM_SELECTED, messages.get(pos).getText() );
                    Log.e("ChatRoomActivity", "Text: " +messages.get(pos).getText());
                    dataToPass.putInt(ITEM_POSITION, pos);
                    dataToPass.putLong(ITEM_ID, id);
                    dataToPass.putString(ITEM_TYPE, messages.get(pos).getType());
                    Log.e("ChatRoomActivity", "Type: " + messages.get(pos).getType());
                     Log.e("ChatRoomActivity", "Tablet? " + isTablet);
            if(isTablet)
                    {
                        dFragment = new DetailsFragment(); //add a DetailFragment
                        dFragment.setArguments( dataToPass ); //pass it a bundle for information
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fltablet, dFragment) //Add the fragment in FrameLayout
                                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
                    }
                    else //isPhone
                    {
                        Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                        nextActivity.putExtras(dataToPass); //send data to next activity
                        startActivity(nextActivity); //make the transition
                    }


                });

        myList.setOnItemLongClickListener( (parent, view, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The Selected Row is: "+pos+"\n"+"The database id: "+ la.getItemId(pos))
                    .setPositiveButton("Yes", (click, arg) -> { db.delete(MyOpener.TABLE_NAME,MyOpener.COL_ID + "= ?", new String[] {Long.toString(la.getItemId(pos))});messages.remove(pos);((myListAdapter) la).notifyDataSetChanged();
                        if (isTablet){
                            getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
                        }})
                    .setNegativeButton("No", (click, arg) -> {  })
                    .create().show();

            return true ;

        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text = (EditText)findViewById(R.id.textGoesHere);
                ContentValues newRowValues = new ContentValues();

                newRowValues.put(MyOpener.COL_MESSAGE, text.getText().toString() );
                newRowValues.put(MyOpener.COL_TYPE, "send");

                long newID = db.insert(MyOpener.TABLE_NAME, MyOpener.COL_MESSAGE, newRowValues);
                messages.add(new Message("send", text.getText().toString(), newID ));
                et.setText("");
                ((myListAdapter) la).notifyDataSetChanged();
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.textGoesHere);
                ContentValues newRowValues = new ContentValues();

                newRowValues.put(MyOpener.COL_MESSAGE, text.getText().toString() );
                newRowValues.put(MyOpener.COL_TYPE, "receive");
                long newID = db.insert(MyOpener.TABLE_NAME, MyOpener.COL_MESSAGE, newRowValues);

                messages.add(new Message("receive", text.getText().toString(), newID));
                et.setText("");
                ((myListAdapter) la).notifyDataSetChanged();
            }
        });

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

            String [] columns = {MyOpener.COL_ID, MyOpener.COL_TYPE, MyOpener.COL_MESSAGE};

            Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);



            int typeColumnIndex = results.getColumnIndex(MyOpener.COL_TYPE);
            int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
            int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

            while (results.moveToNext()) { //starts at -1
                String type = results.getString(typeColumnIndex);
                String message = results.getString(messageColumnIndex);
                long id = results.getLong(idColIndex);
                messages.add(new Message(type, message, id));

            }

        printCursor(results, db.getVersion());

    }

    class myListAdapter extends BaseAdapter {


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return messages.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView;

            if (messages.get(position).getType().equals("receive")) {
                newView = inflater.inflate(R.layout.activity_chat_room_receive, parent, false);
                TextView tView = newView.findViewById(R.id.chatText);
                tView.setText(messages.get(position).getText());
                ImageButton img = newView.findViewById(R.id.sender);
            }
            else{
                newView = inflater.inflate(R.layout.activity_chat_room_send, parent, false);
                TextView tView = newView.findViewById(R.id.chatText);
                tView.setText(messages.get(position).getText());
                ImageButton img = newView.findViewById(R.id.sender);
            }
            return newView;

        }
    }

    class Message {

        private String type = "";
        private String message = "";
        private long id;

        public Message(String type, String msg, long id){
            setType(type);
            setText(msg);
            setId(id);
        }

        public void setText(String msg){ message = msg; }
        public String getText(){ return message; }
        public void setType(String tp){type = tp; }
        public  String getType(){ return type; }
        public void setId(long ID){id = ID;}
        public long getId(){return id;}

    }
    public class MyOpener extends SQLiteOpenHelper{

        protected final static String DATABASE_NAME = "MessagesDB";
        protected final static int VERSION_NUM = 1;
        public final static String TABLE_NAME="MESSAGES";
        public final static String COL_TYPE="TYPE";
        public final static String COL_MESSAGE="MESSAGE";
        public final static String COL_ID= "_id";

        public MyOpener(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+ " ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TYPE + " text," + COL_MESSAGE + " text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);


        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
        }
    }

    public void printCursor(Cursor c, int version) {
        c.moveToFirst();
        c.moveToPrevious();
        Log.d("CHAT_ROOM_ACTIVITY", "Version Number: " + version);
        Log.d("CHAT_ROOM_ACTIVITY", "Column Count: " + c.getColumnCount());
        for (String column : c.getColumnNames()) {
            Log.d("CHAT_ROOM_ACTIVITY", "Column Name: " + column);
        }
        Log.d("CHAT_ROOM_ACTIVITY", "Row Count: " + c.getCount());

        for (int x = 0; x<c.getCount(); x++) {
            c.moveToNext();
            Log.d("CHAT_ROOM_ACTIVITY", "ID: " + c.getString(0) + " TYPE: " + c.getString(1) + " MESSAGE: " + c.getString(2));
        }
        c.moveToFirst();
        c.moveToPrevious();
        }


}