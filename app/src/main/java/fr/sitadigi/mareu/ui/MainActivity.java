package fr.sitadigi.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton MainBtnAdd;
    DatePickerDialog.OnDateSetListener setListener;
    MeetingApiServiceInterface mApiServiceInterface;
    Spinner spinnerRoom;
    private List<Room> mRooms;
    private List<Meeting> mMeetingListSelected;
    private Room mRoomSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainBtnAdd = findViewById(R.id.floatingActionButtonAdd);
//        mMeetingListSelected = mApiServiceInterface.getMeeting();

        showFragmentListMail();
        MainBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //REPLACE FRAGMENT
                //AddMailFragment.newInstance();
               /* AddMailFragment addMailFragment = new AddMailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_list_mail, addMailFragment ); //give your fragment container id in first parameter
                transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
                transaction.commit();
                Log.d("TAG", "onClick: fragment lancer");*/
                Intent addMailActivity = new Intent(view.getContext(), AddMailActivity.class);
                startActivity(addMailActivity);
            }
        });
    }

    void showFragmentListMail() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ListMailFragment listMailFragment = (ListMailFragment) getFragmentManager()
                .findFragmentById(R.id.framLayout_list_mail);
        if (listMailFragment == null) {
            listMailFragment = new ListMailFragment();

            transaction.add(R.id.framLayout_list_mail, listMailFragment);

        } else transaction.show(listMailFragment);
        //give your fragment container id in first parameter
        transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        transaction.commit();
        Log.d("TAG", "onClick: fragment lancer");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();


    }


    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            spinnerRoom = findViewById(R.id.filter_by_room);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.filter_by_date:
                    filterByDate();
                    return true;
                case R.id.filter_by_room:
                    showAlertDialog();
                    return true;
                case R.id.filter_reset:
                    resetDate();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    */
    private void resetDate() {
    }

    private void filterByRoom() {
        mApiServiceInterface = Injection.getService();

    }


}
