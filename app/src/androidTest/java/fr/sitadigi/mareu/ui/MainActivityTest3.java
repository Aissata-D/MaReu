package fr.sitadigi.mareu.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.sitadigi.mareu.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingActionButtonAdd),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_main_recyclerview),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.meetting_subject),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Sujet test"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.meetting_startDateInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meetting_startDateLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_time_duration),
                        childAtPosition(
                                allOf(withId(R.id.add_mail_fragment),
                                        childAtPosition(
                                                withId(R.id.framLayout_add_mail),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_participant),
                        childAtPosition(
                                allOf(withId(R.id.add_mail_fragment),
                                        childAtPosition(
                                                withId(R.id.framLayout_add_mail),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_room),
                        childAtPosition(
                                allOf(withId(R.id.add_mail_fragment),
                                        childAtPosition(
                                                withId(R.id.framLayout_add_mail),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.title_detail), withText("DETAILS DE LA REUNION"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("DETAILS DE LA REUNION")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
