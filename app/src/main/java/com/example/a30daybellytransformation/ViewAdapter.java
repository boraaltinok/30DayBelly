package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

class ViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    User user = null;
    TextView text_name, text_fitness_level;

    ViewAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount() {
        return 5;
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
        if(position == 0)
        {
            view = layoutInflater.inflate(R.layout.get_name, null);
            text_name = view.findViewById(R.id.txt_name);
            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }
        else if(position == 1)
        {
            view = layoutInflater.inflate(R.layout.get_fitness_level, null);
            text_fitness_level = view.findViewById(R.id.text_fitness_level);
            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }
        else if(position == 4)
        {
            view = layoutInflater.inflate(R.layout.create_program, null);
            Button btn_createProgram = view.findViewById(R.id.btn_createProgram);

            btn_createProgram.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    user = new User(text_name.getText().toString(), Integer.parseInt(text_fitness_level.getText().toString()));
                    saveData();
                    Intent intent = new Intent(context.getApplicationContext(), program_main.class);
                    context.startActivity(intent);

                }

            });
            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
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
