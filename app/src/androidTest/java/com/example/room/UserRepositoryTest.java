package com.example.room;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.room.model.User;
import com.example.room.repository.UserDatabase;
import com.example.room.repository.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {

    @Rule
    //可以将默认使用的后台executor转为同步执行，让测试可以马上获得结果
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private UserRepository userRepository;

    private UserDatabase userDatabase;

    @Before
    public void setUp() throws Exception {
        userDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                UserDatabase.class).build();

        userRepository = new UserRepository(userDatabase.getUserDao());
    }

    @Test
    public void should_find_user_by_name() {
        User android = new User("android", "111");
        android.setId(1);
        userDatabase.getUserDao().saveUser(android).subscribeOn(Schedulers.io()).subscribe();
        userRepository.findByUserName("android")
                .test()
                .assertValue(user -> user.getId() == android.getId());
    }

}
