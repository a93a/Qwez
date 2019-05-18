package com.example.qwez.repository.firebase;

import java.util.List;

import io.reactivex.Single;

public interface FirebaseDatabaseType {

    Single<Integer> getUserHighscore(String uId);

    Single<List<Integer>> getTop50Highscores();

}
