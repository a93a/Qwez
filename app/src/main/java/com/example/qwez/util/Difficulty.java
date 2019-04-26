package com.example.qwez.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  Difficulty {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String difficulty;

    Difficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public static String getAsString(Difficulty difficulty){
        String toReturn;
        switch (difficulty){
            case EASY:
                toReturn = "Easy";
                break;
            case MEDIUM:
                toReturn = "Medium";
                break;
            case HARD:
                toReturn = "Hard";
                break;
                default:
                    toReturn = "ERROR unknown difficulty";
                    break;
        }
        return toReturn;
    }

    public static Map<String,Difficulty> getMap(){
        HashMap<String,Difficulty> map = new HashMap<>();
        map.put("Easy", Difficulty.EASY);
        map.put("Medium", Difficulty.MEDIUM);
        map.put("Hard", Difficulty.HARD);
        return map;
    }

    public static List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add("Easy");
        list.add("Medium");
        list.add("Hard");
        return list;
    }
}
