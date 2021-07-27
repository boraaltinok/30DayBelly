package com.example.a30daybellytransformation;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

public class selected_day_adapter extends RecyclerView.Adapter<selected_day_adapter.MyViewHolder> {
    User user;
    Context context;
    int day_of_month;
    Exercise[] days_exercises;
    Timer t;
    int counter = 0;


    public selected_day_adapter(Context ct, User user, int day_of_month)
    {
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


            pb.setMax(user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getDuration());
            holder.timer =  new CountDownTimer(user.month.get(day_of_month - 1).exerciseProgramList.get(exercisePosition).getDuration() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                pb.setProgress(holder.timer_counter);
                holder.timer_counter++;
            }

            @Override
            public void onFinish() {
                cancel();
                //counter = 0;
                //holder.timer_counter = 0;
                //cancel();
                /*if(exercisePosition + 1 < user.month.get(day_of_month - 1).exerciseProgramList.size())
                startCountdown(holder, exercisePosition+1, pb);*/
            }
        }.start();
        /*final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if(counter == 1000)
                    t.cancel();
            }
        };
        t.schedule(tt, 0, 100);*/
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
        holder.text_exerciseName.setText("" + days_exercises[position].getName());
        holder.text_exerciseDuration.setText(""+user.month.get(day_of_month-1).exerciseProgramList.get(position).getDuration());
        holder.sb_seekBarExercise.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {
        return user.month.get(day_of_month-1).exerciseProgramList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_exerciseName, text_exerciseDuration;
        SeekBar sb_seekBarExercise;
        ProgressBar pb_duration;
        Button btn_magic;
        CountDownTimer timer;
        int timer_counter = 0;
        ConstraintLayout mainExerciseLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_exerciseName = itemView.findViewById(R.id.tv_exerciseName);
            text_exerciseDuration = itemView.findViewById(R.id.tv_exerciseDuration);
            sb_seekBarExercise = itemView.findViewById((R.id.seekBarExercise));
            pb_duration = itemView.findViewById(R.id.pb_duration);
            mainExerciseLayout = itemView.findViewById(R.id.mainExerciseLayout);

        }
    }

    private void seekBarHide(){

    }
}
