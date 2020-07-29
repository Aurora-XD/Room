package com.example.room;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.room.Result.LOGIN_SUCCESSFULLY;
import static com.example.room.Result.PASSWORD_INVALID;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @BindView(R.id.login_edit_username)
    EditText mUsername;

    @BindView(R.id.login_edit_password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.observeInsertResult(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(MyApplication.getContext(), aBoolean ? "Insert successfully!" : "Insert Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        loginViewModel.observeUserInfoIsInvalid(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("onChanged", "checkUserInfo: " + aBoolean);
                if (!aBoolean) {
                    Toast.makeText(MyApplication.getContext(), "Invalid user info", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginViewModel.observeLoginResult(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if (LOGIN_SUCCESSFULLY.equals(loginResult.getResult())) {
                    Toast.makeText(MyApplication.getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                } else if (PASSWORD_INVALID.equals(loginResult.result)) {
                    Toast.makeText(MyApplication.getContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.login_button_insert_user)
    void insertUser() {
        loginViewModel.insertUser();
    }

    @OnClick(R.id.login_button_login)
    void login() {
        loginViewModel.login(mUsername.getText().toString(), mPassword.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}