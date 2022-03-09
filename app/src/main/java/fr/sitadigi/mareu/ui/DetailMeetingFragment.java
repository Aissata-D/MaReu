package fr.sitadigi.mareu.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

//import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailMeetingFragment extends Fragment {
    final String POSITION = "POSITION";
    private int mPosition;
    MeetingApiServiceInterface mApiServiceInterface;
    private List<Meeting> mMeetingLists;

    TextView mSubject;
    TextView mRoom;
    TextView mStartDate;
    TextView mEndDate;
    TextView mParticipant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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
        }
        mApiServiceInterface = Injection.getService();
        mMeetingLists = mApiServiceInterface.getMeeting();
        mSubject = view.findViewById(R.id.sujet_detail);
        mRoom = view.findViewById(R.id.salle_detail);
        mStartDate = view.findViewById(R.id.heure_debut_detail);
        mEndDate = view.findViewById(R.id.heure_fin_detail);
        mParticipant = view.findViewById(R.id.liste_participant_details);
        // setTex view
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
        String nameParticipantGlobal = "Noms des participants : \n";
        for (Participant p : participants) {
            nameParticipant = p.getNameParticipant();
            // nameParticipantGlobal = nameParticipant;
            nameParticipantGlobal = nameParticipantGlobal + "\n" + nameParticipant;

        }
        mParticipant.setText(nameParticipantGlobal);
        // Inflate the layout for this fragment
        return view;
    }
}