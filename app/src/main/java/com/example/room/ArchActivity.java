package com.example.room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

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

        archViewModel.observeEnableButton(this, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mButton.setEnabled(value);
            }
        });

        archViewModel.observeIsStart(this, new androidx.lifecycle.Observer<Long>() {
            @Override
            public void onChanged(Long value) {
                mTextView.setText(String.valueOf(value));
            }
        });
    }

    @OnClick(R.id.arch_button_start)
    void startIncrease(){
        archViewModel.increase();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}