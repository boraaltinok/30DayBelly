package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class exerciseViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    int day_of_month = 0;
    TextView txt_currentExerciseName;
    ImageView img_currentExercise;
    User user;

    exerciseViewAdapter(Context context, int day_of_month)
    {
        this.context = context;
        loadData();
        this.day_of_month = day_of_month;
    }
    @Override
    public int getCount() {
        return user.month.get(day_of_month).exerciseProgramList.size() + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        view = layoutInflater.inflate(R.layout.current_exercise, null);
        txt_currentExerciseName = view.findViewById(R.id.txt_currentExerciseName);
        img_currentExercise = view.findViewById(R.id.img_currentExercise);

        txt_currentExerciseName.setText(user.month.get(day_of_month).exerciseProgramList.get(position).getName()+"");

        return view;
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);

        if(user == null)
        {
            user = new User();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.apply();
    }

    private void clearData()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}


