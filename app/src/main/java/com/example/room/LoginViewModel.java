package com.example.room;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.Maybe;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult;
    private UserRepository userRepository = new UserRepository();

    public Maybe<Long> insertUser() {
        return userRepository.saveUser(new User("android", "123456"));
    }

    public MutableLiveData<LoginResult> getLoginResult() {
        if (Objects.isNull(loginResult)) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public Maybe<User> login(String name) {
        return userRepository.findByUserName(name);
    }


    public boolean checkUserInfo(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

}
