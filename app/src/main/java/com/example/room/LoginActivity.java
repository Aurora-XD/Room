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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.room.Result.LOGIN_SUCCESSFULLY;
import static com.example.room.Result.PASSWORD_INVALID;
import static com.example.room.Result.USER_NAME_NOT_EXIST;

public class LoginActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;

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
        compositeDisposable = new CompositeDisposable();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if(LOGIN_SUCCESSFULLY.equals(loginResult.getResult())){
                    Toast.makeText(MyApplication.getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                }else if (PASSWORD_INVALID.equals(loginResult.result)){
                    Toast.makeText(MyApplication.getContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MyApplication.getContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.login_button_insert_user)
    void insertUser() {
        Disposable subscribe = loginViewModel.insertUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Toast.makeText(MyApplication.getContext(), "Insert successfully!", Toast.LENGTH_SHORT).show();
                    }
                });

        compositeDisposable.add(subscribe);
    }

    @OnClick(R.id.login_button_login)
    void login() {
        boolean checkUserInfo = loginViewModel.checkUserInfo(mUsername.getText().toString(), mPassword.getText().toString());
        Log.d("TAG", "login: " + checkUserInfo);
        if (!checkUserInfo) {
            Toast.makeText(this, "Invalid user info", Toast.LENGTH_SHORT).show();
            return;
        }

        Disposable subscribe = loginViewModel.login(mUsername.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        LoginResult loginResult = new LoginResult();
                        Log.d("TAG", "accept: " + user.getUsername() + user.getPassword());
                        if (user.getPassword().equals(mPassword.getText().toString())) {
                            loginResult.setResult(LOGIN_SUCCESSFULLY);
                        } else {
                            loginResult.setResult(PASSWORD_INVALID);
                        }
                        loginViewModel.getLoginResult().setValue(loginResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LoginResult loginResult = new LoginResult();
                        loginResult.setResult(USER_NAME_NOT_EXIST);
                        loginViewModel.getLoginResult().setValue(loginResult);
                    }
                });

        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}