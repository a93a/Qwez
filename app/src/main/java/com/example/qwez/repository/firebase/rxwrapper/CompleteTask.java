package com.example.qwez.repository.firebase.rxwrapper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import io.reactivex.CompletableEmitter;

public class CompleteTask implements OnSuccessListener, OnFailureListener, OnCompleteListener {

    private final CompletableEmitter emitter;

    private CompleteTask(CompletableEmitter emitter) {
        this.emitter = emitter;
    }

    public static <T> void assign(CompletableEmitter emitter, Task<T> task){
        CompleteTask completeTask = new CompleteTask(emitter);
        task.addOnCompleteListener(completeTask);
        task.addOnCompleteListener(completeTask);
        task.addOnFailureListener(completeTask);
    }


    @Override
    public void onComplete(@NonNull Task task) {
        if(!emitter.isDisposed()){
            emitter.onComplete();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if(!emitter.isDisposed()){
            emitter.onError(e);
        }
    }

    @Override
    public void onSuccess(Object o) {
        if(!emitter.isDisposed()){
            emitter.onComplete();
        }
    }
}
