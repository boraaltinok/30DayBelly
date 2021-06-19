package com.example.a30daybellytransformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

public class createUser extends AppCompatActivity {
    //decleration
    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        //initialzation
        slideViewPager = (ViewPager)findViewById(R.id.slideViewPager);
        dotsLayout = (LinearLayout)findViewById(R.id.dotsLayout);
    }
}
