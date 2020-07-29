package com.example.room;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room.model.LoginResult;
import com.example.room.view.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.room.model.Result.LOGIN_SUCCESSFULLY;
import static com.example.room.model.Result.PASSWORD_INVALID;

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
        loginViewModel.setUserRepository(((MyApplication)getApplicationContext()).getUserRepository());

        loginViewModel.observeInsertResult(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showMessage(aBoolean ? "Insert successfully!" : "Insert Failed!");
            }
        });

        loginViewModel.observeUserInfoIsInvalid(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("onChanged", "checkUserInfo: " + aBoolean);
                if (!aBoolean) {
                    showMessage("Invalid user info");
                }
            }
        });

        loginViewModel.observeLoginResult(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if (LOGIN_SUCCESSFULLY.equals(loginResult.getResult())) {
                    showMessage("Login successfully");
                } else if (PASSWORD_INVALID.equals(loginResult.result)) {
                    showMessage("Password is invalid");
                } else {
                    showMessage("Username does not exist");
                }
            }
        });
    }

    private void  showMessage(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
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