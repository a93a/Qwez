package com.example.qwez.repository.firebase;

import com.example.firebasewrapper.rxwrapper.FirebaseDBWrapper;
import com.example.qwez.entity.Highscore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FirebaseDatabase implements FirebaseDatabaseType {

    private final FirebaseFirestore db;
    private static final String HIGHSCORE = "highscore";
    private static final String USER = "user";
    private static final String SCORE = "score";
    private static final int LIMIT_SCORES = 50;
    private static final String USER_NICK = "nick";
    private static final String USER_SCORE = "score";

    public FirebaseDatabase(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public Single<Integer> getUserHighscore(String uId) {
        final DocumentReference ref = db.collection(HIGHSCORE).document(uId);
        return FirebaseDBWrapper.observeDocumentReference(ref)
                .firstOrError()
                .map(d -> (int)(long) d.getData().get(SCORE))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Highscore>> getTop50Highscores() {
        final Query query = db.collection(HIGHSCORE).limit(LIMIT_SCORES);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<Highscore> list = new ArrayList<>();
                    snapshots.forEach(documentSnapshot -> list.add(documentSnapshot.toObject(Highscore.class)));
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateNick(String uid, String newNick) {
        final DocumentReference ref = db.collection(USER).document(uid);
        final Map<String, Object> map = new HashMap<>();
        map.put(USER_NICK,newNick);
        return FirebaseDBWrapper.updateDocument(ref, map)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateHighscore(String uid, int addToHighscore) {
        final DocumentReference ref = db.collection(USER).document(uid);
        return FirebaseDBWrapper.updateDocument(ref, USER_SCORE, FieldValue.increment((long) addToHighscore))
                .subscribeOn(Schedulers.io());
    }
}
