package com.example.room;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitActivity extends AppCompatActivity {

    public static final int MALE = 0;
    public static final int FEMALE = 1;

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
    }

    @OnClick(R.id.sub_button_submit)
    void submitPersonInfo() {
        if (!checkPersonInfo()){
            Toast.makeText(this,"失败",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPersonInfo() {
        if (TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mGender.getText()) || TextUtils.isEmpty(mAge.getText())) {
            return false;
        }
        int gender = Integer.parseInt(mGender.getText().toString());
        int age = Integer.parseInt(mAge.getText().toString());
        return age >= 0 && (gender == MALE || gender == FEMALE);
    }
}