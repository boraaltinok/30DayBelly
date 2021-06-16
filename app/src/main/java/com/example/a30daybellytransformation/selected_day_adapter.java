package com.example.a30daybellytransformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class selected_day_adapter extends RecyclerView.Adapter<selected_day_adapter.MyViewHolder> {
    User user;
    Context context;
    int day_of_month;
    Exercise[] days_exercises;

    public selected_day_adapter(Context ct, User user, int day_of_month)
    {
        this.user = user;
        this.context = ct;
        this.day_of_month = day_of_month;
        days_exercises = new Exercise[user.month.get(day_of_month).exerciseProgramList.size()];
        for(int i = 0; i < user.month.get(day_of_month).exerciseProgramList.size(); i++)
        {
            days_exercises[i] =  user.month.get(day_of_month).exerciseProgramList.get(i);
        }

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_exercise_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_exerciseName.setText(""+user.month.get(position).exerciseProgramList.get(position).getName());
        holder.text_exerciseDuration.setText(""+user.month.get(position).exerciseProgramList.get(position).getDuration());
    }


    @Override
    public int getItemCount() {
        return user.month.get(day_of_month).exerciseProgramList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_exerciseName, text_exerciseDuration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_exerciseName = itemView.findViewById(R.id.tv_exerciseName);
            text_exerciseDuration = itemView.findViewById(R.id.tv_exerciseDuration);

        }
    }
}
