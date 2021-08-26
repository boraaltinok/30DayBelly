package com.example.a30daybellytransformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AllExercises extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    all_exercises_adapter all_ex_adapter;
    User user;

    public Map<String, String> allExercisesMap;
    public static ArrayList<Integer> exerciseImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);
        loadData();
        putMap();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_allExercises);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.all_exercises);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.all_exercises:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0); // to make smooth transition
                        return true;
                    case R.id.program:
                        startActivity(new Intent(getApplicationContext(), program_main.class));
                        overridePendingTransition(0, 0); // to make smooth transition
                        return true;
                }
                return false;
            }
        });


        all_ex_adapter = new all_exercises_adapter(this, user, allExercisesMap);
        recyclerView.setAdapter(all_ex_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        recyclerView.setAnimation(animation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        if(recyclerView !=null){
            recyclerView.setAdapter(new all_exercises_adapter(AllExercises.this, user,allExercisesMap));
            all_ex_adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


    private void loadData()
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

    public void putMap(){

        allExercisesMap = new HashMap<>();
        allExercisesMap.put("","ERROR.");
        Scanner sc = null;
        String key = "";
        String value = "";

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("list_of_exercises.txt")));
            String line;
            Toast.makeText(this, "CRAZY!", Toast.LENGTH_SHORT).show();
            sc = new Scanner(reader);
            sc.useDelimiter(":");
            while (sc.hasNextLine()){
                key = sc.next();
                sc.skip(sc.delimiter());
                value = sc.nextLine();
                allExercisesMap.put(key,value);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
