package com.example.a30daybellytransformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SelectedDaysProgram extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    ImageButton btn_shuffle, btn_start;
    String exerciseName;
    int exerciseDuration, day_of_month;
    selected_day_adapter selectedDayAdapter;
    ArrayList<Integer> exerciseImages = new ArrayList<Integer>();
    int numberOfExercisesToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_days_program);

        loadData();
        day_of_month = getIntent().getIntExtra("day of month", 1);
        numberOfExercisesToday = user.month.get(day_of_month - 1).exerciseProgramList.size();
        for ( int i = 0; i < numberOfExercisesToday; i++){
            String item = (user.month.get(day_of_month - 1).exerciseProgramList.get(i).nameOfImage);
            int resID = getResources().getIdentifier(item , "drawable", getPackageName());
            exerciseImages.add(resID);

        }
        btn_shuffle = (ImageButton)findViewById(R.id.btn_shuffle);
        recyclerView = (RecyclerView)findViewById(R.id.exercise_recycler_view);
        btn_start = (ImageButton)findViewById(R.id.btn_start);
        selectedDayAdapter = new selected_day_adapter(this, user, day_of_month);
        recyclerView.setAdapter(selectedDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shuffle();
        startTodaysWorkout();

    }

    public void startTodaysWorkout()
    {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.GONE);

                final int exercisePosition = 0;
                recursiveStartProgram(exercisePosition);


            }
        });

    }

    public void recursiveStartProgram(final int exercisePosition)
    {
        //recyclerView.findViewHolderForAdapterPosition(exercisePosition).itemView.performClick();
        selected_day_adapter.MyViewHolder holder = (selected_day_adapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(exercisePosition);
        selectedDayAdapter.startCountdown(holder, exercisePosition, holder.pb_duration);
        new CountDownTimer(user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getDuration() * 1000
                , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                cancel();
                if(exercisePosition < user.month.get(day_of_month-1).exerciseProgramList.size()-1)
                {
                    int newParameter = exercisePosition+1;
                    recursiveStartProgram(newParameter);
                }
                else
                {
                    Toast.makeText(SelectedDaysProgram.this, "DAY" + day_of_month+ " completed", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
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
