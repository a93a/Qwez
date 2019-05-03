package com.example.qwez.repository.firebase.rxwrapper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.MaybeEmitter;

/**
 * Wraps Task<T> into a MaybeEmitter<? super T>
 */
public final class MaybeTask<T> implements OnSuccessListener<T>, OnFailureListener, OnCompleteListener<T> {

    private final MaybeEmitter<? super T> emitter;

    private MaybeTask(MaybeEmitter<? super T> emitter) {
        this.emitter = emitter;
    }

    /**
     * assign Task<T> to be wrapped, to wrap in a MaybeEmitter<? super T>
     * @param emitter MaybeEmitter<? super T> wrapper
     * @param task Task to be wrapped
     * @param <T> Task type
     */
    public static <T> void assignOnTask(MaybeEmitter<? super T> emitter, Task<T> task) {
        MaybeTask<T> handler = new MaybeTask<T>(emitter);
        task.addOnSuccessListener(handler);
        task.addOnFailureListener(handler);
        task.addOnCompleteListener(handler);
    }


    /**
     * Calls MaybeEmitter.onComplete when Task.onComplete() is called.
     * Calls and checks !MaybeEmitter.isDisposed
     * @param task wrapped Task
     */
    @Override
    public void onComplete(@NonNull Task<T> task) {
        if (!emitter.isDisposed()){
            emitter.onComplete();
        }
    }

    /**
     * Calls MaybeEmitter.onError(Throwable) when Task.onFailure(Exception) is called.
     * Calls and checks !MaybeEmitter.isDisposed
     * @param e Exception thrown
     */
    @Override
    public void onFailure(@NonNull Exception e) {
        if (!emitter.isDisposed()){
            emitter.onError(e);
        }
    }

    /**
     * Calls MaybeEmitter.onSuccess when Task.onSuccess() is called.
     * Calls and checks !MaybeEmitter.isDisposed
     * @param t Generic value returned by Task
     */
    @Override
    public void onSuccess(T t) {
        if (!emitter.isDisposed()){
            emitter.onSuccess(t);
        }
    }
}
