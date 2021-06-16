package com.example.a30daybellytransformation;

public class Exercise {

    //properties
    String name;
    int duration = 0;
    int volume;
    int tier;


    public Exercise(String name, int volume, int tier)
    {
        this.name = name;
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
