package fr.sitadigi.mareu.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AllActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityTestRule = new ActivityTestRule<>(ListMeetingActivity.class);
    MeetingApiServiceInterface service;
    private ListMeetingActivity mActivity;

    // Class add by espresso record
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
    // End of Class add by espresso record

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
        service = Injection.getNewInstanceApiService();
    }

    /**
     * Verifie that recyclerview is not empty when apllication is lunch
     */
    @Test
    public void MainActivity_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.recyclerview), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }


    @Test
    public void meetingItemList_click_shouldOpenDetailActivity() {
        Meeting meeting = service.getMeeting().get(1);

        //On performe un clic à la position 1 sur la vue recyclerview de list_mail
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // Meeting Detail sreen is display
        onView(ViewMatchers.withId(R.id.meeting_detail))
                .check(matches(isDisplayed()));
        //verifie that field sujet_detail is correct
        onView(ViewMatchers.withId(R.id.sujet_detail))
                .check(matches(withText("Sujet de la réunion : " + meeting.getSubject())));
        //verifie that field salle_detail is correct
        onView(ViewMatchers.withId(R.id.salle_detail))
                .check(matches(withText("Salle de la réunion : " + meeting.getRoom().getNameRoom())));


    }

    @Test
    public void addButton_click_shouldOpenAddMeetingFragment() {
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.floatingActionButtonAdd), isDisplayed()))
                .perform(click());
        // Add new Meeting  sreen is display
        onView(ViewMatchers.withId(R.id.add_mail_fragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void field_to_add_meeting_shouldNotBeEmpty() {
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.floatingActionButtonAdd), isDisplayed()))
                .perform(click());

        onView(ViewMatchers.withId(R.id.add_reunion))
                .perform(click());

        onView(ViewMatchers.withId(R.id.add_mail_fragment))
                .check(matches(isDisplayed()));


    }

    @Test
    public void btnAddMeeting_run() {
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.floatingActionButtonAdd), isDisplayed()))
                .perform(click());

        //  ViewInteraction textInputEditText =
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.meetting_subject1),
                isDisplayed())).perform(replaceText("Sujet test"), closeSoftKeyboard());

        // clic datePicker
        ViewInteraction textInputEditText2 = onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.meetting_startDateTv),
                isDisplayed()));
        textInputEditText2.perform(click());
        //Select current time
        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());
        //Select current time
        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));

        materialButton2.perform(scrollTo(), click());

        // click spiner duration
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.spinner_time_duration), isDisplayed()))
                .perform(click());


        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());


        // Add Room

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_room),
                        childAtPosition(
                                allOf(withId(R.id.add_mail_fragment),
                                        childAtPosition(
                                                withId(R.id.framLayout_add_meeting),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        // Add Participant
        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.tv_add_participant),
                        childAtPosition(
                                allOf(withId(R.id.add_mail_fragment),
                                        childAtPosition(
                                                withId(R.id.framLayout_add_meeting),
                                                0)),
                                5),
                        isDisplayed()));
        materialTextView2.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(0);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());


        // Click on add meeting button
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.add_reunion), isDisplayed()))
                .perform(click());
        // perform(replaceText("Sujet test"), closeSoftKeyboard());

        // Back on the screm   List Meeting fragmen ==> it is display
        onView(ViewMatchers.withId(R.id.recyclerview))
                .check(matches(isDisplayed()));

    }

    @Test
    public void clickOnMenu_run() {

        onView(allOf(withContentDescription("More options"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.action_bar),
                                1),
                        0),
                isDisplayed())).perform(click());

        onView(
                allOf(withId(R.id.title), withText("Filter by date"),
                        withParent(withParent(withId(R.id.content))),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.title), withText("Filter by room"),
                withParent(withParent(withId(R.id.content))),
                isDisplayed())).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.title), withText("Filter reset"),
                withParent(withParent(withId(R.id.content))),
                isDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnItemMenu_filterByRoom_run() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Filter by room"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(0);
        appCompatCheckedTextView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview),
                        withParent(withParent(withId(R.id.fragment_main_recyclerview))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));
    }

    @Test
    public void clickOnItemMenu_filterByDate_run() {
        onView(allOf(withContentDescription("More options"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.action_bar),
                                1),
                        0),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.title), withText("Filter by date"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.content),
                                0),
                        0),
                isDisplayed())).perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview),
                        withParent(withParent(withId(R.id.fragment_main_recyclerview))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));


    }

    @Test
    public void clickOnItemMenu_filterReset_run() {

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Filter reset"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview),
                        withParent(withParent(withId(R.id.fragment_main_recyclerview))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));
    }

    @Test
    public void clickOn_deleteButton_shouldRemoveItem() {

        ViewInteraction textView = onView(
                allOf(withId(R.id.all_text), withText("Sujet 1 - 15h10 - Salle 1"),
                        withParent(withParent(withId(R.id.recyclerview))),
                        isDisplayed()));
        //  textView.check(matches(withText("Sujet 1 - 15h10 - Salle 1")));
        // When perform a click on a delete icon
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        //Verifie that view is delete
        ViewInteraction textView1 = onView(
                allOf(withId(R.id.all_text), withText("Sujet 1 - 15h10 - Salle 1"),
                        withParent(withParent(withId(R.id.recyclerview))),
                        isDisplayed()));
        textView1.check(doesNotExist());
    }
}




