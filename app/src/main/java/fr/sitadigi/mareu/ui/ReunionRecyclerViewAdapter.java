package fr.sitadigi.mareu.ui;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.events.DeleteMeetingEvent;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;

public class ReunionRecyclerViewAdapter extends RecyclerView.Adapter<ReunionRecyclerViewAdapter.ViewHolder> {
    MeetingApiServiceInterface mApiServiceInterface;

    List<Meeting> mMeetings = new ArrayList<>();
    private int mPosition;
    FragmentActivity mFragmentActivity;
    final String POSITION = "POSITION";

    public ReunionRecyclerViewAdapter(FragmentActivity fragmentActivity, List<Meeting> meetings) {
        this.mMeetings = meetings;
        this.mFragmentActivity = fragmentActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_mail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mApiServiceInterface = Injection.getService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        //Get Meeting
        //  mMeetings = mApiServiceInterface.getMeeting();
        //+ " - " + dateFormat.format(meeting.getStartDate1().getTime())

        Meeting meeting = mMeetings.get(position);
        int hour = meeting.getStartDate1().get(Calendar.HOUR_OF_DAY);
        int minute = meeting.getStartDate1().get(Calendar.MINUTE);

        String allText = meeting.getSubject()
                + " - " + hour + "h" + minute + " - " + meeting.getRoom().getNameRoom();
        holder.mTextReunion.setText(allText);
        // afficher la liste des mail participants
        String lisParticipant = "";
        String lisParticipantGlobal = lisParticipant;
        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            lisParticipant = meeting.getParticipants().get(i).getAdresseMail();
            if (i == 0) {
                lisParticipantGlobal = lisParticipant;
            } else lisParticipantGlobal = lisParticipantGlobal + " , " + lisParticipant;

        }
        holder.mAllMail.setText(lisParticipantGlobal);
        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mApiServiceInterface.removeReunion(meeting);
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getAdapterPosition();
                //FAIRE UN CALLBACK ?????
                DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
                // Put Meeting position in a detailMeetingFragment
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, mPosition);
                detailMeetingFragment.setArguments(bundle);
                // Start detailMeetingFragment
                FragmentManager fragmentManager = mFragmentActivity.getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framLayout_list_mail, detailMeetingFragment); //give your fragment container id in first parameter
                transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {

        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mDate;
        TextView mTime;
        TextView mTextReunion;
        MaterialTextView mAllMail;
        ImageView mBtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextReunion = itemView.findViewById(R.id.all_text);
            mTime = itemView.findViewById(R.id.spinner_time_duration);
            mDate = itemView.findViewById(R.id.meetting_startDateLayout);
            mAllMail = itemView.findViewById(R.id.meetting_all_email_item);
            mBtnDelete = itemView.findViewById(R.id.delete_button);


        }
    }


}
