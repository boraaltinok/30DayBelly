package com.example.a30daybellytransformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class program_main extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    int[] imagesOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_main);

        loadData();
        //imagesOfDays = { R.drawable}
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        myAdapter = new MyAdapter(this, user);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        recyclerView.setAnimation(animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        if(recyclerView !=null){
            recyclerView.setAdapter(new MyAdapter(program_main.this, user));
            myAdapter.notifyDataSetChanged();
        }
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

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.apply();
    }

    private void clearData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
