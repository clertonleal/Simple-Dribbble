package com.clertonleal.dribbble.activity;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.clertonleal.dribbble.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openShotDetail() {
        onView(withContentDescription("Populares")).check(matches(isDisplayed()));
        onView(withContentDescription("Populares")).perform(RecyclerViewActions.scrollToPosition(9));
        onView(withContentDescription("Populares")).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
    }

    @Test
    public void openZoomImage() {
        openShotDetail();
        onView(withId(R.id.image_dribble)).perform(click());
    }
}
