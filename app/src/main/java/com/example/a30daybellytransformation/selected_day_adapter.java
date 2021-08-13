package com.example.a30daybellytransformation;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class selected_day_adapter extends RecyclerView.Adapter<selected_day_adapter.MyViewHolder> {
    User user;
    Context context;
    int day_of_month;
    Exercise[] days_exercises;
    int[] todaysExerciseImages;
    Timer t;
    int counter = 0;


    public selected_day_adapter(Context ct, User user, int day_of_month, ArrayList<Integer> list)
    {
        int sizeOfArray = list.size();
        todaysExerciseImages = new int[ sizeOfArray];
        for ( int i = 0; i < sizeOfArray; i++)
            todaysExerciseImages[i] = list.get(i);
        this.user = user;
        this.context = ct;
        this.day_of_month = day_of_month;
        days_exercises = new Exercise[user.month.get(day_of_month - 1).exerciseProgramList.size()];
        for(int i = 0; i < user.month.get(day_of_month - 1).exerciseProgramList.size(); i++)
        {
            days_exercises[i] =  user.month.get(day_of_month-1).exerciseProgramList.get(i);
        }

    }

    public void startCountdown(final MyViewHolder holder, final int exercisePosition, final ProgressBar pb, final boolean workoutStopped)
    {


            pb.setMax(user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getDuration()* 10 * 2);// to avoid lag
            holder.currentExerciseDuration = user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getLeftDuration();
            holder.timer =  new CountDownTimer(holder.currentExerciseDuration, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(holder.timer_counter == pb.getMax())
                {
                    holder.currentExerciseFinished = true;
                }
                if(!workoutStopped)
                {
                    pb.setProgress(holder.timer_counter);
                    holder.timer_counter++;
                }
                else if(workoutStopped)
                {
                    holder.currentExerciseDuration = millisUntilFinished;
                }
            }

            @Override
            public void onFinish() {
                counter = 0;
                holder.timer_counter = 0;
                cancel();
            }
        }.start();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_exercise_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.exerciseIcon.setImageResource(todaysExerciseImages[position]);

        holder.text_exerciseName.setText("" + days_exercises[position].getName());
        holder.text_exerciseDuration.setText("00:"+user.month.get(day_of_month-1).exerciseProgramList.get(position).getDuration());
        holder.img_checked.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return user.month.get(day_of_month-1).exerciseProgramList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_exerciseName, text_exerciseDuration;
        ProgressBar pb_duration;
        CountDownTimer timer;
        ImageView exerciseIcon, img_checked;
        int timer_counter = 0;
        ConstraintLayout mainExerciseLayout;
        boolean currentExerciseFinished = false;
        long currentExerciseDuration;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseIcon = itemView.findViewById(R.id.exerciseIcon);
            text_exerciseName = itemView.findViewById(R.id.tv_exerciseName);
            text_exerciseDuration = itemView.findViewById(R.id.tv_exerciseDuration);
            pb_duration = itemView.findViewById(R.id.pb_duration);
            img_checked = itemView.findViewById(R.id.img_checked);
            mainExerciseLayout = itemView.findViewById(R.id.mainExerciseLayout);

        }
    }

    private void seekBarHide(){

    }
}
