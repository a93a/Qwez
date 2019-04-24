package com.example.qwez.base;


import com.example.qwez.entity.ErrorCarrier;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {


    protected final MutableLiveData<ErrorCarrier> error = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> progress = new MutableLiveData<>();
    protected Disposable disposable;

    @Override
    protected void onCleared() {
        cancel();
    }

    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    protected void onError(Throwable throwable) {
        if(progress.getValue()!=null && progress.getValue()){
            progress.setValue(false);
        }
        error.postValue(new ErrorCarrier(throwable.getMessage()));
    }

    public LiveData<ErrorCarrier> error() {
        return error;
    }

    public LiveData<Boolean> progress() {
        return progress;
    }

}
