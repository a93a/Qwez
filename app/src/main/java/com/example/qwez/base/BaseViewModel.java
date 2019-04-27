package com.example.qwez.base;


import com.example.qwez.entity.ErrorCarrier;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {

    //LiveData for errors
    private final MutableLiveData<ErrorCarrier> error = new MutableLiveData<>();
    //LiveData for progress
    protected final MutableLiveData<Boolean> progress = new MutableLiveData<>();
    //Disposable resource
    protected Disposable disposable;

    @Override
    protected void onCleared() {
        cancel();
    }

    /**
     * Cancel this viewmodels disposable
     */
    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * Set error to LiveData. Also sets {@code progress} to false
     * @param throwable to set
     */
    protected void onError(Throwable throwable) {
        if(progress.getValue()!=null && progress.getValue()){
            progress.setValue(false);
        }
        error.postValue(new ErrorCarrier(throwable.getMessage()));
    }

    /**
     * Get error LiveData
     * @return error
     */
    public LiveData<ErrorCarrier> error() {
        return error;
    }

    /**
     * Get progress LiveData
     * @return progress
     */
    public LiveData<Boolean> progress() {
        return progress;
    }

}
