package fr.sitadigi.mareu.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.sitadigi.mareu.R;
import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.events.DeleteMeetingEvent;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class ReunionRecyclerViewAdapter extends RecyclerView.Adapter<ReunionRecyclerViewAdapter.ViewHolder> {
    MeetingApiServiceInterface mApiServiceInterface;

    List<Meeting> mMeetings = new ArrayList<>();

    public ReunionRecyclerViewAdapter(List<Meeting> meetings) {
        this.mMeetings = meetings;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy HH:MM");
        //Get Meeting
        mMeetings = mApiServiceInterface.getMeeting();
        Meeting meeting = mMeetings.get(position);
        String allText = meeting.getSubject()+ " - " +"\n\r"+ dateFormat.format(meeting.getStartDate1().getTime())+"\n"
                + " - " + dateFormat.format(meeting.getEndDate1().getTime())+"\n\r" + " - "+ meeting.getRoom().getNameRoom();
        holder.mTextReunion.setText(allText);
        // afficher la liste des mail participants
        String lisParticipant = "";
        String lisParticipantGlobal = lisParticipant;
        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            lisParticipant = meeting.getParticipants().get(i).getAdresseMail();
            lisParticipantGlobal = lisParticipantGlobal + " , " + lisParticipant;
        }
        holder.mAllMail.setText(lisParticipantGlobal);
        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mApiServiceInterface.removeReunion(meeting);
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
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
        TextView mAllMail;
        ImageView mBtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextReunion = itemView.findViewById(R.id.all_text);
            mTime = itemView.findViewById(R.id.meetting_endDateLayout);
            mDate = itemView.findViewById(R.id.meetting_startDateLayout);
            mAllMail = itemView.findViewById(R.id.meetting_all_email_item);
            mBtnDelete = itemView.findViewById(R.id.delete_button);


        }
    }


}
