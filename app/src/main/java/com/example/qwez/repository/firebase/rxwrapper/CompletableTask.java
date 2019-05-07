package com.example.qwez.repository.firebase.rxwrapper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.CompletableEmitter;

/**
 * Wraps Task<T> into a CompletableEmitter.
 */
public final class CompletableTask implements OnSuccessListener, OnFailureListener, OnCompleteListener {

    private final CompletableEmitter emitter;

    private CompletableTask(CompletableEmitter emitter) {
        this.emitter = emitter;
    }

    /**
     * assign Task<T> to be wrapped, to wrap in a CompletableEmitter
     * @param emitter CompletableEmitter wrapper
     * @param task Task to be wrapped
     * @param <T> Task type
     */
    public static <T> void assign(CompletableEmitter emitter, Task<T> task){
        CompletableTask completeTask = new CompletableTask(emitter);
        task.addOnCompleteListener(completeTask);
        task.addOnSuccessListener(completeTask);
        task.addOnFailureListener(completeTask);
    }

    /**
     * Calls CompletableEmitter.onComplete when Task.onComplete() is called.
     * Calls and checks !CompletableEmitter.isDisposed
     * @param task wrapped Task
     */
    @Override
    public void onComplete(@NonNull Task task) {
        if(!emitter.isDisposed()){
            emitter.onComplete();
        }
    }

    /**
     * Calls CompletableEmitter.onError(Throwable) when Task.onFailure(Exception) is called.
     *Calls and checks !CompletableEmitter.isDisposed
     * @param e Exception thrown
     */
    @Override
    public void onFailure(@NonNull Exception e) {
        if(!emitter.isDisposed()){
            emitter.onError(e);
        }
    }

    /**
     * Calls CompletableEmitter.onComplete() when Task.onSuccess(Object) is called.
     * Calls and checks !CompletableEmitter.isDisposed
     * @param o Object returned by Task
     */
    @Override
    public void onSuccess(Object o) {
        if(!emitter.isDisposed()){
            emitter.onComplete();
        }
    }
}
