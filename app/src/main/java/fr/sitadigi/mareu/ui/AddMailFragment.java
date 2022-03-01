package fr.sitadigi.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

//import android.app.Fragment;
import androidx.fragment.app.Fragment;

//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    String lisParticipant ="";
    String lisParticipantGlobal= lisParticipant;

    //Spinner mPlace;
    TextInputLayout mEndDate;
    TextInputLayout mStartDate;
    TextInputLayout mSubject;
    TextInputLayout mListeParticipant;
    TextInputLayout mAddParticipant;
    Button mBtnConfirmDate;
    Button mBtnAddReunion;
    List<Participant> mParticipants;
    MeetingApiServiceInterface mApiServiceInterface ;
    Spinner spinnerRoom, spinnerParticipant;
    List<Room> mRooms;



    public onButtonAddReunionListener mCallback;
    private List<Meeting> mMeetingLists;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar mCompareDate = Calendar.getInstance();
    private Calendar mCompareStartDate;
    private Calendar mCompareEndDate ;

    public AddMailFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AddMailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMailFragment newInstance() {
        AddMailFragment fragment = new AddMailFragment();
        Bundle args = new Bundle();
      //  args.putString(ARG_PARAM1, param1);
      //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public interface onButtonAddReunionListener {
        void OnButtonAddReunionClick(View view);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mail, container, false);
      //  mPlace = view.findViewById(R.id.spinner_room);
        mEndDate = view.findViewById(R.id.meetting_endDateLayout);
        mStartDate = view.findViewById(R.id.meetting_startDateLayout);

        mSubject = view.findViewById(R.id.meetting_subject);
        mListeParticipant = view.findViewById(R.id.list_participant);
        //mAddParticipant = view.findViewById(R.id.add_participant_text);
        mBtnConfirmDate   = view.findViewById(R.id.confirm_date);
        mBtnAddReunion = view.findViewById(R.id.add_reunion);
        spinnerRoom = view.findViewById(R.id.spinner_room);
        spinnerParticipant = view.findViewById(R.id.spinner_participant);



        // Spiner Participant
        mApiServiceInterface = Injection.getService();

        mParticipants = mApiServiceInterface.getMailsParticipant();
        ArrayAdapter<Participant> adapterParticipant = new ArrayAdapter<Participant>(this.getActivity(),
                android.R.layout.simple_spinner_item,
                mParticipants);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapterParticipant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerParticipant.setPrompt("Select participant");
        this.spinnerParticipant.setAdapter(adapterParticipant);

        // When user select a List-Item.

        this.spinnerParticipant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedHandler(adapterView, view, i, l);
                spinnerParticipant.getSelectedItem().toString();

                mListeParticipant.getEditText().setText(spinnerParticipant.getSelectedItem().toString());
                Log.e("TAG", "onItemSelected: " +spinnerParticipant.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing

            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapterParticipant = adapterView.getAdapter();
                Participant mParticipant = (Participant) adapterParticipant.getItem(position);
            }
        });

            init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //createCallbackToParentActivity();
    }

    public void init() {
        mApiServiceInterface = Injection.getService();
        mMeetingLists = mApiServiceInterface.getMeeting();


        mBtnAddReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           // Meeting meeting = new Meeting(2,mStartDate,mEndDate,)

                mCallback.OnButtonAddReunionClick(view);

            }

        });
        //Set year; month and day
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        // Set hour and minute
        int Hour = calendar.get(calendar.HOUR_OF_DAY);
        int Minute = calendar.get(calendar.MINUTE);


        // Set StartDate
        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setDateAndHour(mStartDate);
                mCompareStartDate = mCompareDate;
                Log.e("TAG", "onClick: mCompare est "+mCompareDate +" "+mCompareStartDate );

            }
        });
        // Set EndDate
            mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateAndHour(mEndDate);
                mCompareEndDate = mCompareDate;
                Log.e("TAG", "onClick: mCompare est "+mCompareDate );
            }
        });

        mBtnConfirmDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
                boolean bEndDate = mEndDate.getEditText().getText().toString().trim().length()>0 ;
                if(mCompareStartDate !=null && mCompareEndDate!=null
                        && bStartDate && bEndDate) {
                    mRooms = mApiServiceInterface.getAvailableRoom(mCompareStartDate, mCompareEndDate);
                }else mRooms= new ArrayList<>();
                availableRoom();

            }
        });

    }
    public void setDateAndHour(TextInputLayout field){
        Calendar date;
        //public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                int month = monthOfYear+1;
                //  String date1 = dayOfMonth+" / "+month+" / "+ year ;
                //mDate.getEditText().setText(date1);
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        String time = hourOfDay+" h "+minute;
                        String date1 = dayOfMonth+" / "+month+" / "+ year ;

                        String mDateDebut = date1 + " à " +time;
                        field.getEditText().setText(mDateDebut);
                        mCompareDate.set(year, month, dayOfMonth,hourOfDay,minute);
                        //mTime.setText(time);
                        Log.e("TAG", "The choosen one " + mCompareDate);
                        boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
                        boolean bEndDate = mEndDate.getEditText().getText().toString().trim().length()>0 ;
                        if(mCompareStartDate !=null && mCompareEndDate!=null
                                && bStartDate && bEndDate) {
                            mRooms = mApiServiceInterface.getAvailableRoom(mCompareStartDate, mCompareEndDate);
                        }else mRooms= new ArrayList<>();
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

    }

public void availableRoom(){
    mApiServiceInterface = Injection.getService();
   // mRooms= new ArrayList<>();
    ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(this.getActivity(),
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
            mListeParticipant.getEditText().setText("Select Room");
        }

        private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
            Adapter adapter = adapterView.getAdapter();
            Room mRooms = (Room) adapter.getItem(position);
        }
    });
}
    public void createCallbackToParentActivity(){
        mCallback = (onButtonAddReunionListener)getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
 /*
 public void setDateAndTime(){


    new SlideDateTimePicker.Builder(this.getActivity().getSupportFragmentManager())//  getSupportFragmentManager())
            .setListener(listener)
            .setInitialDate(new Date())
            .build()
            .show();
}

 SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {

            // Do something with the date. This Date object contains
            // the date and time that the user has selected.
            //include ':slideDateTimePicker'
        }

        @Override
        public void onDateTimeCancel()
        {
            // Overriding onDateTimeCancel() is optional.
        }
    };
*/
 /*   mParticipants = new ArrayList<>();
        mBtnAddMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participant participant= mApiServiceInterface.getMailsParticipant().get(0);
                mParticipants.add(participant);
                for(int i = 0; i < mParticipants.size(); i++){
                    lisParticipant = mParticipants.get(i).getAdresseMail();
                }
                lisParticipantGlobal = lisParticipantGlobal +" , "+ lisParticipant;
                mListeParticipant.getEditText().setText(lisParticipantGlobal);
                Log.e("TAG", "onClick: Addmail "+lisParticipantGlobal+" dans le fragment" );
            }
        });
*/

/* Meeting meeting = new Meeting(mApiServiceInterface.getMeeting().size()
                        ,mTime.getText(),
                        mDate.getEditText().getText()
                        ,mApiServiceInterface.getRoom().get(0)
                        ,mSubject.getEditText().getText().toString()
                        , mParticipants);
                mApiServiceInterface.addMeeting(meeting);

                Log.e("TAG", "onClick: Addreunion "
                        +"Sujet "+ meeting.getId() +" Lieu "+ meeting.getRoom() + " dans le fragment" );
*/

 /* // setDateAndTime();
                Log.e("TAG", "onClick: mDate" );

                //SET TIME

                boolean is24HourView=true;
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext()
                        ,R.style.Widget_AppCompat_ActionBar,timeSetListener,Hour,Minute,is24HourView);
                timePickerDialog.show();
                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String heure_minute = i+ "h" + i1;
                        mTime.setText(heure_minute);

                    }
                };
                //SET DATE
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext()
                        ,R.style.Widget_AppCompat_ActionBar,setListener,Year,Month,Day);
                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int jour = i2;
                        int mois = i1+1;
                        int annee = i;
                        String date = jour +" - "+ mois +" - "+annee;
                        //mDate = date;
                        Log.e("TAG", "onDateSet: " +date );
                        mDate.getEditText().setText(date);

                    }
                };
*/


