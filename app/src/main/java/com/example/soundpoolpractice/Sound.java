package com.example.soundpoolpractice;

public class Sound implements Comparable<Sound> {
    private int soundPoolId = 0;
    private String name;

    Sound() {}

    Sound(int setSoundPoolId) {
        soundPoolId = setSoundPoolId;
    }

    public int getSoundPoolId() {
        return soundPoolId;
    }

    public void setSoundPoolId(int setSoundPoolId) {
        soundPoolId = setSoundPoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String setName) {
        name = setName;
    }

    @Override
    public int compareTo(Sound other) {
        return name.compareTo(other.getName());
    }
}
