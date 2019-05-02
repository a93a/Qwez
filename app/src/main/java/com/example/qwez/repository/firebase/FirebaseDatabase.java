package com.example.qwez.repository.firebase;

import android.app.Activity;
import android.content.Context;

import com.example.qwez.repository.firebase.rxwrapper.FirebaseDBWrapper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseDatabase implements FirebaseDatabaseType {

    private final FirebaseFirestore db;

    public FirebaseDatabase(FirebaseFirestore db) {
        this.db = db;
    }

}
