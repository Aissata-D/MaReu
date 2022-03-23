package fr.sitadigi.mareu.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;
import fr.sitadigi.mareu.utils.SetDurationAndMeetingEndDate;
import fr.sitadigi.mareu.utils.ShowAlertDialogAddParticipant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMeetingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String TABLET = "TABLET";

    public onButtonAddReunionListener mCallback;
    MaterialTextView mStartDate;
    MaterialTextView mAddParticipant;
    TextInputLayout mSubject;
    TextInputEditText mSubjectInput;
    MaterialTextView mListeParticipant;
    Button mBtnAddReunion;
    MeetingApiServiceInterface mApiServiceInterface;
    Spinner spinnerRoom, spinnerDuration;
    List<Room> mRooms;
    private List<Meeting> mMeetingLists;
    // TODO: Rename and change types of parameters
    private Calendar mCompareDate = new GregorianCalendar();
    private Calendar mCompareStartDate = new GregorianCalendar();
    private Calendar mCompareEndDate = new GregorianCalendar();
    private List<String> mDuration;
    private Room mRoomSelected ;
    private List<Participant> mParticipantSelecteds = new ArrayList<>();
    private String mTablet;
    ShowAlertDialogAddParticipant mShowAlertDialogAddParticipant;
    SetDurationAndMeetingEndDate mSetDurationAndMeetingEndDate;


    public AddMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMeetingFragment newInstance() {
        AddMeetingFragment fragment = new AddMeetingFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mail, container, false);
        mStartDate = view.findViewById(R.id.meetting_startDateTv);
        mSubject = view.findViewById(R.id.meetting_subject);
        mSubjectInput = view.findViewById(R.id.meetting_subject1);
        mListeParticipant = view.findViewById(R.id.list_participant);
        mBtnAddReunion = view.findViewById(R.id.add_reunion);
        spinnerRoom = view.findViewById(R.id.spinner_room);
        spinnerDuration = view.findViewById(R.id.spinner_time_duration);
        mAddParticipant = view.findViewById(R.id.tv_add_participant);
        mApiServiceInterface = Injection.getService();
        // Set Listener on  mAddParticipant
        mAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show DatePicker Dialog
                mShowAlertDialogAddParticipant = new ShowAlertDialogAddParticipant(mParticipantSelecteds
                        ,mListeParticipant);
                mShowAlertDialogAddParticipant.showAlertDialogAddParticipant(getActivity());
            }
        });

        //Set meeting duration and his endDate
        mDuration = mApiServiceInterface.getDuration();
        mSetDurationAndMeetingEndDate =new SetDurationAndMeetingEndDate(mApiServiceInterface
                ,getActivity(),spinnerDuration,mRooms,spinnerRoom,mStartDate
                ,mCompareStartDate,mCompareEndDate,mCompareDate
               ,mDuration,getContext());
        mSetDurationAndMeetingEndDate.spinnerDuration();
       // call creating a new meeting method
        createNewMeeting();
        return view;
    }

    public void createNewMeeting() {
        mApiServiceInterface = Injection.getService();
        mRooms = mApiServiceInterface.getRoom();
        mMeetingLists = mApiServiceInterface.getMeeting();

        mBtnAddReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mRoomSelected = mSetDurationAndMeetingEndDate.getRoomChoice();
                mMeetingLists = mApiServiceInterface.getMeeting();
                boolean bSubject = mSubject.getEditText().getText().toString().trim().length() > 0;

                // Verifie that all fields of meeting information is filled
                if (mRoomSelected != null) {
                    if (mMeetingLists != null && mCompareStartDate != null && mCompareEndDate != null
                            && !mRoomSelected.equals(mRooms.get(0)) && bSubject && mParticipantSelecteds.size() != 0
                            && !mSetDurationAndMeetingEndDate.getDurationSelected()
                            .equals(mDuration.get(0))) {
                        Meeting meeting = new Meeting(mMeetingLists.size(), mCompareStartDate, mCompareEndDate
                                , mRoomSelected
                                , mSubject.getEditText().getText().toString()
                                , mParticipantSelecteds);
                        // Create a new Meeting
                        mApiServiceInterface.addMeeting(meeting);
                        // Call callback to close addMeeting fragment and open ListMeeting Fragment
                        mCallback.OnButtonAddReunionClick(view);
                        // Ask user to filles all informations of meeting to create a new meeting
                    } else Toast.makeText(getActivity(), "VEUILLER REMPLIR TOUS LES CHAMPS  ",
                            Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getActivity(), "VEUILLER REMPLIR la salle !!! ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Clic to fix meeting start date and time
        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // HIDE KEYBOARD WHEN SET DATE AND TIME TEXTVIEW IS CLICKED
                InputMethodManager inputManager = (InputMethodManager) view
                        .getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                IBinder binder = view.getWindowToken();
                inputManager.hideSoftInputFromWindow(binder,
                        InputMethodManager.HIDE_NOT_ALWAYS);
                //set meeting date and time
               mSetDurationAndMeetingEndDate.setDateAndHour(mStartDate);
                mCompareStartDate = mCompareDate;
                Log.e("TAG", "onClick: mCompare est " + mCompareDate + " " + mCompareStartDate);
            }
        });
    }

// Define a callback to close fragment and his activity parent
    public void createCallbackToParentActivity() {
        mCallback = (onButtonAddReunionListener) getActivity();
    }

    // interface to use callback
    public interface onButtonAddReunionListener {
        void OnButtonAddReunionClick(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Create a call back
        createCallbackToParentActivity();
    }
}
