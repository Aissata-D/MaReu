package fr.sitadigi.mareu.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMeetingFragment extends Fragment {
    final String POSITION = "POSITION";
    private int mPosition = 1;
    private  String mConfig;
    public final String TABLET= "TABLET";
    public final String PHONE= "PHONE";
    public final String CONFIG = "CONFIG";
    MeetingApiServiceInterface mApiServiceInterface;
    private List<Meeting> mMeetingLists;

    TextView mSubject;
    TextView mRoom;
    TextView mStartDate;
    TextView mEndDate;
    TextView mParticipant;
    ImageView mBtnBack;
    private AddMeetingFragment mAddMeetingFragment;
    private  DetailMeetingFragment mDetailMeetingFragment;


    public DetailMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailMeetingFragment newInstance() {
        DetailMeetingFragment fragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_meeting, container, false);
        // Get position of meeting
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION);
            mConfig= getArguments().getString(CONFIG);
        }
        mApiServiceInterface = Injection.getService();
        mMeetingLists = mApiServiceInterface.getMeeting();
        mSubject = view.findViewById(R.id.sujet_detail);
        mRoom = view.findViewById(R.id.salle_detail);
        mStartDate = view.findViewById(R.id.heure_debut_detail);
        mEndDate = view.findViewById(R.id.heure_fin_detail);
        mParticipant = view.findViewById(R.id.liste_participant_details);
        mBtnBack = view.findViewById(R.id.details_button_back);
        // setTex view
        setTextViews();
        // Inflate the layout for this fragment
        return view;
    }

    public  void setTextViews(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm");
        String subject = "Sujet de la réunion : " + mMeetingLists.get(mPosition).getSubject();
        mSubject.setText(subject);
        String room = "Salle de la réunion : " + mMeetingLists.get(mPosition).getRoom().getNameRoom();
        mRoom.setText(room);

        String startDate = "Heure du debut de la réunion : \n"
                + dateFormat.format(mMeetingLists.get(mPosition).getStartDate1().getTime());
        mStartDate.setText(startDate);
        String endDate = "Heure de fin de la réunion : \n"
                + dateFormat.format(mMeetingLists.get(mPosition).getEndDate1().getTime());
        mEndDate.setText(endDate);
        List<Participant> participants = mMeetingLists.get(mPosition).getParticipants();
        String nameParticipant = "";
        String nameParticipantGlobal = "Noms des participants : ";
        for (Participant p : participants) {
            nameParticipant = p.getNameParticipant();
            // nameParticipantGlobal = nameParticipant;
            nameParticipantGlobal = nameParticipantGlobal + "\n" + nameParticipant;

        }
        mParticipant.setText(nameParticipantGlobal);
        //Clic button to close detailFragment
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConfig==TABLET){
                    Log.e("TAG", "onClick: BTNbackpress TABLET");
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment fragment= getActivity().getSupportFragmentManager()
                            .findFragmentById(R.id.framLayout_add_or_detail);
                    if(fragment instanceof AddMeetingFragment) {
                        mAddMeetingFragment = (AddMeetingFragment) fragment;
                    }else {
                        mDetailMeetingFragment = (DetailMeetingFragment)fragment;
                    }
                    if (mAddMeetingFragment == null ) {

                        mAddMeetingFragment = new AddMeetingFragment();
                        transaction.replace(R.id.framLayout_add_or_detail, mAddMeetingFragment); //give your fragment container id in first parameter
                        transaction.commit();
                    }

                }else if(mConfig==PHONE){
                    Log.e("TAG", "onClick: BTNbackpress PHONE");
                    getActivity().onBackPressed();
                }
            }
        });
    }
}