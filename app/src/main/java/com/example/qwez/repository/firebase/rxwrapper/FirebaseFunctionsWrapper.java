package com.example.qwez.repository.firebase.rxwrapper;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import io.reactivex.Single;

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
