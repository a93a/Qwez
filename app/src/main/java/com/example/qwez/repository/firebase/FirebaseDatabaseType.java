package com.example.qwez.repository.firebase;

import com.example.qwez.entity.Highscore;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FirebaseDatabaseType {

    Single<Integer> getUserHighscore(String uId);

    Single<List<Highscore>> getTop50Highscores();

    Completable updateNick(String uid, String newNick);

}
