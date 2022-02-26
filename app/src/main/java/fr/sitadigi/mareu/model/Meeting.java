package fr.sitadigi.mareu.model;


import android.text.format.Time;

import java.util.Date;
import java.util.List;

public class Meeting {
    int id;
    Date startDate;
    Date endDate;
    Room room;
    String subject;
    List<Participant> participants;

    public Meeting(int id, Date startDate, Date endDate, Room room, String subject,
                   List<Participant> participants) {

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.subject = subject;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
