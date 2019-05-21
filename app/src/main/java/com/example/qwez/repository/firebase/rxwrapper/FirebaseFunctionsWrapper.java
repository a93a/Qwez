package com.example.qwez.repository.firebase.rxwrapper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Cancellable;

public final class FirebaseFunctionsWrapper {

    public static Single<HttpsCallableResult> performFunction(FirebaseFunctions functions, String function, Object data){
        return Single.create(emitter -> functions.getHttpsCallable(function)
                .call(data)
                .addOnSuccessListener(emitter::onSuccess)
                .addOnFailureListener(emitter::onError));
    }

    public static Single<HttpsCallableResult> performFunction(FirebaseFunctions functions, String function){
        return performFunction(functions, function, null);
    }

}
