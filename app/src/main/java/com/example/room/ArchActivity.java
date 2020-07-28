package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArchActivity extends AppCompatActivity {

    private ArchViewModel archViewModel;

    private CompositeDisposable compositeDisposable;

    @BindView(R.id.arch_button_start)
    Button mButton;

    @BindView(R.id.arch_text)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);
        compositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);
        archViewModel = new ViewModelProvider(this).get(ArchViewModel.class);

        archViewModel.getNumber().observe(this, new androidx.lifecycle.Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTextView.setText(String.valueOf(integer));
            }
        });
    }

    @OnClick(R.id.arch_button_start)
    void startIncrease(){
        archViewModel.increase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mButton.setEnabled(false);
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        archViewModel.getNumber().setValue(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}