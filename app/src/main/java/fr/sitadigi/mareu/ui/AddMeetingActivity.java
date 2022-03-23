package fr.sitadigi.mareu.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import fr.sitadigi.mareu.R;

public class AddMeetingActivity extends AppCompatActivity implements AddMeetingFragment.onButtonAddReunionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AddMeetingFragment addMeetingFragment = (AddMeetingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_add_meeting);
        if (addMeetingFragment == null) {
            addMeetingFragment = new AddMeetingFragment();
            transaction.add(R.id.framLayout_add_meeting, addMeetingFragment); //give your fragment container id in first parameter
        } else {
            transaction.show(addMeetingFragment);
        }
        transaction.commit();
    }

    // Answer to the callback create in AddMeetingFragment
    @Override
    public void OnButtonAddReunionClick(View view) {
        finish();
    }
}