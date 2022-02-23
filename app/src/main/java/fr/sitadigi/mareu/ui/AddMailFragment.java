package fr.sitadigi.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.service.ReunionApiServiceInterface;

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

    TextInputLayout mPlace;
    TextInputEditText mTime;
    TextInputLayout mDate;
    TextInputLayout mSubject;
    TextInputLayout mListeParticipant;
    TextInputLayout mAddParticipant;
    Button mBtnAddMail;
    Button mBtnAddReunion;
    List<Participant> mParticipants;
    ReunionApiServiceInterface mApiServiceInterface ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public onButtonAddReunionListener mCallback;
    private List<Meeting> mMeetingLists;

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
        mPlace = view.findViewById(R.id.meetting_place);
        mTime = view.findViewById(R.id.meetting_time1);
        mDate = view.findViewById(R.id.meetting_date);

        mSubject = view.findViewById(R.id.meetting_subject);
        mListeParticipant = view.findViewById(R.id.list_participant);
        mAddParticipant = view.findViewById(R.id.add_participant_text);
        mBtnAddMail = view.findViewById(R.id.add_mail);
        mBtnAddReunion = view.findViewById(R.id.add_reunion);
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
        mMeetingLists = mApiServiceInterface.getReunion();

        mParticipants = new ArrayList<>();
        mBtnAddMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participant participant = new Participant(mAddParticipant.getEditText().getText().toString());
                mParticipants.add(participant);
                for(int i = 0; i < mParticipants.size(); i++){
                    lisParticipant = mParticipants.get(i).getMailParticipant();
                }
                lisParticipantGlobal = lisParticipantGlobal +" , "+ lisParticipant;
                mListeParticipant.getEditText().setText(lisParticipantGlobal);
                Log.e("TAG", "onClick: Addmail "+lisParticipantGlobal+" dans le fragment" );
            }
        });

        mBtnAddReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting meeting = new Meeting(mPlace.getEditText().getText().toString()
                        ,mSubject.getEditText().getText().toString(), mTime.getText().toString()
                        , mParticipants);
                mApiServiceInterface.addReunion(meeting);

                Log.e("TAG", "onClick: Addreunion "
                        +"Sujet "+ meeting.getSujet() +" Lieu "+ meeting.getLieu() + " dans le fragment" );

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

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: mDate" );
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext()
                        ,R.style.Widget_AppCompat_ActionBar,setListener,Year,Month,Day);
                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }


        });
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

        // TimePicker
        mTime.setOnClickListener(new View.OnClickListener() {
            boolean is24HourView=true;
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext()
                        ,R.style.Widget_AppCompat_ActionBar,timeSetListener,Hour,Minute,is24HourView);
                timePickerDialog.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String heure_minute = i+ "h" + i1;
                mTime.setText(heure_minute);
            }
        };
    }

    public void createCallbackToParentActivity(){
        mCallback = (onButtonAddReunionListener)getActivity();
    }
}