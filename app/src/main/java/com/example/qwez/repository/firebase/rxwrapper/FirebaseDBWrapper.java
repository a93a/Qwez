package com.example.qwez.repository.firebase.rxwrapper;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;

import org.intellij.lang.annotations.Flow;

import javax.annotation.Nullable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Cancellable;

public final class FirebaseDBWrapper {

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges changes,
                                                                      @NonNull final Activity activity){
        return observeDocumentReference(reference, changes,BackpressureStrategy.DROP,activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull BackpressureStrategy strategy,
                                                                      @NonNull final Activity activity){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,strategy, activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final Activity activity){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,BackpressureStrategy.DROP,
                activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges changes){
        return observeDocumentReference(reference, changes,BackpressureStrategy.DROP);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull BackpressureStrategy strategy){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,strategy);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,BackpressureStrategy.DROP);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges metadataChanges,
                                                                      @NonNull BackpressureStrategy strategy){
        return Flowable.create(emitter -> {
            final ListenerRegistration registration = reference.addSnapshotListener(metadataChanges, (documentSnapshot, error) -> {
                if(error != null && !emitter.isCancelled()){
                    emitter.onError(error);
                    return;
                }
                emitter.onNext(documentSnapshot);
            });
            emitter.setCancellable(registration::remove);
        }, strategy);
    }

   public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                     @NonNull final MetadataChanges metadataChanges,
                                                                     @NonNull BackpressureStrategy strategy,
                                                                     @NonNull final Activity activity){
        return Flowable.create(emitter -> {
            final ListenerRegistration registration = reference.addSnapshotListener(activity,metadataChanges, (documentSnapshot, error) -> {
                if(error != null && !emitter.isCancelled()){
                    emitter.onError(error);
                    return;
                }
                emitter.onNext(documentSnapshot);
            });
            emitter.setCancellable(registration::remove);
        }, strategy);
   }

}
