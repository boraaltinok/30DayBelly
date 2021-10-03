package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Objects;

class ViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    User user = null;
    TextView text_name, text_fitness_level, text_weight, text_levelStatus;
    Button btn_next;
    NumberPicker height_picker;
    SeekBar sb_fitnessLevel;
    ImageView emoji1, emoji2, emoji3;
    boolean validName = false;


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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if(position == 0)
        {
            view = layoutInflater.inflate(R.layout.get_name, null);
            text_name = view.findViewById(R.id.txt_name);
            btn_next = view.findViewById(R.id.btn_next);
            final ViewPager viewPager = (ViewPager) container;
            text_name.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(TextUtils.isEmpty(s))
                    {
                        btn_next.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_btn_not_entered));
                        validName = false;
                    }
                    else
                    {
                        btn_next.setBackground(ContextCompat.getDrawable(context, R.drawable.close_btn_background));
                        validName = true;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            



            btn_next.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                   if(validName)
                   {
                       viewPager.setCurrentItem(position + 1);
                   }
                }
            });



            viewPager.addView(view, 0);
        }
        else if(position == 1)
        {
            final Animation expandIn = AnimationUtils.loadAnimation(this.context, R.anim.expand_in);
            final Animation collapseIn = AnimationUtils.loadAnimation(this.context, R.anim.collapse_in);
            view = layoutInflater.inflate(R.layout.get_fitness_level, null);
            Button btn_next_pos1 = view.findViewById(R.id.btn_next);

            sb_fitnessLevel = view.findViewById(R.id.sb_fitnessLevel);
            text_levelStatus = view.findViewById(R.id.tv_levelStatus);
            emoji1 = view.findViewById(R.id.ic_emoji1);
            emoji2 = view.findViewById(R.id.ic_emoji2);
            emoji3 = view.findViewById(R.id.ic_emoji3);
            sb_fitnessLevel.setProgress(50);
            emoji1.setImageResource(R.drawable.blank);
            emoji1.setBackgroundColor(Color.TRANSPARENT);
            emoji3.setImageResource(R.drawable.blank);
            emoji3.setBackgroundColor(Color.TRANSPARENT);
            sb_fitnessLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int i = 0;
                int level = 1;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    i = progress;
                    if (i < 33) {

                        text_levelStatus.setText("I am a beginner");


                    }
                    else if (i >= 33 && i < 70) {

                        level = 2;
                        text_levelStatus.setText("I am intermediate");


                    }
                    else {
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

            final ViewPager viewPager = (ViewPager) container;
            btn_next_pos1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(position + 1);
                }
            });
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

            final ViewPager viewPager = (ViewPager) container;

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    if(position == 3 && (TextUtils.isEmpty(text_weight.getText().toString())))
                    {
                        text_weight.setError("ENTER YOUR WEIGHT PLEASE");
                        viewPager.setCurrentItem(3);

                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
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

    /*
    return 1/2 means 2 fragments in one screen 1/3 means 3 fragments in one screen etc.
     */
    @Override
    public float getPageWidth(int position) {
        float nbPages = 10; // You could display partial pages using a float value
        return (1);
    }

    void checkInfoValid(final Button btn_next, TextView et_name)
    {
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.equals(""))
                {
                    btn_next.setBackground(ContextCompat.getDrawable(context, R.drawable.close_btn_background));
                }
                else
                {
                    btn_next.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_btn_not_entered));
                }
            }
        });
    }

}
