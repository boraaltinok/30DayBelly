package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
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
    TextView text_name, text_fitness_level, text_weight, text_levelStatus;
    NumberPicker height_picker;
    SeekBar sb_fitnessLevel;
    ImageView emoji1, emoji2, emoji3;


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
            if(TextUtils.isEmpty(text_name.getText().toString())) {
                text_name.setError("Your message");
                //return;
            }
            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }
        else if(position == 1)
        {
            final Animation expandIn = AnimationUtils.loadAnimation(this.context, R.anim.expand_in);
            final Animation collapseIn = AnimationUtils.loadAnimation(this.context, R.anim.collapse_in);
            view = layoutInflater.inflate(R.layout.get_fitness_level, null);
            sb_fitnessLevel = view.findViewById(R.id.sb_fitnessLevel);
            text_levelStatus = view.findViewById(R.id.tv_levelStatus);
            emoji1 = view.findViewById(R.id.ic_emoji1);
            emoji2 = view.findViewById(R.id.ic_emoji2);
            emoji3 = view.findViewById(R.id.ic_emoji3);
            sb_fitnessLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int i = 0;
                int level = 1;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    i = progress;
                    if ( i < 33){

                        text_levelStatus.setText("I am a beginner");
                    }

                    else if ( i >= 33 && i < 70){

                        level = 2;


                    }

                    else{
                        level = 3;
                        text_levelStatus.setText("I am advanced");
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {


                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if ( i < 33){
                        level = 1;
                        text_levelStatus.setText("I am a beginner");
                        emoji1.setImageResource(R.drawable.sad_emoji);
                        emoji1.startAnimation(expandIn);
                        emoji2.startAnimation(collapseIn);
                        emoji3.startAnimation(collapseIn);
                        emoji2.setImageResource(R.drawable.blank);
                        emoji2.setBackgroundColor(Color.TRANSPARENT);
                        emoji3.setImageResource(R.drawable.blank);
                        emoji3.setBackgroundColor(Color.TRANSPARENT);
                    }

                    else if ( i >= 33 && i < 70){

                        level = 2;
                        text_levelStatus.setText("I am intermediate");
                        emoji2.setImageResource(R.drawable.heart_emoji);
                        emoji2.startAnimation(expandIn);
                        emoji1.startAnimation(collapseIn);
                        emoji3.startAnimation(collapseIn);
                        emoji1.setImageResource(R.drawable.blank);
                        emoji1.setBackgroundColor(Color.TRANSPARENT);
                        emoji3.setImageResource(R.drawable.blank);
                        emoji3.setBackgroundColor(Color.TRANSPARENT);

                    }

                    else{
                        level = 3;
                        text_levelStatus.setText("I am advanced");
                        emoji3.setImageResource(R.drawable.money_emoji);
                        emoji3.startAnimation(expandIn);
                        emoji1.startAnimation(collapseIn);
                        emoji2.startAnimation(collapseIn);
                        emoji1.setImageResource(R.drawable.blank);
                        emoji1.setBackgroundColor(Color.TRANSPARENT);
                        emoji2.setImageResource(R.drawable.blank);
                        emoji2.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });

            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }
        else if(position == 2)
        {
            view = layoutInflater.inflate(R.layout.get_height, null);
            height_picker = view.findViewById(R.id.height_picker);
            height_picker.setMinValue(135);
            height_picker.setMaxValue(210);


            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
        }

        else if(position ==3)
        {
            view = layoutInflater.inflate(R.layout.get_weight, null);
            text_weight = view.findViewById(R.id.text_weight);

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

                    user = new User(text_name.getText().toString(), getLevelFromProgress(sb_fitnessLevel.getProgress()), height_picker.getValue(), Double.parseDouble(text_weight.getText().toString()));

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

    private int getLevelFromProgress( int progress){
        if ( progress < 33){
            return 1;
        }

        else if ( progress >= 33 && progress < 70){
            return 2;
        }

        else
            return 3;
    }

}
