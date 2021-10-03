package com.example.a30daybellytransformation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a30daybellytransformation.Day;
import com.example.a30daybellytransformation.R;
import com.example.a30daybellytransformation.User;

import java.util.HashMap;
import java.util.Map;

public class all_exercises_adapter extends RecyclerView.Adapter<all_exercises_adapter.MyViewHolder> {

    Context context;
    User user;
    Day day = new Day();
    Map<String,String> allExercisesMap;



    public all_exercises_adapter(Context ct, User user, Map<String, String> allExercisesMap)
    {
        this.user = user;
        this.context = ct;
        this.allExercisesMap = new HashMap<>();
        this.allExercisesMap = allExercisesMap;
    }

    public void addExtendableInfo(final MyViewHolder holder)
    {

            holder.btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.expanded == false)
                    {
                        holder.expandableLayout.setVisibility(View.VISIBLE);
                        holder.expanded = true;
                    }
                    else if(holder.expanded == true)
                    {
                        holder.expandableLayout.setVisibility(View.GONE);
                        holder.expanded = false;
                    }

                }
            });
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.all_exercises_exercise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_exerciseName.setText(day.allExercisesList.get(position));

        holder.img_exercise.setImageResource(getImage(position));
        String exerciseUnderscore = day.allExercisesList.get(position).replaceAll(" ", "_").toLowerCase();
        String info = allExercisesMap.get(exerciseUnderscore);
        holder.txt_info.setText(info);
        holder.expandableLayout.setVisibility(View.GONE);
        addExtendableInfo(holder);


    }

    @Override
    public int getItemCount() {
        return day.allExercisesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_exerciseName, txt_info;
        ImageView img_exercise;
        ImageButton btn_info;
        boolean expanded = false;
        ConstraintLayout exerciseLayout, expandableLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_exerciseName = itemView.findViewById(R.id.txt_exerciseName);
            img_exercise = itemView.findViewById(R.id.img_exercise);
            btn_info = itemView.findViewById(R.id.btn_info);
            txt_info = itemView.findViewById(R.id.txt_exerciseInfo);
            exerciseLayout = itemView.findViewById(R.id.exerciseIcon);
            expandableLayout = itemView.findViewById(R.id.expandalbe_layout);
        }
    }

    private int getImage(int pos){
            String item = day.allExercisesList.get(pos);
            item = item.replaceAll(" ", "_").toLowerCase();
            int resID = context.getResources().getIdentifier(item, "drawable", context.getPackageName());
            return resID;
    }

}
