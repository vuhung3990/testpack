package com.example.hungvu.testpack;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * RxBus to replace event bus
 * <pre>
 * Example:
 *     onCreate()
 *     subscription = RxBus.getInstance().subscribe...
 *
 *     onDestroy()
 *     RxBus.getInstance().unSubscribe(subscription);
 * </pre>
 */
public class RxBus {
    private static RxBus instance;
    private final Subject<Object, Object> _bus = PublishSubject.create();

    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    /**
     * send event object
     *
     * @param o object to send (string, int, object ...)
     */
    public void send(Object o) {
        _bus.onNext(o);
    }

    /**
     * @return The Observable class that implements the Reactive Pattern.
     * This class provides methods for subscribing to the Observable as well as delegate methods to the various Observers
     */
    public Observable<Object> toObserverable() {
        return _bus;
    }

    /**
     * @param filter    type of return object, null to skip
     * @param subcriber Provides a mechanism for receiving push-based notifications from Observables, and permits manual un-subscribing from these Observables
     * @return Subscription returns from Observable.subscribe(Subscriber) to allow un-subscribing
     * @see #unSubscribe(Subscription)
     */
    public Subscription subscribe(final Class filter, Subscriber<? super Object> subcriber) {
        return _bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return filter != null ? filter.isInstance(o) : true;
            }
        }).subscribe(subcriber);
    }

    /**
     * un-subscribe to avoid leak memory
     *
     * @param subscription Subscription returns from Observable.subscribe(Subscriber) to allow un-subscribing
     * @see #subscribe(Class, Subscriber)
     */
    public void unSubscribe(Subscription subscription) {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}