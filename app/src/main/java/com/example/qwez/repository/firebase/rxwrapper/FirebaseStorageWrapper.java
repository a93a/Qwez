package com.example.qwez.repository.firebase.rxwrapper;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Maybe;
import io.reactivex.Single;

public final class FirebaseStorageWrapper {

    public static Single<UploadTask.TaskSnapshot> putFile(StorageReference reference, Uri uri){
        return Single.create(emitter -> {
            StorageTask<UploadTask.TaskSnapshot> task = reference.putFile(uri)
                    .addOnSuccessListener(emitter::onSuccess)
                    .addOnFailureListener(e -> {
                        if(!emitter.isDisposed()){
                            emitter.onError(e);
                        }
            });
            emitter.setCancellable(task::cancel);
        });
    }

    public static Maybe<Uri> getDownloadUrl(StorageReference ref) {
        return Maybe.create(emitter -> MaybeTask.assign(emitter, ref.getDownloadUrl()));
    }

}
