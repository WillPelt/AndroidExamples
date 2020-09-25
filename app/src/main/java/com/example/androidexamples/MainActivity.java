package com.example.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);



        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_message), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        Switch switcher = findViewById(R.id.switcher);
        TextView myTextView = findViewById(R.id.switcher);

        switcher.setOnCheckedChangeListener( (whatClicked, newState) -> {
            Snackbar.make(myTextView, "The switch is now " + newState, Snackbar.LENGTH_SHORT).setAction( "Undo", click -> switcher.setChecked(!newState)).show();

        });

    }

}



