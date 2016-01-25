package com.example.hungvu.testpack;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class RxBus {

    private static RxBus instance;
    private final Subject<Object, Object> _bus = PublishSubject.create();

    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }
}