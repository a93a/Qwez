package com.example.qwez.util;

/**
 * Constants class for quiz constants
 */
public enum  Category {

    /*
    public static final String GENERAL_KNOWLEDGE = "9";
    public static final String BOOKS = "10";
    public static final String FILMS = "11";
    public static final String MUSIC = "12";
    public static final String MUSICAL_AND_THEATRES = "13";
    public static final String TELEVISION = "14";
    public static final String VIDEOGAMES = "15";
    public static final String BOARDGAMES = "16";
    public static final String SCIENCE_AND_NATURE = "17";
    public static final String COMPUTERS = "18";
    public static final String MATHEMATICS = "19";
    public static final String MYTHOLOGY = "20";
    public static final String SPORTS = "21";
    public static final String GEOGRAPHY = "22";
    public static final String HISTORY = "23";
    public static final String POLITICS = "24";
    public static final String ART = "25";
    public static final String CELEBRITIES = "26";
    public static final String ANIMALS = "27";
    public static final String VEHICLES = "28";
    public static final String COMICS = "29";
    public static final String GADGETS = "30";
    public static final String ANIME_AND_MANGA = "31";
    public static final String CARTOONS_AND_ANIMATIONS = "32";
    */
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

}
