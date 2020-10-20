package com.example.androidexamples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    public ArrayList<String> elements = new ArrayList<>();
    public ArrayList<String> senders = new ArrayList<>();
    ListAdapter la = new myListAdapter();

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

        myList.setOnItemLongClickListener( (parent, view, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The Selected Row is: "+pos+"\n"+"The database id: "+ id)
                    .setPositiveButton("Yes", (click, arg) -> {elements.remove(pos); senders.remove(pos); ((myListAdapter) la).notifyDataSetChanged(); })
                    .setNegativeButton("No", (click, arg) -> {  })
                    .create().show();

            return true ;

        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setType("send");
                EditText text = (EditText)findViewById(R.id.textGoesHere);
                message.setText(text.getText().toString());
                elements.add(message.getText());
                senders.add(message.getType());
                et.setText("");
                ((myListAdapter) la).notifyDataSetChanged();
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setType("receive");
                EditText text = (EditText)findViewById(R.id.textGoesHere);
                message.setText(text.getText().toString());
                elements.add(message.getText());
                senders.add(message.getType());
                et.setText("");
                ((myListAdapter) la).notifyDataSetChanged();
            }
        });




    }

    class myListAdapter extends BaseAdapter {


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView;

            if (senders.get(position).equals("receive")) {
                newView = inflater.inflate(R.layout.activity_chat_room_receive, parent, false);
                TextView tView = newView.findViewById(R.id.chatText);
                tView.setText(elements.get(position));
                ImageButton img = newView.findViewById(R.id.sender);
            }
            else{
                newView = inflater.inflate(R.layout.activity_chat_room_send, parent, false);
                TextView tView = newView.findViewById(R.id.chatText);
                tView.setText(elements.get(position));
                ImageButton img = newView.findViewById(R.id.sender);
            }
            return newView;

        }
    }

    static class message {

        private static String type = "";
        private static String text = "hi";

        public static void setText(String msg){
            text = msg;
        }
        public static String getText(){
            return text;
        }
        public static void setType(String tp){
            type = tp;
        }
        public static String getType(){
            return type;
        }

    }



}