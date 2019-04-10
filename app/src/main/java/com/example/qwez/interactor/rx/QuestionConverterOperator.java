package com.example.qwez.interactor.rx;

import com.example.qwez.repository.opentdb.entity.Question;
import com.example.qwez.util.QuestionConverter;

import java.util.List;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

public class QuestionConverterOperator implements ObservableOperator<List<com.example.qwez.repository.local.Question>, List<Question>> {


    @Override
    public Observer<? super List<Question>> apply(Observer<? super List<com.example.qwez.repository.local.Question>> observer) throws Exception {
        return new DisposableObserver<List<Question>>() {

            @Override
            protected void onStart() {
                observer.onSubscribe(this);
            }

            @Override
            public void onNext(List<Question> questions) {
                dispose();
                List<com.example.qwez.repository.local.Question> list = QuestionConverter.toDatabase(questions);
                observer.onNext(list);
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
