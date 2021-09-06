package com.example.a30daybellytransformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab_buy_shuffles;
    CollapsingToolbarLayout ctbl;
    User user;
    ImageButton btn_back;
    TextInputEditText txt_name, txt_fitnesslevel, txt_height, txt_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadData();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        ctbl = findViewById(R.id.collapsing_toolbar);
        fab_buy_shuffles = findViewById(R.id.fab_buy_shuffles);

        txt_name = findViewById(R.id.txt_name);
        txt_fitnesslevel = findViewById(R.id.txt_fitnessLevel1);
        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_initialweight);

        fillProfile();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.all_exercises:
                        startActivity(new Intent(getApplicationContext(), AllExercises.class));
                        overridePendingTransition(0, 0); // to make smooth transition
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.program:
                        startActivity(new Intent(getApplicationContext(), program_main.class));
                        overridePendingTransition(0, 0); // to make smooth transition
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), program_main.class));
        overridePendingTransition(0, 0); // to make smooth transition

    }
    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.apply();
    }


    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);

        if(user == null)
        {
            user = new User();
            Toast.makeText(this, "HERE", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void fillProfile()
    {
        ctbl.setTitle((user.getName()).toUpperCase()+ "");

        txt_name.setText(user.getName());
        txt_height.setText(user.getHeight() + "");
        txt_weight.setText(user.getWeight() + "");



        switch(user.fitness_level){
            case 1:
                txt_fitnesslevel.append(" BEGINNER");
                break;
            case 2:
                txt_fitnesslevel.append(" INTERMEDIATE");
                break;
            case 3:
                txt_fitnesslevel.append(" ADVANCED");
                break;
        }
    }
}
