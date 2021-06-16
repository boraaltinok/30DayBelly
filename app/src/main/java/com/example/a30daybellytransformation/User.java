package com.example.a30daybellytransformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class User {
    //properties
    String name;
    int fitness_level; // 1 = low 2 = average 3 = high
    ArrayList<Day> month = new ArrayList<Day>();
    int default_volume;

    public User()
    {
        this.name = "default_name";
        int fitness_level = 1;

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public User(String name, int fitness_level) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.fitness_level = fitness_level;

        if(fitness_level == 1)
        {
            default_volume = 200;
            for(int i = 1; i < 31; i++)
            {
                Day day = new Day(i, default_volume);

                month.add(day);
                //month.get(i).printTodaysExercises();
            }

        }
        if(fitness_level == 2)
        {
            default_volume = 300;
            for(int i = 1; i < 31; i++)
            {
                Day day = new Day(i, default_volume);

                month.add(day);
                //month.get(i).printTodaysExercises();
            }

        }
        else if(fitness_level == 3)
        {
            default_volume = 400;
            for(int i = 1; i < 31; i++)
            {
                Day day = new Day(i, default_volume);

                month.add(day);
                //month.get(i).printTodaysExercises();
            }

        }
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
