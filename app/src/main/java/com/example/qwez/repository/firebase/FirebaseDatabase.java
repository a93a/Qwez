package com.example.qwez.repository.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDatabase implements FirebaseDatabaseType {

    private final FirebaseFirestore db;

    public FirebaseDatabase(FirebaseFirestore db) {
        this.db = db;
    }

}
