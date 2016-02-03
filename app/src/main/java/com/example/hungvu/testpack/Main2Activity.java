package com.example.hungvu.testpack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func2;
import rx.functions.Func3;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "rxJava";
    private Subscription sub;
    private EditText mEdittext;
    private Subscriber<? super CharSequence> subTextChange;
    private EditText txt4;
    private EditText txt2;
    private EditText txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sub = RxBus.getInstance().subscribe(Integer.class, new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: " + o);
            }
        });


        // search text timer
        mEdittext = (EditText) findViewById(R.id.editText);
        subTextChange = new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error: ");
            }

            @Override
            public void onNext(CharSequence charSequence) {
                Log.d(TAG, "search: " + charSequence);
            }
        };
        Observable.create(new Observable.OnSubscribe<CharSequence>() {
            @Override
            public void call(final Subscriber<? super CharSequence> subscriber) {
                mEdittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }).throttleWithTimeout(500, TimeUnit.MILLISECONDS).subscribe(subTextChange);


        // form valid using combine
        txt2 = (EditText) findViewById(R.id.editText2);
        txt3 = (EditText) findViewById(R.id.editText3);
        txt4 = (EditText) findViewById(R.id.editText4);

        Observable<CharSequence> observable2 = Observable.create(new Observable.OnSubscribe<CharSequence>() {
            @Override
            public void call(final Subscriber<? super CharSequence> subscriber) {
                txt2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });
        Observable<CharSequence> observable3 = Observable.create(new Observable.OnSubscribe<CharSequence>() {
            @Override
            public void call(final Subscriber<? super CharSequence> subscriber) {
                txt3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });
        Observable<CharSequence> observable4 = Observable.create(new Observable.OnSubscribe<CharSequence>() {
            @Override
            public void call(final Subscriber<? super CharSequence> subscriber) {
                txt4.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });

        Observable.combineLatest(observable2, observable3, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                boolean isGreater10 = Integer.parseInt(txt2.getText().toString()) > 10;
                if (!isGreater10) txt2.setError("must be greater 10");

                boolean isLengthGreater5 = txt3.getText().toString().length() > 5;
                if (!isLengthGreater5) txt3.setError("length must be greater than 5");

                return isGreater10 && isLengthGreater5;
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.d(TAG, "return: " + aBoolean);
            }
        });
    }

    @Override
    protected void onDestroy() {
        RxBus.getInstance().unSubscribe(sub);
        subTextChange.unsubscribe();
        super.onDestroy();
    }
}
