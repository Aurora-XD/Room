package com.example.room;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class NetworkActivity extends AppCompatActivity {
    public static final String PATH = "https://twc-android-bootcamp.github.io/fake-data/data/default.json";
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();
    }

    @OnClick(R.id.net_button_get_info)
    void getPersonInfo() {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    Response response = HttpUtil.sendHttpRequest(PATH);
                    if(response.isSuccessful()){
                        emitter.onNext(response.toString());
                        emitter.onComplete();
                    }else {
                        emitter.onError(new Exception("request failed!"));
                    }
                } catch (IOException e) {
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values -> Toast.makeText(this, values, Toast.LENGTH_SHORT).show(),
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MyApplication.getContext(), "Exception", Toast.LENGTH_SHORT).show();
                            }
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}