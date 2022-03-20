package fr.sitadigi.mareu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
//import android.app.Fragment;
import androidx.fragment.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.events.DeleteMeetingEvent;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TABLET = "TABLET";
    FloatingActionButton MainBtnAdd;
    public static List<Participant> mMailLists;
    public static List<Meeting> sMeetingLists;
    RecyclerView mRecyclerView;
    MeetingApiServiceInterface mApiServiceInterface;
    ReunionRecyclerViewAdapter reunionRecyclerViewAdapter;
    Calendar date = new GregorianCalendar();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Room> mRooms;
    private String mTablet;

    public ListMailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListMailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMailFragment newInstance() {
        ListMailFragment fragment = new ListMailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
                mTablet = getArguments().getString(TABLET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_mail, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        //Display menu in fragment
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mApiServiceInterface = Injection.getService();
        sMeetingLists = mApiServiceInterface.getMeeting();

        initRecyclerView();
    if ( mTablet != "TABLET") {
    MainBtnAdd = view.findViewById(R.id.floatingActionButtonAdd);
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

        return view;
    }

    public void initRecyclerView() {
        mMailLists = mApiServiceInterface.getMailsParticipant();
        ReunionRecyclerViewAdapter reunionRecyclerViewAdapter =
                new ReunionRecyclerViewAdapter((FragmentActivity) this.getActivity(), sMeetingLists,mTablet);
        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_by_date:
                filterByDate();
                return true;
            case R.id.filter_by_room:
                showAlertDialogFilterByRoom();
                return true;
            case R.id.filter_reset:
                resetDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetDate() {
        mApiServiceInterface = Injection.getService();
        List<Meeting> mListReset = mApiServiceInterface.getMeeting();
        reunionRecyclerViewAdapter = new ReunionRecyclerViewAdapter((FragmentActivity) this.getActivity(), mListReset,mTablet);
        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
        //
    }

    private void filterByDate() {

        final Calendar currentDate = new GregorianCalendar();
        new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                //date.set(year, monthOfYear, dayOfMonth);
                Log.e("TAG", "The choosen one Date " + date.getTime());
                //SET RECYCLERVIEW
                mApiServiceInterface = Injection.getService();
                List<Meeting> mListFilterByDate = mApiServiceInterface.filterByStartDay(date);
                reunionRecyclerViewAdapter =
                        new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByStartDay(date),mTablet);
                mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

        Log.e("TAG", "filterByDate: date " + date.getTime());
        Log.e("TAG", "filterByDate:currentDate " + currentDate.getTime());
    }

    private void showAlertDialogFilterByRoom() {
        final String SELECT_ROOM = "Select Room";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        mApiServiceInterface = Injection.getService();
        mRooms = mApiServiceInterface.getRoom();
        if (mRooms.get(0).getNameRoom().equals(SELECT_ROOM) ) {
            mApiServiceInterface.removeRoom(mRooms.get(0));
        }


        alertDialog.setTitle("Select room");

        String[] items = {mRooms.get(0).getNameRoom(), mRooms.get(1).getNameRoom(), mRooms.get(2).getNameRoom(),
                mRooms.get(3).getNameRoom(), mRooms.get(4).getNameRoom(), mRooms.get(5).getNameRoom(),
                mRooms.get(6).getNameRoom(), mRooms.get(7).getNameRoom(), mRooms.get(8).getNameRoom(), mRooms.get(9).getNameRoom()};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(0)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 1:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(1)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 2:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(2)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 3:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(3)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 4:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(4)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 5:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(5)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 6:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(6)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 7:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(7)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 8:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(8)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                    case 9:
                        reunionRecyclerViewAdapter =
                                new ReunionRecyclerViewAdapter((FragmentActivity) getActivity(), mApiServiceInterface.filterByRoom(mRooms.get(9)),mTablet);
                        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);
                        dialog.dismiss();
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void onDeleteReunion(DeleteMeetingEvent event) {
        mApiServiceInterface.removeMeeting(event.mMeeting);
        initRecyclerView();
    }



}