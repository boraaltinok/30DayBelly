package com.example.a30daybellytransformation;

public class Exercise {

    //properties
    String name;
    String nameOfImage;
    int duration = 0;
    int volume;
    int tier;
    String exerciseInfo;
    long leftDuration; // variable to detect the pauses in countdowns and pauses the countdown, it will change


    public Exercise(String name, int volume, int tier)
    {
        this.name = name;
        nameOfImage = name;
        nameOfImage = nameOfImage.replaceAll(" ", "_").toLowerCase();
        exerciseInfo = name + " is an exercise that works the body.";
        this.volume = volume;
        this.tier = tier;
        switch (tier){
            case 1:
                duration = 30;
                break;
            case 2:
                duration = 25;
                break;
            case 3:
                duration = 30;
                break;
            case 4:
                duration = 40;
                break;
        }
        leftDuration = duration * 1000;
    }

    public void resetLeftDuration()
    {
        leftDuration = duration;
    }
    public long getLeftDuration()
    {
        return leftDuration;
    }
    public void setLeftDuration(long leftDur)
    {
        leftDuration = leftDur;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getVolume()
    {
        return this.volume;
    }
    public void setVolume(int vol)
    {
        this.volume = vol;
    }
}
