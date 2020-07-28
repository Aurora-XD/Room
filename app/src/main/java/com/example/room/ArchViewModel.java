package com.example.room;

import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ArchViewModel extends ViewModel {

    private MutableLiveData<Integer> number;
    private volatile boolean FLAG = false;
    private int start = 0;

    public MutableLiveData<Integer> getNumber() {
        if(Objects.isNull(number)){
            number = new MutableLiveData<>();
        }
        return number;
    }

    public Observable<Integer> increase(){
        FLAG = true;
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                while (FLAG){
                    SystemClock.sleep(1000);
                    Log.d("TAG", "subscribe: "+start);
                    ++start;
                    emitter.onNext(start);
                }
                emitter.onComplete();
            }
        });
    }

    @Override
    protected void onCleared() {
        FLAG = false;
        super.onCleared();
    }
}
