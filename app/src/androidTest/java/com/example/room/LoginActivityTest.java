package com.example.room;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.room.model.User;
import com.example.room.repository.UserRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void should_login_successfully_when_given_correct_user_info(){
        User user = new User("android","123456");
        user.setId(1);
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = applicationContext.getUserRepository();
        when(userRepository.findByUserName("android")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));

        onView(withId(R.id.login_edit_username)).perform(typeText("android"),closeSoftKeyboard());
        onView(withId(R.id.login_edit_password)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.login_button_login)).perform(click());

        onView(withText("Login successfully")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_login_failed_when_given_incorrect_password(){
        User user = new User("android","123456");
        user.setId(1);
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = applicationContext.getUserRepository();
        when(userRepository.findByUserName("android")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));

        onView(withId(R.id.login_edit_username)).perform(typeText("android"),closeSoftKeyboard());
        onView(withId(R.id.login_edit_password)).perform(typeText("12"),closeSoftKeyboard());
        onView(withId(R.id.login_button_login)).perform(click());

        onView(withText("Password is invalid")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_login_failed_when_given_incorrect_username(){
        User user = new User("android","123456");
        user.setId(1);
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserRepository userRepository = applicationContext.getUserRepository();
        when(userRepository.findByUserName("xxxx")).thenReturn(new MaybeCreate(emitter -> emitter.onComplete()));

        onView(withId(R.id.login_edit_username)).perform(typeText("xxxx"),closeSoftKeyboard());
        onView(withId(R.id.login_edit_password)).perform(typeText("12"),closeSoftKeyboard());
        onView(withId(R.id.login_button_login)).perform(click());

        onView(withText("Username does not exist")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_login_failed_when_given_invalid_user_info(){
        User user = new User("android","123456");
        user.setId(1);

        onView(withId(R.id.login_edit_username)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.login_edit_password)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.login_button_login)).perform(click());

        onView(withText("Invalid user info")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}
