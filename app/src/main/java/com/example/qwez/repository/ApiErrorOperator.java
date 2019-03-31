package com.example.qwez.repository;

import com.example.qwez.repository.entity.ResponseBody;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class ApiErrorOperator<T> implements ObservableOperator<T, Response<T>> {


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
                observer.onNext(tResponse.body());
                observer.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                if (!isDisposed()) {
                    observer.onError(e);
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
