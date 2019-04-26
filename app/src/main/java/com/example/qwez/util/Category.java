package com.example.qwez.util;

import android.graphics.ColorSpace;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Constants class for quiz constants
 */
public enum  Category {

    GENERAL_KNOWLEDGE(9),
    BOOKS(10),
    FILMS(11),
    MUSIC(12),
    MUSICAL_AND_THEATRES(13),
    TELEVISION(14),
    VIDEOGAMES(15),
    BOARDGAMES(16),
    SCIENCE_AND_NATURE(17),
    COMPUTERS(18),
    MATHEMATICS(19),
    MYTHOLOGY(20),
    SPORTS(21),
    GEOGRAPHY(22),
    HISTORY(23),
    POLITICS(24),
    ART(25),
    CELEBRITIES(26),
    ANIMALS(27),
    VEHICLES(28),
    COMICS(29),
    GADGETS(30),
    ANIME_AND_MANGA(31),
    CARTOONS_AND_ANIMATIONS(32);

    private int category;

    Category(int category){
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public static String getAsString(Category category){
        String toReturn;
        switch (category){
            case GENERAL_KNOWLEDGE:
                toReturn = "General Knowledge";
                break;
            case BOOKS:
                toReturn = "Books";
                break;
            case FILMS:
                toReturn = "Films";
                break;
            case MUSIC:
                toReturn = "Music";
                break;
            case MUSICAL_AND_THEATRES:
                toReturn = "Musical and Theatres";
                break;
            case TELEVISION:
                toReturn = "Television";
                break;
            case VIDEOGAMES:
                toReturn = "Videogames";
                break;
            case BOARDGAMES:
                toReturn = "Boardgames";
                break;
            case SCIENCE_AND_NATURE:
                toReturn = "Science and Nature";
                break;
            case COMPUTERS:
                toReturn = "Computers";
                break;
            case MATHEMATICS:
                toReturn = "Mathematics";
                break;
            case MYTHOLOGY:
                toReturn = "Mythology";
                break;
            case SPORTS:
                toReturn = "Sports";
                break;
            case GEOGRAPHY:
                toReturn = "Geography";
                break;
            case HISTORY:
                toReturn = "History";
                break;
            case POLITICS:
                toReturn = "Politics";
                break;
            case ART:
                toReturn = "Art";
                break;
            case CELEBRITIES:
                toReturn = "Celebrities";
                break;
            case ANIMALS:
                toReturn = "Animals";
                break;
            case VEHICLES:
                toReturn = "Vehicles";
                break;
            case COMICS:
                toReturn = "Comics";
                break;
            case GADGETS:
                toReturn = "Gadgets";
                break;
            case ANIME_AND_MANGA:
                toReturn = "Anime and Manga";
                break;
            case CARTOONS_AND_ANIMATIONS:
                toReturn = "Cartoons and Animations";
                break;
                default:
                    toReturn = "ERROR: UNKNOWN CATEGORY";
                    break;
        }
        return toReturn;
    }

    public static int getAsInt(Category category){
        int toReturn;
        switch (category){
            case GENERAL_KNOWLEDGE:
                toReturn = 9;
                break;
            case BOOKS:
                toReturn = 10;
                break;
            case FILMS:
                toReturn = 11;
                break;
            case MUSIC:
                toReturn = 12;
                break;
            case MUSICAL_AND_THEATRES:
                toReturn = 13;
                break;
            case TELEVISION:
                toReturn = 14;
                break;
            case VIDEOGAMES:
                toReturn = 15;
                break;
            case BOARDGAMES:
                toReturn = 16;
                break;
            case SCIENCE_AND_NATURE:
                toReturn = 17;
                break;
            case COMPUTERS:
                toReturn = 18;
                break;
            case MATHEMATICS:
                toReturn = 19;
                break;
            case MYTHOLOGY:
                toReturn = 20;
                break;
            case SPORTS:
                toReturn = 21;
                break;
            case GEOGRAPHY:
                toReturn = 22;
                break;
            case HISTORY:
                toReturn = 23;
                break;
            case POLITICS:
                toReturn = 24;
                break;
            case ART:
                toReturn = 25;
                break;
            case CELEBRITIES:
                toReturn = 26;
                break;
            case ANIMALS:
                toReturn = 27;
                break;
            case VEHICLES:
                toReturn = 28;
                break;
            case COMICS:
                toReturn = 29;
                break;
            case GADGETS:
                toReturn = 30;
                break;
            case ANIME_AND_MANGA:
                toReturn = 31;
                break;
            case CARTOONS_AND_ANIMATIONS:
                toReturn = 32;
                break;
            default:
                toReturn = -1;
                break;
        }
        return toReturn;
    }

    public static Map<String,Category> getMap(){
        HashMap<String,Category> map = new HashMap<>();
        Arrays.stream(Category.values()).forEach(category1 -> map.put(Category.getAsString(category1), category1));
        return map;
    }

    public static List<String> getList(){
        List<String> list = new ArrayList<>();
        Arrays.stream(Category.values()).forEach(category1 -> list.add(Category.getAsString(category1)));
        return list;
    }

}
