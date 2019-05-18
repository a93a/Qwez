package com.example.qwez.ui.settings;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.qwez.base.BaseViewModel;
import com.example.qwez.interactor.ChangeProfilePhotoInteractor;
import com.example.qwez.interactor.ChangeUserNickInteractor;
import com.example.qwez.interactor.ChangeUserPasswordInteractor;
import com.example.qwez.interactor.LogoutUserInteractor;
import com.example.qwez.router.LoginRouter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class SettingsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();
    private final MutableLiveData<Boolean> nickChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> photoChange = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeUserNickInteractor changeUserNickInteractor;
    private final ChangeProfilePhotoInteractor changeProfilePhotoInteractor;

    public SettingsViewModel(LogoutUserInteractor logoutUserInteractor,
                             ChangeUserPasswordInteractor changeUserPasswordInteractor,
                             LoginRouter loginRouter,
                             ChangeUserNickInteractor changeUserNickInteractor,
                             ChangeProfilePhotoInteractor changeProfilePhotoInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeUserNickInteractor = changeUserNickInteractor;
        this.changeProfilePhotoInteractor = changeProfilePhotoInteractor;
    }

    public void logoutUser(){
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout,this::onError);
    }

    public void changePassword(String oldPass, String newPass){
        progress.setValue(true);
        disposable = changeUserPasswordInteractor.changeUserPassword(oldPass,newPass)
                .subscribe(this::onPassChange,this::onError);
    }

    public void changeNick(String newNick){
        progress.setValue(true);
        disposable = changeUserNickInteractor.ChangeNick(newNick)
                .subscribe(this::onNick,this::onError);
    }

    public void changePhoto(Uri uri){
        //progress.setValue(true);
        //disposable = changeProfilePhotoInteractor.changeProfilePhoto(uri)
        //        .subscribe(this::onPhotoChanged,this::onError);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("/user_photo").child(String.format("/%S", firebaseUser.getUid()));
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri newUri) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(newUri)
                                .build();
                        firebaseUser.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //if it gets here it means operation was successful
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Timber.d("shak e3");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.d("shak e2");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.d("shak e1 %s",e.getMessage());
            }
        });

    }

    private void onPhotoChanged(){
        progress.setValue(false);
        photoChange.setValue(true);
    }

    private void onNick() {
        progress.setValue(false);
        nickChange.setValue(true);
    }

    private void onPassChange() {
        progress.setValue(false);
        passChanged.setValue(true);
    }

    private void onLogout() {
        progress.setValue(false);
        logout.setValue(true);
    }

    public void startLogin(Context context, boolean clearstack){
        loginRouter.open(context, clearstack);
    }

    public MutableLiveData<Boolean> logout() {
        return logout;
    }

    public MutableLiveData<Boolean> passChange(){
        return passChanged;
    }

    public MutableLiveData<Boolean> nickChange(){
        return nickChange;
    }

    public MutableLiveData<Boolean> photoChange(){
        return photoChange;
    }

}


