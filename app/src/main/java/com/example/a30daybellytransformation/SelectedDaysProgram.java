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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SelectedDaysProgram extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    ImageButton btn_shuffle, btn_start, btn_pause;
    String exerciseName;
    int exerciseDuration, day_of_month;
    selected_day_adapter selectedDayAdapter;
    ArrayList<Integer> exerciseImages = new ArrayList<Integer>();
    int numberOfExercisesToday;

    boolean workoutStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_days_program);

        loadData();
        day_of_month = getIntent().getIntExtra("day of month", 1);
        createImages();
        btn_shuffle = (ImageButton)findViewById(R.id.btn_shuffle);
        recyclerView = (RecyclerView)findViewById(R.id.exercise_recycler_view);
        btn_start = (ImageButton)findViewById(R.id.btn_start);
        btn_pause = (ImageButton)findViewById(R.id.btn_pause);
        selectedDayAdapter = new selected_day_adapter(this, user, day_of_month, exerciseImages);
        recyclerView.setAdapter(selectedDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shuffle();
        startTodaysWorkout();

        btn_pause.setVisibility(View.GONE);

    }

    public void startTodaysWorkout()
    {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);

                final int exercisePosition = 0;
                recursiveStartProgram(exercisePosition);


            }

        });


    }

    public boolean pauseTodaysWorkout(int pausedExercisePosition, final ProgressBar holdersProgressBar)
    {
        workoutStopped = false;
        Toast.makeText(this, "workout stopped " + workoutStopped, Toast.LENGTH_SHORT).show();
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pause.setVisibility(View.GONE);
                workoutStopped = true;
                Toast.makeText(SelectedDaysProgram.this, "workout stopped", Toast.LENGTH_SHORT).show();
                btn_start.setVisibility(View.VISIBLE);
            }
        });
        return workoutStopped;
    }

    public void recursiveStartProgram(final int exercisePosition)
    {
        //recyclerView.findViewHolderForAdapterPosition(exercisePosition).itemView.performClick();
        final selected_day_adapter.MyViewHolder holder = (selected_day_adapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(exercisePosition);
        selectedDayAdapter.startCountdown(holder, exercisePosition, holder.pb_duration, workoutStopped);
        //pauseTodaysWorkout to check if user clicked pause button
        pauseTodaysWorkout(exercisePosition, holder.pb_duration);
        new CountDownTimer(user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getDuration() * 1000
                , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(workoutStopped)
                {
                    holder.timer.cancel();
                }

            }
            @Override
            public void onFinish() {
                cancel();
                if(exercisePosition < user.month.get(day_of_month-1).exerciseProgramList.size()-1
                &&workoutStopped == false)
                {
                    int newParameter = exercisePosition+1;
                    recursiveStartProgram(newParameter);
                }
                else
                {
                    Toast.makeText(SelectedDaysProgram.this, "DAY" + day_of_month+ " completed", Toast.LENGTH_SHORT).show();
                    btn_start.setVisibility(View.VISIBLE);
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
                createImages();
                recyclerView.setAdapter(new selected_day_adapter(SelectedDaysProgram.this, user, day_of_month, exerciseImages));
                selectedDayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void createImages(){
        exerciseImages.clear();
        numberOfExercisesToday = user.month.get(day_of_month - 1).exerciseProgramList.size();
        for ( int i = 0; i < numberOfExercisesToday; i++){
            String item = (user.month.get(day_of_month - 1).exerciseProgramList.get(i).nameOfImage);
            int resID = getResources().getIdentifier(item , "drawable", getPackageName());
            exerciseImages.add(resID);

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

    private void getData()
    {

    }

    private void setData()
    {

    }
}
