package com.example.qwez.repository;

import com.example.qwez.repository.opentdb.entity.ResponseBody;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Custom ObservableOperator that takes in Retrofit<T> Response.
 *
 * Disposes to not break operator chain, and subscribes observer to this.
 *
 * Parses T data from Response<T>
 *
 * Emits onError() if Retrofit response was not successful
 *
 * @param <T> Object type
 */
public final class ApiOperator<T> implements ObservableOperator<T, Response<T>> {

    private static final int RESPONSE_CODE_ERROR = 1;

    @Override
    public Observer<? super Response<T>> apply(final Observer<? super T> observer) throws Exception {
        return new DisposableObserver<Response<T>>() {

            @Override
            public void onStart() {
                observer.onSubscribe(this);
            }

            @Override
            public void onNext(Response<T> tResponse) {
                dispose();
                if(!tResponse.isSuccessful()) {
                    observer.onError(new Exception("ApiOperator " + tResponse.message()));
                }else{
                    ResponseBody responseBody = (ResponseBody) tResponse.body();
                    if (responseBody != null && responseBody.getResponseCode()==RESPONSE_CODE_ERROR) {
                        observer.onError(new Exception("ApiOperator empty QuestionList"));
                    }else{
                        observer.onNext(tResponse.body());
                        observer.onComplete();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!isDisposed()) {
                    observer.onError(new Throwable("ApiOperator: "+e.getMessage()));
                }
            }

            @Override
            public void onComplete() {
                if (!isDisposed()) {
                    observer.onComplete();
                }
            }
        };
    }
}
