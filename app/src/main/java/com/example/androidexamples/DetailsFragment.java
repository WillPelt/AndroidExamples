package com.example.androidexamples;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {


    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;
    private String type;
    private String msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID );
        msg = dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED);
        type = dataFromActivity.getString(ChatRoomActivity.ITEM_TYPE);

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.msghere);
        message.setText(msg);

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.textid);
        idView.setText("ID=" + id);

        CheckBox cb = (CheckBox)result.findViewById(R.id.fragchck);
        cb.setChecked(type.equals("send"));
        Log.e("DetailsFragment", type);


        // get the delete button, and add a click listener:
       Button finishButton = (Button)result.findViewById(R.id.finishButton);
       finishButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
       });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}