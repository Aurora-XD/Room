package com.example.room;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;

    @BindView(R.id.view_text_person)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();
        setTextView();
    }

    private void setTextView(){

        MyApplication.getLocalDataSource()
                .getPersonDao()
                .getAllPerson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Person>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Person> people) {
                        StringBuilder value = new StringBuilder();
                        for (Person person : people) {
                            value.append("id: ").append(person.getId()).append("\n")
                                 .append("name: ").append(person.getName()).append("\n")
                                 .append("gender: ").append(person.getGender()).append("\n")
                                 .append("age: ").append(person.getAge()).append("\n").append("\n");
                        }
                        mTextView.setText(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}