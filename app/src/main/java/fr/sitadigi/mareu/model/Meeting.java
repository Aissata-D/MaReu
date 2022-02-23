package fr.sitadigi.mareu.model;


import android.text.format.Time;

import java.util.Date;
import java.util.List;

public class Meeting {
    int Id;
    Date startDate;
    Date endDate;
   Room room;
    String subject;
   List<Participant> participants;

    private String mLieu;
    private String mSujet;
    private Date mDate ;
    Time t = new Time();
    private String mTime;
    private List<Participant> mParticipants;

    public Meeting(String lieu, String sujet, Date date, String time, List<Participant> participants) {
        mLieu = lieu;
        mSujet = sujet;
        mDate = date;
        mTime = time;
        mParticipants = participants;
    }
    public Meeting(String lieu, String sujet, List<Participant> participants) {
        mLieu = lieu;
        mSujet = sujet;
        mParticipants = participants;
         t = new Time();
        this.mDate = new Date();
    }

    public Meeting(String lieu, String sujet, String time, List<Participant> participants) {
        mLieu = lieu;
        mSujet = sujet;
        mTime = time;
        mParticipants = participants;
    }


    public Time getT() {
        return t;
    }

    public void setT(Time t) {
        this.t = t;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getLieu() {
        return mLieu;
    }

    public void setLieu(String lieu) {
        mLieu = lieu;
    }

    public String getSujet() {
        return mSujet;
    }

    public void setSujet(String sujet) {
        mSujet = sujet;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public List<Participant> getParticipants() {
        return mParticipants;
    }

    public void setParticipants(List<Participant> participants) {
        mParticipants = participants;
    }
}
