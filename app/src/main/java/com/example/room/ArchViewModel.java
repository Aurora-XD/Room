package com.example.room;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ArchViewModel extends ViewModel {

    private Disposable disposable;
    private MutableLiveData<Long> number;
    private MutableLiveData<Boolean> enableButton;

    public ArchViewModel() {
        number = new MutableLiveData<>();
        number.postValue(0L);
        enableButton = new MutableLiveData<>();
        enableButton.postValue(true);
    }

    public void observeIsStart(@NonNull LifecycleOwner owner, @NonNull Observer observer){
        number.observe(owner,observer);
    }

    public void observeEnableButton(@NonNull LifecycleOwner owner, @NonNull Observer observer){
        enableButton.observe(owner,observer);
    }

    public void increase(){
        enableButton.setValue(false);
        disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("TAG", "accept: "+aLong);
                        Long currentValue = number.getValue();
                        number.postValue(++currentValue);
                    }
                });
    }

    @Override
    protected void onCleared() {
        enableButton.setValue(true);
        if(Objects.nonNull(disposable) && !disposable.isDisposed()){
            disposable.dispose();
        }
        super.onCleared();
    }
}
