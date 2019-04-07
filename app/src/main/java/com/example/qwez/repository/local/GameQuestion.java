package com.example.qwez.repository.local;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class GameQuestion {

    @Embedded
    public Game game;

    @Relation(parentColumn = "id", entityColumn = "question_id")
    public List<Question> questions;

}
