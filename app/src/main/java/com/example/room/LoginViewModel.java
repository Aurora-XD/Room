package com.example.room;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.room.Result.LOGIN_SUCCESSFULLY;
import static com.example.room.Result.PASSWORD_INVALID;
import static com.example.room.Result.USER_NAME_NOT_EXIST;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult;
    private MutableLiveData<Boolean> insertResult;
    private MutableLiveData<Boolean> isUserInfoInvalid;
    private UserRepository userRepository = new UserRepository();
    private CompositeDisposable compositeDisposable;

    public LoginViewModel() {
        compositeDisposable = new CompositeDisposable();
        loginResult = new MutableLiveData<>();
        insertResult = new MutableLiveData<>();
        isUserInfoInvalid = new MutableLiveData<>();
    }

    public void observeInsertResult(@NonNull LifecycleOwner owner, @NonNull Observer<Boolean> observer){
        insertResult.observe(owner, observer);
    }

    public void observeUserInfoIsInvalid(@NonNull LifecycleOwner owner, @NonNull Observer<Boolean> observer){
        isUserInfoInvalid.observe(owner, observer);
    }

    public void observeLoginResult(@NonNull LifecycleOwner owner, @NonNull Observer<LoginResult> observer){
        loginResult.observe(owner, observer);
    }

    public void insertUser() {
        Disposable insertDisposable = userRepository.saveUser(new User("android", "123456"))
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        insertResult.postValue(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        insertResult.postValue(false);
                    }
                });
        compositeDisposable.add(insertDisposable);
    }

    public void login(String name,String password) {
        if(!checkUserInfo(name,password)){
            return;
        }
        Disposable loginDisposable = userRepository.findByUserName(name).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        LoginResult result = new LoginResult();
                        Log.d("TAG", "accept: " + user.getUsername() + user.getPassword());
                        if (user.getPassword().equals(password)) {
                            result.setResult(LOGIN_SUCCESSFULLY);
                        } else {
                            result.setResult(PASSWORD_INVALID);
                        }
                        loginResult.postValue(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LoginResult result = new LoginResult();
                        result.setResult(USER_NAME_NOT_EXIST);
                        loginResult.postValue(result);
                    }
                });
        compositeDisposable.add(loginDisposable);
    }

    public boolean checkUserInfo(String username, String password) {
        boolean isInvalid = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
        isUserInfoInvalid.postValue(isInvalid);
        Log.d("TAG", "checkUserInfo: "+isInvalid);
        return isInvalid;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
