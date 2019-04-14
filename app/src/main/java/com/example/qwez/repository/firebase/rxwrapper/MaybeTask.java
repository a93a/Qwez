package com.example.qwez.repository.firebase.rxwrapper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import io.reactivex.MaybeEmitter;

public class MaybeTask<T> implements OnSuccessListener<T>, OnFailureListener, OnCompleteListener<T> {

    private final MaybeEmitter<? super T> emitter;

    private MaybeTask(MaybeEmitter<? super T> emitter) {
        this.emitter = emitter;
    }

    public static <T> void assignOnTask(MaybeEmitter<? super T> emitter, Task<T> task) {
        MaybeTask<T> handler = new MaybeTask<T>(emitter);
        task.addOnSuccessListener(handler);
        task.addOnFailureListener(handler);
        task.addOnCompleteListener(handler);
    }


    @Override
    public void onComplete(@NonNull Task<T> task) {
        if (!emitter.isDisposed()){
            emitter.onComplete();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (!emitter.isDisposed()){
            emitter.onError(e);
        }
    }

    @Override
    public void onSuccess(T t) {
        if (!emitter.isDisposed()){
            emitter.onSuccess(t);
        }
    }
}
