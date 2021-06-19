package com.example.a30daybellytransformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Timer;

public class SelectedDaysProgram extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    EditText overall_counter;

    String exerciseName;
    int exerciseDuration, day_of_month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_days_program);

        loadData();
        day_of_month = getIntent().getIntExtra("day of month", 1);
        SeekBar seekBarExercise = (SeekBar)findViewById(R.id.seekBarExercise);
        recyclerView = (RecyclerView)findViewById(R.id.exercise_recycler_view);
        selected_day_adapter selectedDayAdapter = new selected_day_adapter(this, user, day_of_month);
        overall_counter = findViewById(R.id.overall_counter);
        overall_counter.setText("");
        recyclerView.setAdapter(selectedDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
