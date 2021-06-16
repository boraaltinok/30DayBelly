package com.example.a30daybellytransformation;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Day {
    //properties
    int totalVolume;
    int day_of_month;
    int default_volume;
    int selected_exercise_volume;


    String[] tier1 = {"jump squats", "squats", "vertical jumps", "mountain climbers", "burpees" };
    String[] tier2 = {"crunches", "planks", "russian twists", "leg raises", "side planks"};
    String[] tier3 = {"plank jack", "high to low plank", "high knees", "jumping lunge", "jump rope"};
    String[] tier4 = {"skaters", "walkout", "hip thrusts", "back extenisons", "jumping jacks"};
    String[][] tiers = new String[4][5];

    ArrayList<Exercise> exerciseProgramList = new ArrayList<Exercise>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Day(int day_of_month, int default_volume) {
        // TODO Auto-generated constructor stub
        //adding all exercises to 2d tiers array
        tiers[0] = tier1;
        tiers[1] = tier2;
        tiers[2] = tier3;
        tiers[3] = tier4;
        this.day_of_month = day_of_month;
        this.default_volume = default_volume;
        calculateTotalVolume(day_of_month); // calculates todays volume depending on day_of_month
        createProgram(tiers);



    }
    public void setDay_of_month(int day_of_month)
    {
        this.day_of_month = day_of_month;
    }
    public int getDay_of_month()
    {
        return this.day_of_month;
    }

    /*
     * FUNCTION THAT CHANGES totalVolume property of the class depending on day_of_month
     * Day0 to Day4 ------> same with default volume
     * Day5 to Day9 ------> default volume + 60
     * Day10 to Day14 ------> default volume + 120
     * Day15 to Day19 ------> default volume + 180
     * Day20 to Day24 ------> default volume + 240
     * Day25 to Day30 ------> default volume + 300
     *
     */
    public void calculateTotalVolume(int day_of_month)
    {
        if(day_of_month < 5)
        {
            this.totalVolume = this.default_volume;
        }
        else if(5 <= day_of_month && day_of_month <10)
        {
            this.totalVolume = this.default_volume + 60;
        }
        else if(10 <= day_of_month && day_of_month <15)
        {
            this.totalVolume = this.default_volume + 120;
        }
        else if(15 <= day_of_month && day_of_month <20)
        {
            this.totalVolume = this.default_volume + 180;
        }
        else if(20 <= day_of_month && day_of_month <25)
        {
            this.totalVolume = this.default_volume + 240;
        }
        else if(25 <= day_of_month && day_of_month <= 31)
        {
            this.totalVolume = this.default_volume + 300;
        }
        //System.out.println("TODAYS VOLUME : " + totalVolume);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createProgram(String[][] tiers)
    {

        int tmp_total_volume = totalVolume;
        ArrayList<String> usedExercises = new ArrayList<>();// arraylist to avoid the same exercises occuring more than once in a day

        //completing our total volume with random exercises
        while(tmp_total_volume >0) {
            int random_tier_index = (int)(Math.random() * 4); //random for tier
            int random_exercise_index =(int)(Math.random() * 5); //random for exercise

            String str_tier_index = String.valueOf(random_tier_index);
            String str_random_exercise_index = String.valueOf(random_exercise_index);

            String str = str_tier_index + str_random_exercise_index; //11  12
            boolean used = false;

            for(int i = 0; i < usedExercises.size(); i++)
            {
                if(usedExercises.get(i).equals(str))
                {
                    used = true;
                }
            }
            //if exercise is not used add it
            if(Boolean.compare(used, true) != 0) {

                usedExercises.add(str);

                switch(random_tier_index) {
                    case 0:
                        selected_exercise_volume = 30;
                        break;
                    case 1:
                        selected_exercise_volume = 50;
                        break;
                    case 2:
                        selected_exercise_volume = 60;
                        break;
                    case 3:
                        selected_exercise_volume = 60;
                        break;
                }
                //result.add(tiers[random_tier_index][random_exercise_index]);
                Exercise exercise = new Exercise(tiers[random_tier_index][random_exercise_index],
                        selected_exercise_volume);
                exerciseProgramList.add(exercise);
                tmp_total_volume = tmp_total_volume - selected_exercise_volume;
            }
        }
    }
}
