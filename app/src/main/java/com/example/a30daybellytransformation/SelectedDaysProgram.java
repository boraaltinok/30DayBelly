package com.example.a30daybellytransformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SelectedDaysProgram extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    ImageButton btn_shuffle;
    String exerciseName;
    int exerciseDuration, day_of_month;
    selected_day_adapter selectedDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_days_program);

        loadData();
        day_of_month = getIntent().getIntExtra("day of month", 1);
        btn_shuffle = (ImageButton)findViewById(R.id.btn_shuffle);
        recyclerView = (RecyclerView)findViewById(R.id.exercise_recycler_view);
        selectedDayAdapter = new selected_day_adapter(this, user, day_of_month);
        recyclerView.setAdapter(selectedDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shuffle();

    }

    public void shuffle(){
        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                user.month.get(day_of_month-1).shuffleProgram();
                saveData();
                loadData();
                recyclerView.setAdapter(new selected_day_adapter(SelectedDaysProgram.this, user, day_of_month));
                selectedDayAdapter.notifyDataSetChanged();
            }
        });
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

    private void getData()
    {

    }

    private void setData()
    {

    }
}
