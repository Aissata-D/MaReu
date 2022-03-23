package fr.sitadigi.mareu.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class SetDurationAndMeetingEndDate {
    final String SELECT_DURATION = "Select Duration";
    final String SELECT_ROOM = "Select Room";
    MeetingApiServiceInterface mApiServiceInterface;
    boolean isSelectDuration;
    private Activity mActivity;
    private Spinner spinnerDuration;
    private List<Room> mRooms;
    private Spinner spinnerRoom;
    private MaterialTextView mStartDate;
    private Calendar mCompareDate = new GregorianCalendar();
    private Calendar mCompareStartDate = new GregorianCalendar();
    private Calendar mCompareEndDate = new GregorianCalendar();
    private String mDurationSelected;
    private List<String> mDuration;
    private Context mContext;
    private Room mRoomChoice;


    public SetDurationAndMeetingEndDate(MeetingApiServiceInterface apiServiceInterface
            , Activity activity, Spinner spinnerDuration, List<Room> rooms, Spinner spinnerRoom
            , MaterialTextView startDate, Calendar compareStartDate, Calendar compareEndDate
            , Calendar compareDate, List<String> duration, Context context) {

        mApiServiceInterface = apiServiceInterface;
        mActivity = activity;
        this.spinnerDuration = spinnerDuration;
        mRooms = rooms;
        this.spinnerRoom = spinnerRoom;
        mStartDate = startDate;
        mCompareStartDate = compareStartDate;
        mCompareEndDate = compareEndDate;
        mCompareDate = compareDate;
        mDuration = duration;
        mContext = context;
    }

    public List<Room> availableRoom() {

        mApiServiceInterface = Injection.getService();
        List<Room> mRoomsGlobal = mApiServiceInterface.getRoom();
        if (!mRoomsGlobal.get(0).getNameRoom().equals(SELECT_ROOM)) {
            mApiServiceInterface.addInitialTextRoom();
        }
        boolean bStartDate = mStartDate.getText().toString().trim().length() > 0;

        if (mCompareStartDate != null && mCompareEndDate != null
                && bStartDate && isSelectDuration) {
            mRooms = mApiServiceInterface.getAvailableRoom(mCompareStartDate, mCompareEndDate);
        } else mRooms = new ArrayList<>();
        ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(mActivity,
                android.R.layout.simple_spinner_item,
                mRooms);
        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerRoom.setAdapter(adapter);
        // When user select a List-Item.
        this.spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedHandler(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing
            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                mRoomChoice = mRooms.get(position);
            }
        });
        return mRooms;
    }

    public Room getRoomChoice() {
        return mRoomChoice;
    }

    public void spinnerDuration() {
        ///SPINNER DURATION---------------------------
        mApiServiceInterface = Injection.getService();
        mDuration = mApiServiceInterface.getDuration();
        if (!mDuration.get(0).equals(SELECT_DURATION)) {
            mApiServiceInterface.addInitialTextDuration();
        }
        ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_spinner_item,
                mDuration);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // this.spinnerDuration.setPrompt("Select \n duration11");
        this.spinnerDuration.setAdapter(adapterDuration);
        int lastIndex = 0;
        spinnerDuration.setSelection(lastIndex);
        // When user select a List-Item.

        this.spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedHandler(adapterView, view, i, l);

                if (!adapterView.getItemAtPosition(i).equals(SELECT_DURATION)) {
                    mDurationSelected = spinnerDuration.getSelectedItem().toString();
                    isSelectDuration = true;
                    boolean bStartDate = mStartDate.getText().toString().trim().length() > 0;
                    if (bStartDate && mDurationSelected != null) {
                        setEndDate();
                    }
                }
                availableRoom();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing
            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapterDuration = adapterView.getAdapter();
                String mDuration = (String) adapterDuration.getItem(position);
            }
        });
    }

    public String getDurationSelected() {
        return mDurationSelected;
    }

    //Method to set meeting start date and his endtime
    public void setEndDate() {
        mDuration = mApiServiceInterface.getDuration();
        if (!mDurationSelected.equals(mDuration.get(0))) {
            mCompareEndDate.setTime(mCompareStartDate.getTime());
        }
        if (mDurationSelected.equals(mDuration.get(1))) {
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(2))) {
            mCompareEndDate.add(Calendar.HOUR, 1);
        } else if (mDurationSelected.equals(mDuration.get(3))) {
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(4))) {
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(5))) {
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(6))) {
            mCompareEndDate.add(Calendar.HOUR, 2);
        } else if (mDurationSelected.equals(mDuration.get(7))) {
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(8))) {
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(9))) {
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(10))) {
            mCompareEndDate.add(Calendar.HOUR, 3);
        } else if (mDurationSelected.equals(mDuration.get(11))) {
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(12))) {
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(13))) {
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(14))) {
            mCompareEndDate.add(Calendar.HOUR, 4);
        }
    }

    // Set Date and hour methode
    public void setDateAndHour(MaterialTextView field) {
        Calendar date;
        final Calendar currentDate = new GregorianCalendar();
        date = new GregorianCalendar();
        new DatePickerDialog(this.mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                int month = monthOfYear;
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
                        field.setText(dateFormat.format(date.getTime()));
                        mCompareDate.set(year, month, dayOfMonth, hourOfDay, minute);
                        boolean bStartDate = mStartDate.getText().toString().trim().length() > 0;
                        if (bStartDate && mDurationSelected != null) {
                            setEndDate();
                        }
                        availableRoom();
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
