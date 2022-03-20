package fr.sitadigi.mareu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
//import android.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
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

//import android.app.Fragment;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;

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
    final String SELECT_DURATION = "Select Duration";
    final String SELECT_ROOM = "Select Room";
    public onButtonAddReunionListener mCallback;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    String lisParticipant = "";
    String lisParticipantGlobal = lisParticipant;
    MaterialTextView mStartDate;
    MaterialTextView mAddParticipant;
    TextInputLayout mSubject;
    MaterialTextView mListeParticipant;
    Button mBtnAddReunion;
    List<Participant> mParticipants;
    MeetingApiServiceInterface mApiServiceInterface;
    Spinner spinnerRoom,  spinnerDuration;
    List<Room> mRooms;
    String mDurationSelected;
    boolean isSelectDuration;
    String nameParticipant = "";
    String nameParticipantGlobal = nameParticipant;
    String participant1 = "";
    String participantGlobal1 = "";
    private List<Meeting> mMeetingLists;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar mCompareDate = new GregorianCalendar();
    private Calendar mCompareStartDate = new GregorianCalendar();
    private Calendar mCompareEndDate = new GregorianCalendar();
    private List<String> mDuration;
    private Room mRoomSelected;
    private List<Participant> mParticipantSelecteds = new ArrayList<>();
    private String mTablet;
    private static final String TABLET = "TABLET";

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
        mListeParticipant = view.findViewById(R.id.list_participant);
        //mAddParticipant = view.findViewById(R.id.add_participant_text);
        mBtnAddReunion = view.findViewById(R.id.add_reunion);
        spinnerRoom = view.findViewById(R.id.spinner_room);
        spinnerDuration = view.findViewById(R.id.spinner_time_duration);
        mAddParticipant =view.findViewById(R.id.tv_add_participant);
        mApiServiceInterface = Injection.getService();

        mAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogAddParticipant(getActivity());
            }
        });


///SPINNER DURATION---------------------------
        mDuration = mApiServiceInterface.getDuration();
        if (!mDuration.get(0).equals(SELECT_DURATION)) {
            mApiServiceInterface.addInitialTextDuration();
        }
        ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(this.getActivity(),
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

        //SPINNER DURATION FIN -------------------------------------

     /*
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
                nameParticipant = spinnerParticipant.getSelectedItem().toString();
                //String nameParticipantGlobal ;
                nameParticipantGlobal = nameParticipantGlobal + " " + nameParticipant;
                mListeParticipant.setText(nameParticipantGlobal);

                // mParticipantSelecteds = new Participant(1,spinnerParticipant.getSelectedItem().toString(),"mail");
                Log.e("TAG", "onItemSelected: " + spinnerParticipant.getSelectedItem().toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing

            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapterParticipant = adapterView.getAdapter();
                Participant mParticipant = (Participant) adapterParticipant.getItem(position);

                mParticipantSelecteds.add(mParticipant);
                for (int i = 0; i < mParticipantSelecteds.size(); i++) {
                    participant1 = mParticipantSelecteds.get(i).getNameParticipant();
                    participantGlobal1 = participantGlobal1 + participant1;
                }
            }
        });*/

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
                mMeetingLists = mApiServiceInterface.getMeeting();
                boolean bSubject = mSubject.getEditText().getText().toString().trim().length() > 0;
               /* if ( mCompareStartDate == null ||mCompareEndDate == null) {
                    Toast.makeText(getActivity(),"Veillez remplir une date et heure de debut " ,
                            Toast.LENGTH_SHORT).show();
                }
                if ( mRoomSelected == null ) {
                    Toast.makeText(getActivity(),"Veillez selectionner une salle " ,
                            Toast.LENGTH_SHORT).show();
                }
                if ( mParticipantSelecteds == null ) {
                    Toast.makeText(getActivity(),"Veillez selectionner les participants " ,
                            Toast.LENGTH_SHORT).show();
                }
                if ( mDurationSelected.equals(mDuration.get(0)) ) {
                    Toast.makeText(getActivity(),"Veillez selectionner la durée  " ,
                            Toast.LENGTH_SHORT).show();
                }*/

                if (mRoomSelected != null) {
                    if (mMeetingLists != null && mCompareStartDate != null && mCompareEndDate != null
                            && !mRoomSelected.equals(mRooms.get(0)) && bSubject && mParticipantSelecteds.size() != 0
                            && !mDurationSelected.equals(mDuration.get(0))) {
                        Meeting meeting = new Meeting(mMeetingLists.size(), mCompareStartDate, mCompareEndDate
                                , mRoomSelected
                                , mSubject.getEditText().getText().toString()
                                , mParticipantSelecteds);
                        mApiServiceInterface.addMeeting(meeting);
                        //if ( mTablet != "TABLET") {
                        mCallback.OnButtonAddReunionClick(view);
                        //}
                    } else Toast.makeText(getActivity(), "VEUILLER REMPLIR TOUS LES CHAMPS  ",
                            Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getActivity(), "VEUILLER REMPLIR TOUS LES CHAMPS  ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Set StartDate
        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setDateAndHour(mStartDate);
                mCompareStartDate = mCompareDate;
                Log.e("TAG", "onClick: mCompare est " + mCompareDate + " " + mCompareStartDate);
            }
        });

    }

    public void setDateAndHour(MaterialTextView field) {
        Calendar date;
        //public void showDateTimePicker() {
        final Calendar currentDate = new GregorianCalendar();
        //date = Calendar.getInstance();
        date = new GregorianCalendar();
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
                        String time = hourOfDay + " h " + minute;
                        String date1 = dayOfMonth + " / " + month + " / " + year;
                        //date.add(Calendar.MONTH, 5 );
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:mm");
                        //String mDateDebut = date1 + " à " +time;
                        field.setText(dateFormat.format(date.getTime()));
                        mCompareDate.set(year, month, dayOfMonth, hourOfDay, minute);
                        //mTime.setText(time);
                        Log.e("TAG", "The choosen one mCompareDate " + mCompareDate.getTime());
                        boolean bStartDate = mStartDate.getText().toString().trim().length() > 0;

                        //boolean bEndDate = mEndDate.getEditText().getText().toString().trim().length()>0 ;
                        // boolean bStartDate = mStartDate.getEditText().getText().toString().trim().length()>0 ;
                        if (bStartDate && mDurationSelected != null) {
                            setEndDate();
                        }

                        availableRoom();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

    }

    public void availableRoom() {
        mApiServiceInterface = Injection.getService();
        List<Room>mRoomsGlobal= mApiServiceInterface.getRoom();
        if (!mRoomsGlobal.get(0).getNameRoom().equals(SELECT_ROOM) ) {
            mApiServiceInterface.addInitialTextRoom();
        }

        boolean bStartDate = mStartDate.getText().toString().trim().length() > 0;
        if (mCompareStartDate != null && mCompareEndDate != null
                && bStartDate && isSelectDuration) {
            mRooms = mApiServiceInterface.getAvailableRoom(mCompareStartDate, mCompareEndDate);

        } else mRooms = new ArrayList<>();
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
            }

            private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapter = adapterView.getAdapter();
                mRoomSelected = (Room) adapter.getItem(position);
            }
        });

    }

    public void createCallbackToParentActivity() {
        mCallback = (onButtonAddReunionListener) getActivity();
    }

    // DIALOGUE PARTICIPANT

    final boolean[] checkedInfos = new boolean[]{false, false, false, false};
    public  void showAlertDialogAddParticipant(final Activity activity)  {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mParticipants = mApiServiceInterface.getMailsParticipant();
        // Set Title.
        builder.setTitle("Select participant");

        // Add a list
        final String[] participant = {mParticipants.get(0).getNameParticipant(),
                mParticipants.get(1).getNameParticipant(), mParticipants.get(2).getNameParticipant(),
                mParticipants.get(3).getNameParticipant()};

         // Sheep


        builder.setMultiChoiceItems(participant, checkedInfos, new DialogInterface.OnMultiChoiceClickListener()  {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedInfos[which] = isChecked;
                for (int i = 0; i < participant.length; i++) {
                    if (checkedInfos[i] && !mParticipantSelecteds.contains(mParticipants.get(i))) {
                        mParticipantSelecteds.add(mParticipants.get(i));
                    } else if(!checkedInfos[i] && mParticipantSelecteds.contains(mParticipants.get(i))) {
                    mParticipantSelecteds.remove(mParticipants.get(i));}

                }
            }
        });

        //
        builder.setCancelable(true);
      //  builder.setIcon(R.drawable.ic_baseline_delete_24);

        // Create "Yes" button with OnClickListener.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Close Dialog
                dialog.dismiss();

                String s= null;
                for(int i=0; i< participant.length;i++)  {
                    if(checkedInfos[i]) {
                        if(s == null)  {
                            s = participant[i];

                           // mParticipantSelecteds.add(mParticipants.get(i));
                        } else {
                            s+= ", " + participant[i];
                        }
                    }
                }
                s = s == null? "":s;

                // Do something, for example: Call a method of Activity...

                mListeParticipant.setText(s);
            }
        });

        // Create "Cancel" button with OnClickListener.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(activity,"You choose Cancel button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }




    //FIN DIALOGUE PARTICIPANT

    public void setEndDate() {
        // mCompareEndDate = new GregorianCalendar();
        if (!mDurationSelected.equals(mDuration.get(0))) {
            mCompareEndDate.setTime(mCompareStartDate.getTime());
        }
        if (mDurationSelected.equals(mDuration.get(1))) {
            //mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(2))) {
            //  mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
        } else if (mDurationSelected.equals(mDuration.get(3))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(4))) {
            //  mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(5))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 1);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(6))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
        } else if (mDurationSelected.equals(mDuration.get(7))) {
            // mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(8))) {
            //   mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(9))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 2);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(10))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
        } else if (mDurationSelected.equals(mDuration.get(11))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 15);
        } else if (mDurationSelected.equals(mDuration.get(12))) {
            //     mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 30);
        } else if (mDurationSelected.equals(mDuration.get(13))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 3);
            mCompareEndDate.add(Calendar.MINUTE, 45);
        } else if (mDurationSelected.equals(mDuration.get(14))) {
            //    mCompareEndDate = mCompareStartDate;
            mCompareEndDate.add(Calendar.HOUR, 4);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface onButtonAddReunionListener {
        void OnButtonAddReunionClick(View view);
    }
}
