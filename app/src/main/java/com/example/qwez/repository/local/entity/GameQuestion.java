package com.example.qwez.repository.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Nested fields Class with {@link @Entity #Game} as @Embedded object and @Relation (as list)
 * {@link @Entity #Question}
 */
public class GameQuestion {

    @Embedded
    public Game game;

    @Relation(parentColumn = "id", entityColumn = "question_id")
    public List<Question> questions;

}
