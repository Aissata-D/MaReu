package fr.sitadigi.mareu.model;


import android.text.format.Time;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Meeting {
    int id;
    Date startDate;
    Date endDate;
    Calendar startDate1 =Calendar.getInstance();
    Calendar endDate1=Calendar.getInstance();
    Room room;
    String subject;
    List<Participant> participants;

   /* public Meeting(int id, Date startDate, Date endDate, Room room, String subject,
                   List<Participant> participants) {

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.subject = subject;
        this.participants = participants;
    }*/

    public Meeting(int id,
                   Calendar startDate1, Calendar endDate1, Room room, String subject,
                   List<Participant> participants1) {
        this.id = id;
        this.startDate1 = startDate1;
        this.endDate1 = endDate1;
        this.room = room;
        this.subject = subject;
        this.participants = participants1;

    }
    /////////////////GETTER AND SETTER CALENDAR-----------------------------------------------

    public Calendar getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(Calendar startDate1) {
        this.startDate1 = startDate1;
    }

    public Calendar getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(Calendar endDate1) {
        this.endDate1 = endDate1;
    }


    /////////////////GETTER AND SETTER CALENDAR-FIN----------------------------------------------





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
/*
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
*/
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
