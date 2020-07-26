package com.example.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewActivity extends AppCompatActivity {
    private Disposable disposable;

    @BindView(R.id.view_text_person)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ButterKnife.bind(this);
        setTextView();
    }

    private void setTextView(){

        PersonDao personDao = MyApplication.getLocalDataSource().getPersonDao();

        SingleObserver<List<Person>> observer = new SingleObserver<List<Person>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(List<Person> people) {
                StringBuilder value = new StringBuilder();
                for (Person person : people) {
                    value.append("id: "+person.getId()+"\n");
                    value.append("name: "+person.getName()+"\n");
                    value.append("gender: "+person.getGender()+"\n");
                    value.append("age: "+person.getAge()+"\n").append("\n");
                }
                mTextView.setText(value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        };

        personDao.getAllPerson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        if(Objects.nonNull(disposable) && !disposable.isDisposed()){
            disposable.dispose();
        }
        super.onDestroy();
    }
}