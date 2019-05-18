package com.example.qwez.repository.firebase;

import com.example.qwez.repository.firebase.rxwrapper.FirebaseDBWrapper;
import com.example.qwez.repository.firebase.rxwrapper.FirebaseStorageWrapper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FirebaseDatabase implements FirebaseDatabaseType {

    private final FirebaseFirestore db;
    private static final String HIGHSCORE = "highscore";
    private static final String SCORE = "score";
    private static final int LIMIT_SCORES = 50;

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
    public Single<List<Integer>> getTop50Highscores() {
        final Query query = db.collection(HIGHSCORE).limit(LIMIT_SCORES);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<Integer> list = new ArrayList<>();
                    snapshots.forEach(documentSnapshot -> list.add((int) (long) documentSnapshot.getData().get(SCORE)));
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }
}
