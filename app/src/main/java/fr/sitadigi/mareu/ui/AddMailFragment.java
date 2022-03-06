package fr.sitadigi.mareu.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    MaterialTextView mListeParticipant;
    TextInputLayout mAddParticipant;
    Button mBtnConfirmDate;
    Button mBtnAddReunion;
    List<Participant> mParticipants;
    MeetingApiServiceInterface mApiServiceInterface ;
    Spinner spinnerRoom, spinnerParticipant, spinnerDuration;
    List<Room> mRooms;
    String mDurationSelected;
    final  String SELECT_DURATION = "Select Duration";
    boolean isSelectDuration;
    public onButtonAddReunionListener mCallback;
    private List<Meeting> mMeetingLists;
    String nameParticipant="";
    String nameParticipantGlobal = nameParticipant;

    String participant1="";
    String participantGlobal1="";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar mCompareDate = new GregorianCalendar() ;
    private Calendar mCompareStartDate = new GregorianCalendar();
    private Calendar mCompareEndDate = new GregorianCalendar() ;
    private List<String> mDuration;
    private Room mRoomSelected;
    private List<Participant> mParticipantSelecteds = new ArrayList<>();

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
        mStartDate = view.findViewById(R.id.meetting_startDateLayout);

        mSubject = view.findViewById(R.id.meetting_subject);
        mListeParticipant = view.findViewById(R.id.list_participant);
        //mAddParticipant = view.findViewById(R.id.add_participant_text);
        mBtnAddReunion = view.findViewById(R.id.add_reunion);
        spinnerRoom = view.findViewById(R.id.spinner_room);
        spinnerParticipant = view.findViewById(R.id.spinner_participant);
        spinnerDuration= view.findViewById(R.id.spinner_time_duration);
        mApiServiceInterface = Injection.getService();


///SPINNER DURATION---------------------------
        mDuration = mApiServiceInterface.getDuration();
        if(!mDuration.get(0).equals(SELECT_DURATION)) {
            mApiServiceInterface.addInitialTextDuration();
        }
        ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,
                mDuration);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerDuration.setPrompt("Select duration11");
        this.spinnerDuration.setAdapter(adapterDuration);
        int lastIndex=0;
        spinnerDuration.setSelection(lastIndex);
        // When user select a List-Item.

        this.spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedHandler(adapterView, view, i, l);
                if(! adapterView.getItemAtPosition(i).equals(SELECT_DURATION)) {
                    mDurationSelected = spinnerDuration.getSelectedItem().toString();
                    isSelectDuration=true;
                    boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
                    if(bStartDate && mDurationSelected!=null) {
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

        //SPINNER DURATION FIN -------------------------------------

        // Spiner Participant

        mParticipants = mApiServiceInterface.getMailsParticipant();
        ArrayAdapter<Participant> adapterParticipant = new ArrayAdapter<Participant>(this.getActivity(),
                android.R.layout.simple_spinner_item,
                mParticipants);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapterParticipant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //this.spinnerParticipant.setPrompt("Select participant");
        this.spinnerParticipant.setAdapter(adapterParticipant);

        // When user select a List-Item.

        this.spinnerParticipant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedHandler(adapterView, view, i, l);
                //mParticipantSelecteds.add(spinnerParticipant.getSelectedItem());

               // String nameParticipant="";
                nameParticipant= spinnerParticipant.getSelectedItem().toString();
                //String nameParticipantGlobal ;
                nameParticipantGlobal = nameParticipantGlobal +" "+ nameParticipant;
                mListeParticipant.setText(nameParticipantGlobal);

                // mParticipantSelecteds = new Participant(1,spinnerParticipant.getSelectedItem().toString(),"mail");
                Log.e("TAG", "onItemSelected: " +spinnerParticipant.getSelectedItem().toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing

            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapterParticipant = adapterView.getAdapter();
                Participant mParticipant = (Participant) adapterParticipant.getItem(position);

                mParticipantSelecteds.add(mParticipant);
                for (int i=0; i< mParticipantSelecteds.size();i++) {
                    participant1 = mParticipantSelecteds.get(i).getNameParticipant();
                    participantGlobal1= participantGlobal1+ participant1;
                   // mListeParticipant.getEditText().setText(participantGlobal1);
                }
            }
        });

            init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }

    public void init() {
        mApiServiceInterface = Injection.getService();
        mMeetingLists = mApiServiceInterface.getMeeting();


        mBtnAddReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeetingLists= mApiServiceInterface.getMeeting();
                boolean bSubject = mSubject.getEditText().getText().toString().trim().length()>0 ;

                if(mMeetingLists!=null && mCompareStartDate!=null && mCompareEndDate!=null
                    && mRoomSelected!= null && bSubject && mParticipantSelecteds!= null
                && !mDurationSelected.equals(mDuration.get(0))){
            Meeting meeting = new Meeting(mMeetingLists.size(),mCompareStartDate,mCompareEndDate
                    ,mRoomSelected
                    , mSubject.getEditText().getText().toString()
                    ,mParticipantSelecteds);
            mApiServiceInterface.addMeeting(meeting);

                mCallback.OnButtonAddReunionClick(view);}


            }

        });

        // Set StartDate
        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setDateAndHour(mStartDate);
                mCompareStartDate = mCompareDate;
                Log.e("TAG", "onClick: mCompare est "+mCompareDate +" "+mCompareStartDate );


            }
        });

    }
    public void setDateAndHour(TextInputLayout field){
        Calendar date;
        //public void showDateTimePicker() {
        final Calendar currentDate = new GregorianCalendar();
        //date = Calendar.getInstance();
        date= new GregorianCalendar();
        new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                Log.e("TAG", "The choosen one Date " + date.getTime());

                int month = monthOfYear;//+1;

                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        String time = hourOfDay+" h "+minute;
                        String date1 = dayOfMonth+" / "+month+" / "+ year ;
                        //date.add(Calendar.MONTH, 5 );
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
                        //String mDateDebut = date1 + " à " +time;
                        field.getEditText().setText(dateFormat.format(date.getTime()));
                        mCompareDate.set(year, month, dayOfMonth,hourOfDay,minute);
                        //mTime.setText(time);
                        Log.e("TAG", "The choosen one mCompareDate " + mCompareDate.getTime());
                        boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;

                        //boolean bEndDate = mEndDate.getEditText().getText().toString().trim().length()>0 ;
                       // boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
                        if(bStartDate && mDurationSelected!=null) {
                            setEndDate();
                        }

                            availableRoom();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

    }

public void availableRoom(){
    mApiServiceInterface = Injection.getService();
    boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
    if(mCompareStartDate !=null && mCompareEndDate!=null
            && bStartDate && isSelectDuration) {
        mRooms = mApiServiceInterface.getAvailableRoom(mCompareStartDate, mCompareEndDate);
    }else mRooms= new ArrayList<>();
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
           // mRoomSelected = spinnerRoom.getSelectedItem();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //Nothing
           // mListeParticipant.getEditText().setText("Select Room");
        }

        private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
            Adapter adapter = adapterView.getAdapter();
            mRoomSelected = (Room) adapter.getItem(position);
        }
    });

    }
    public void createCallbackToParentActivity(){
        mCallback = (onButtonAddReunionListener)getActivity();
    }

    public void setEndDate(){
        // mCompareEndDate = new GregorianCalendar();
        if(!mDurationSelected.equals(mDuration.get(0))) {
            mCompareEndDate.setTime(mCompareStartDate.getTime());
        }
        if (mDurationSelected.equals(mDuration.get(1))) {
            //mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(2))) {
            //  mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
        }
        else if (mDurationSelected.equals(mDuration.get(3))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        }
        else if (mDurationSelected.equals(mDuration.get(4))) {
            //  mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        }
        else if (mDurationSelected.equals(mDuration.get(5))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        }
        else if (mDurationSelected.equals(mDuration.get(6))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
        }
        else if (mDurationSelected.equals(mDuration.get(7))) {
            // mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        }
        else if (mDurationSelected.equals(mDuration.get(8))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        }
        else if (mDurationSelected.equals(mDuration.get(9))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        }
        else if (mDurationSelected.equals(mDuration.get(10))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
        }
        else if (mDurationSelected.equals(mDuration.get(11))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        }
        else if (mDurationSelected.equals(mDuration.get(12))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        }
        else if (mDurationSelected.equals(mDuration.get(13))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        }
        else if (mDurationSelected.equals(mDuration.get(14))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 4);
        }
       // SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
        //mListeParticipant.setText(dateFormat.format(mCompareEndDate.getTime()));

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


