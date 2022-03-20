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
import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class MainActivity extends AppCompatActivity implements AddMailFragment.onButtonAddReunionListener {

    private static final String TABLET = "TABLET" ;
    DatePickerDialog.OnDateSetListener setListener;
    MeetingApiServiceInterface mApiServiceInterface;
    Spinner spinnerRoom;
    private List<Room> mRooms;
    private List<Meeting> mMeetingListSelected;
    private Room mRoomSelected;
    private ListMailFragment mListMailFragment;
    private AddMailFragment mAddMailFragment;
    private  DetailMeetingFragment mDetailMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mMeetingListSelected = mApiServiceInterface.getMeeting();
       // Injection.getNewInstanceApiService();
        showFragmentListMail();
      // showFragmentDetailsMeetingOrAddMeetingFragment();

    }

    void showFragmentListMail() {
       // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_add_or_detail);
        if(fragment instanceof AddMailFragment) {
            mAddMailFragment = (AddMailFragment) fragment;
        }else {
            mDetailMeetingFragment = (DetailMeetingFragment)fragment;
        }

        ListMailFragment listMailFragment = (ListMailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_list_mail);
        if(findViewById(R.id.framLayout_add_or_detail) ==null){
            if (listMailFragment == null ) {
                listMailFragment = new ListMailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TELEPHONE");
                listMailFragment.setArguments(bundle);

                transaction.add(R.id.framLayout_list_mail, listMailFragment);

            } //else transaction.show(listMailFragment);

        }else {
            if (listMailFragment == null ) {
                listMailFragment = new ListMailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TABLET");
                listMailFragment.setArguments(bundle);

                transaction.add(R.id.framLayout_list_mail, listMailFragment);

            } //else transaction.show(listMailFragment);

            if (mAddMailFragment == null && mDetailMeetingFragment==null) {

                mAddMailFragment = new AddMailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TABLET, "TABLET");
               mAddMailFragment.setArguments(bundle);

                transaction.add(R.id.framLayout_add_or_detail, mAddMailFragment);


            }//else transaction.show(mAddMailFragment);

        }

        //give your fragment container id in first parameter
        //transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        //transaction.addToBackStack(null);
        transaction.commit();
         //else transaction.show(mAddMailFragment);
        //give your fragment container id in first parameter
        // transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        //transaction.commit();
    }
    void showFragmentDetailsMeetingOrAddMeetingFragment() {


      // mDetailMeetingFragment = (DetailMeetingFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.framLayout_add_or_detail);



    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();


    }*/

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


    @Override
    public void OnButtonAddReunionClick(View view) {
       // if ( mTablet != "TABLET") {
           // showFragmentListMail();
        Intent MainActivity = new Intent(view.getContext(), MainActivity.class);
        startActivity(MainActivity);
            Log.e("TAG", "OnButtonAddReunionClick: MainActivity; Callback reussi!!!");
            //finish();
      //  }
    }
}
