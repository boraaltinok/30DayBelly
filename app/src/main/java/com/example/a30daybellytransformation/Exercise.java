package com.example.a30daybellytransformation;

public class Exercise {

    //properties
    String name;
    int duration;
    int volume;


    public Exercise(String name, int volume)
    {
        this.name = name;
        this.volume = volume;
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
