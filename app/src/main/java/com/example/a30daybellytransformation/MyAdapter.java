package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    User user;
    Context context;
    int[] days;


    public MyAdapter(Context ct, User user)
    {
        this.user = user;
        this.context = ct;

        days =  new int[user.month.size()];

        for(int i = 0; i < user.month.size(); i++)
        {
            days[i] = i + 1; // 1 2 3 4 5 6 .....  month.size() + 1 which is 30;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_day_of_month.setText(days[position] + ". DAY");
        holder.text_exerciseCount.setText(user.month.get(position).exerciseProgramList.size() + "exercises");

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectedDaysProgram.class);
                intent.putExtra("exercise count", user.month.get(position).exerciseProgramList.size());
                intent.putExtra("day of month", days[position]);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_day_of_month, text_exerciseCount;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_day_of_month = itemView.findViewById(R.id.tv_day);
            text_exerciseCount = itemView.findViewById(R.id.tv_exerciseCount);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
