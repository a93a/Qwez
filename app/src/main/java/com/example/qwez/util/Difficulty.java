package com.example.qwez.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Constants class for quiz constants
 */
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


    /**
     * Get Difficulty ENUM(s) String value
     * @param difficulty of which String value is wanted
     * @return Difficulty String value
     */
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

    /**
     * @return Map of Difficulty String Value as key, and Difficulty ENUM as value
     */
    public static Map<String,Difficulty> getMap(){
        HashMap<String,Difficulty> map = new HashMap<>();
        map.put("Easy", Difficulty.EASY);
        map.put("Medium", Difficulty.MEDIUM);
        map.put("Hard", Difficulty.HARD);
        return map;
    }

    /**
     * @return List of Difficulty String Value(s)
     */
    public static List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add("Easy");
        list.add("Medium");
        list.add("Hard");
        return list;
    }
}
