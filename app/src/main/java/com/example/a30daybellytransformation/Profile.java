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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView profilePicture;
    FloatingActionButton fab_buy_shuffles;
    CollapsingToolbarLayout ctbl;
    TextView userName, txt_fitnessLevel;
    User user;
    ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadData();

        userName = findViewById(R.id.txt_userName);
        txt_fitnessLevel = findViewById(R.id.txt_fitnessLevel);
        profilePicture = findViewById(R.id.profile_img);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        ctbl = findViewById(R.id.collapsing_toolbar);
        fab_buy_shuffles = findViewById(R.id.fab_buy_shuffles);

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

        userName.setText((user.getName()).toUpperCase()+ "");
        ctbl.setTitle((user.getName()).toUpperCase()+ "");

        switch(user.fitness_level){
            case 1:
                txt_fitnessLevel.append(" BEGINNER");
                break;
            case 2:
                txt_fitnessLevel.append(" INTERMEDIATE");
                break;
            case 3:
                txt_fitnessLevel.append(" ADVANCED");
                break;
        }
    }
}
