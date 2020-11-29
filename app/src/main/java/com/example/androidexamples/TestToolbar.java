package com.example.androidexamples;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


//android:theme="@style/Theme.AndroidExamples"

public class TestToolbar extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView==null) {
            Log.e("TestToolbar", " NULL");
        }
            else{
                navigationView.setNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.candle:
                            Intent cr = new Intent(TestToolbar.this, ChatRoomActivity.class);
                            startActivity(cr);
                            break;
                        case R.id.penguin:
                            Intent wf = new Intent(TestToolbar.this, WeatherForecast.class);
                            startActivity(wf);
                            break;
                        case R.id.santa:
                            setResult(500);
                            finish();
                            break;
                    }
                    drawer.closeDrawer(GravityCompat.START);
                    return true;

                });
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.candle:
                Toast ctoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.candle_toast_message), Toast.LENGTH_SHORT);
                ctoast.show();
                break;
            case R.id.penguin:
                Toast ptoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.penguin_toast_message), Toast.LENGTH_SHORT);
                ptoast.show();
                break;
            case R.id.santa:
                Toast stoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.santa_toast_message), Toast.LENGTH_SHORT);
                stoast.show();
                break;
            case R.id.overflow:
                Toast otoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.overflow_toast_message), Toast.LENGTH_SHORT);
                otoast.show();
                break;
        }
        return true;
    }



}