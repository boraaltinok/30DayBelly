package com.example.a30daybellytransformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SelectedDaysProgram extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
    ImageButton btn_shuffle, btn_start, btn_pause;
    TextView shuffleCount;
    String exerciseName;
    ImageView doneCheck, img_currentExercise;
    int exerciseDuration, day_of_month;
    selected_day_adapter selectedDayAdapter;
    ArrayList<Integer> exerciseImages = new ArrayList<Integer>();
    int numberOfExercisesToday;
    long currentExerciseDuration;

    CountDownTimer timer;
    int exercisePosition = 0;
    Dialog dialog, dialog_shf;

    selected_day_adapter.MyViewHolder holder;


    boolean workoutStopped = false;
    long tmpMillisUntilFinished;

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
        shuffleCount = (TextView)findViewById(R.id.tv_shuffleCount);
        doneCheck = (ImageView)findViewById(R.id.img_dayDone);
        img_currentExercise=(ImageView)findViewById(R.id.img_currentExercise);
        dialog = new Dialog(this);
        dialog_shf = new Dialog(this);
        selectedDayAdapter = new selected_day_adapter(this, user, day_of_month, exerciseImages);
        recyclerView.setAdapter(selectedDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shuffle();
        startTodaysWorkout();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        recyclerView.setAnimation(animation);
        btn_pause.setVisibility(View.GONE);
        doneCheck.setVisibility(View.GONE);

        user.month.get(day_of_month -1).didNotStarted = true;



        if(user.month.get(day_of_month - 1).dayDone == true)
        {
            doneCheck.setVisibility(View.VISIBLE);
        }

        shuffleCount.setText(user.getShuffles() + "");

    }

    public void updateShuffleCountText()
    {
        shuffleCount.setText(user.getShuffles() + "");
    }

    public void startTodaysWorkout()
    {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.month.get(day_of_month -1).didNotStarted = false;
                saveData();
                loadData();
                btn_start.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
                workoutStopped = false;
                recursiveStartProgram();
            }
        });
    }

    /*
    *Function that detects the pause button click.
    * If user cliks pause function sets the workoutStopped variable to true
    * returns the value of workoutStopped
     */
    public boolean pauseTodaysWorkout()
    {
        workoutStopped = false;
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pause.setVisibility(View.GONE);
                workoutStopped = true;

                currentExerciseDuration = tmpMillisUntilFinished;
                user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).setLeftDuration(currentExerciseDuration);
                timer.cancel();
                holder.timer.cancel();
                Toast.makeText(SelectedDaysProgram.this, "workout stopped", Toast.LENGTH_SHORT).show();
                btn_start.setVisibility(View.VISIBLE);
            }
        });
        return workoutStopped;
    }


    /*
    *Function that starts the workout.
    * Finds the holder from adapter communicates with horizontal progress bar
    * Starts the workout
    * Has pause function in it to detect any pause clicks.
    *
     */

    /*
    onBackPressed
    *Ask the user if they want to quit or continue if back press interupts workout
     */
    @Override
    public void onBackPressed() {
        // if day exercises are finished just do the default back press action
        if(user.month.get(day_of_month - 1).dayDone == true || user.month.get(day_of_month -1).didNotStarted == true)
        {
            super.onBackPressed();
        }
        //super.onBackPressed();
        else {
        /*
        if workout is interupted with back press ask the user if they want to leave or continue
         */
            btn_pause.performClick();

            openQuitWorkoutDialog();
            /*AlertDialog.Builder builder = new AlertDialog.Builder(SelectedDaysProgram.this);
            builder.setTitle("DO YOU WANT TO STOP THE WORKOUT");
            builder.setIcon(android.R.drawable.ic_dialog_dialer);
            builder.setMessage("Do you want to stop the workout :(?");
            builder.setPositiveButton("Yes, i am tired!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), program_main.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No i am warrior! THAT WAS ACCIDENT!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(SelectedDaysProgram.this, "CONTINUES", Toast.LENGTH_SHORT).show();
                    btn_start.performClick();
                }
            });
            builder.show();*/
        }

    }

    public void recursiveStartProgram()
    {
        holder = (selected_day_adapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(exercisePosition);
        selectedDayAdapter.startCountdown(holder, exercisePosition, holder.pb_duration, workoutStopped);

        pauseTodaysWorkout();//sets workoutStopped to true and arranges the button views
        currentExerciseDuration = user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getLeftDuration();
        img_currentExercise.setImageDrawable(holder.exerciseIcon.getDrawable());

        timer = new CountDownTimer(currentExerciseDuration
                , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int leftSeconds =  (int)(millisUntilFinished/ 1000);
                tmpMillisUntilFinished = millisUntilFinished;
                if(leftSeconds < 10)
                {
                    holder.text_exerciseDuration.setText("00:0" + leftSeconds);
                }
                else
                {
                    holder.text_exerciseDuration.setText("00:"+leftSeconds);
                }
                if(leftSeconds == 0)
                {
                    showCheckedAnimation();
                }


                if(millisUntilFinished < 1000) // detects if workout duration is over in next onTick if so changes the current finished to true
                {
                    holder.currentExerciseFinished = true;
                }
                /*
                *if it stops cancels the countdown in this class
                * Cancels adapters(current exercise card) countdown
                 */
                if(workoutStopped)
                {
                    currentExerciseDuration = millisUntilFinished;
                    user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).setLeftDuration(currentExerciseDuration);
                    timer.cancel();
                    holder.timer.cancel();
                }
            }
            @Override
            public void onFinish() {
                Toast.makeText(SelectedDaysProgram.this, ""+holder.pb_duration.getProgress(), Toast.LENGTH_SHORT).show();
                cancel();

                /*
                *In order to start the new exercise conditions:
                * exercisePosition should not exceed or match programlist size
                * workoutStopped must be false(if it is true workout is paused at the moment)
                * currentExerciseFinished == true otherwise program would not wait until the exercise is over to move to next exercise
                 */
                if(exercisePosition < user.month.get(day_of_month-1).exerciseProgramList.size()-1
                &&workoutStopped == false && holder.currentExerciseFinished == true)
                {
                    exercisePosition++; // classes exercisePosition is updated, we will assign it to 0 when the workout is over.
                    recursiveStartProgram(); // calls the next startCountdown() for next exercise
                }
                else if(exercisePosition == user.month.get(day_of_month -1).exerciseProgramList.size()-1)// else means our workout is over we will prompt the user and set the exercisePosition to 0 and arrange buttons visibilities
                {
                    Toast.makeText(SelectedDaysProgram.this, " "+"exercisePos : " + exercisePosition
                            +"\nWorkout stopped" + workoutStopped + "\ncurrentExFinished" + holder.currentExerciseFinished, Toast.LENGTH_SHORT).show();
                    Toast.makeText(SelectedDaysProgram.this, "DAY" + day_of_month+ " completed", Toast.LENGTH_SHORT).show();
                    user.month.get(day_of_month - 1).dayDone = true;
                    saveData();
                    loadData();
                    exercisePosition = 0;
                    btn_pause.setVisibility(View.GONE);
                    btn_start.setVisibility(View.VISIBLE);
                    /*
                    *setting each exercise's leftDuration to their duration
                     */
                    for(int i = 0; i <  user.month.get(day_of_month - 1).exerciseProgramList.size(); i++)
                    {
                        user.month.get(day_of_month - 1).exerciseProgramList.get(i).resetLeftDuration();
                    }
                }
            }
        }.start();
    }

    public void shuffle(){
        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(user.shuffles <= 0)
                {
                    openShuffleDialog();
                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectedDaysProgram.this);
                    builder.setTitle("NO SHUFFLES LEFT");
                    builder.setIcon(android.R.drawable.ic_menu_add);
                    builder.setMessage("Ups. You ran out of shuffles. Want to buy more shuffles and spice your workout program up?");
                    builder.setPositiveButton("Yes, ı want varıety!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SelectedDaysProgram.this, "BOUGHT 2 SHUFFLES!", Toast.LENGTH_SHORT).show();
                            user.setShuffles(user.shuffles + 2);
                            updateShuffleCountText();
                        }
                    });
                    builder.setNegativeButton("No, fuck me in the ass pleasee.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SelectedDaysProgram.this, "DID NOT BUY", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                     */
                }
                if(user.getShuffles()>0) {
                    user.month.get(day_of_month-1).shuffleProgram();
                    user.shuffles = user.shuffles - 1;
                    updateShuffleCountText();
                    saveData();
                    loadData();
                    createImages();
                    exercisePosition = 0;
                    if (timer != null) {
                        timer.cancel();
                    }
                    btn_start.setVisibility(View.VISIBLE);
                    btn_pause.setVisibility(View.GONE);
                    recyclerView.setAdapter(new selected_day_adapter(SelectedDaysProgram.this, user, day_of_month, exerciseImages));
                    selectedDayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void openQuitWorkoutDialog()
    {
        dialog.setContentView(R.layout.stop_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewClose = dialog.findViewById(R.id.img_close);
        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        Button btn_quit = dialog.findViewById(R.id.btn_quit);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.performClick();
                dialog.dismiss();

            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), program_main.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openShuffleDialog()
    {
        dialog.setContentView(R.layout.shuffle_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewClose = dialog.findViewById(R.id.img_close);
        Button btn_buy = dialog.findViewById(R.id.btn_buy);
        Button btn_dontBuy = dialog.findViewById(R.id.btn_dontBuy);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setShuffles(user.shuffles + 2);
                updateShuffleCountText();
                saveData();
                loadData();
                dialog.dismiss();
            }
        });

        btn_dontBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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

    private void showCheckedAnimation()
    {
        holder.img_checked.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        holder.img_checked.setAnimation(animation);
    }

    private void getData()
    {

    }

    private void setData()
    {

    }
}
