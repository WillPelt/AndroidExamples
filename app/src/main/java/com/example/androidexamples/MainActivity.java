package com.example.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_linear);

        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String email = prefs.getString("email", "");
        EditText typeField = findViewById(R.id.email);
        typeField.setText(email);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_message), Toast.LENGTH_LONG);
                toast.show(); }
        });

        Switch switcher = findViewById(R.id.switcher);
        TextView myTextView = findViewById(R.id.switcher);
        switcher.setOnCheckedChangeListener( (whatClicked, newState) -> {
            Snackbar.make(myTextView,getResources().getString(R.string.switch_message) + newState, Snackbar.LENGTH_SHORT).setAction( "Undo", click -> switcher.setChecked(!newState)).show();
        });

        final Button login = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText typeField = findViewById(R.id.email);
        saveSharedPrefs(typeField.getText().toString());
    }

    private void saveSharedPrefs(String stringToSave){
        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", stringToSave);
        editor.commit();
    }

}



