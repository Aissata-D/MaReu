package fr.sitadigi.mareu.ui;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.events.DeleteReunionEvent;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.service.ReunionApiServiceInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMailFragment extends Fragment {
    RecyclerView mRecyclerView ;

    ReunionApiServiceInterface mApiServiceInterface ;

    public static List<Participant> mMailLists;

    public static List<Meeting> sMMeetingLists;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListMailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMailFragment newInstance(String param1, String param2) {
        ListMailFragment fragment = new ListMailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view =inflater.inflate(R.layout.fragment_list_mail, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        // Inflate the layout for this fragment
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initRecyclerView();
        return view;
    }

    public void initRecyclerView(){
        mApiServiceInterface = Injection.getService();
        sMMeetingLists = mApiServiceInterface.getReunion();
        mMailLists = mApiServiceInterface.getMailsParticipant();
        ReunionRecyclerViewAdapter reunionRecyclerViewAdapter =
                new ReunionRecyclerViewAdapter(sMMeetingLists);
        mRecyclerView.setAdapter(reunionRecyclerViewAdapter);

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
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiServiceInterface.removeReunion(event.mMeeting);
        initRecyclerView();
    }

   /* @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    */


}