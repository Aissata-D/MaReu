package fr.sitadigi.mareu.ui;

import android.app.DatePickerDialog;
//import android.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class ListMeetingActivity extends AppCompatActivity implements AddMeetingFragment.onButtonAddReunionListener {

    private static final String TABLET = "TABLET";
    DatePickerDialog.OnDateSetListener setListener;
    MeetingApiServiceInterface mApiServiceInterface;
    Spinner spinnerRoom;
    ListMeetingFragment listMeetingFragment;
    private List<Room> mRooms;
    private List<Meeting> mMeetingListSelected;
    private Room mRoomSelected;
    private ListMeetingFragment mListMeetingFragment;
    private AddMeetingFragment mAddMeetingFragment;
    private DetailMeetingFragment mDetailMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragmentListMeetingOrAddMeetingOrDetailMeeting();
    }

    void showFragmentListMeetingOrAddMeetingOrDetailMeeting() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//Initialise fragment to get framLayout_add_or_detail according Fragment displayed
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_add_or_detail);
        if (fragment instanceof AddMeetingFragment) {
            mAddMeetingFragment = (AddMeetingFragment) fragment;
        } else {
            mDetailMeetingFragment = (DetailMeetingFragment) fragment;
        }
// Initialise fragment to get framLayout_list_meeting according Fragment displayed
        Fragment fragment1 = getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_list_meeting);
        if (fragment1 instanceof ListMeetingFragment) {
            listMeetingFragment = (ListMeetingFragment) fragment1;
        } else {
            mDetailMeetingFragment = (DetailMeetingFragment) fragment1;
        }

        if (findViewById(R.id.framLayout_add_or_detail) == null) {
            if (listMeetingFragment == null && mDetailMeetingFragment == null) {
                listMeetingFragment = new ListMeetingFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TELEPHONE");
                listMeetingFragment.setArguments(bundle);
                transaction.add(R.id.framLayout_list_meeting, listMeetingFragment);
            }

        } else {
            if (listMeetingFragment == null) {
                listMeetingFragment = new ListMeetingFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TABLET");
                listMeetingFragment.setArguments(bundle);
                transaction.add(R.id.framLayout_list_meeting, listMeetingFragment);
            }
            if (mAddMeetingFragment == null && mDetailMeetingFragment == null) {
                mAddMeetingFragment = new AddMeetingFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TABLET");
                mAddMeetingFragment.setArguments(bundle);
                transaction.add(R.id.framLayout_add_or_detail, mAddMeetingFragment);
            }
        }
        transaction.commit();
    }

    @Override
    public void OnButtonAddReunionClick(View view) {
        Intent ListMeetingActivity = new Intent(view.getContext(), ListMeetingActivity.class);
        startActivity(ListMeetingActivity);
        Log.e("TAG", "OnButtonAddReunionClick: Callback dans listmeeting!!!");

    }
}
