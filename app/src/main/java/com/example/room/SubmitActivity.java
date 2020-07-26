package com.example.room;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SubmitActivity extends AppCompatActivity {

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    private CompositeDisposable compositeDisposable;

    @BindView(R.id.sub_edit_name)
    EditText mName;

    @BindView(R.id.sub_edit_gender)
    EditText mGender;

    @BindView(R.id.sub_edit_age)
    EditText mAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();
    }

    @OnClick(R.id.sub_button_submit)
    void submitPersonInfo() {
        if (!checkPersonInfo()) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            return;
        }

        Person person = new Person(mName.getText().toString(),
                Integer.parseInt(mGender.getText().toString()),
                Integer.parseInt(mAge.getText().toString()));

        Disposable disposable = MyApplication.getLocalDataSource()
                .getPersonDao()
                .createPerson(person)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Toast.makeText(SubmitActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(SubmitActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private boolean checkPersonInfo() {
        if (TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mGender.getText()) || TextUtils.isEmpty(mAge.getText())) {
            return false;
        }
        int gender = Integer.parseInt(mGender.getText().toString());
        int age = Integer.parseInt(mAge.getText().toString());
        return age >= 0 && (gender == MALE || gender == FEMALE);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}