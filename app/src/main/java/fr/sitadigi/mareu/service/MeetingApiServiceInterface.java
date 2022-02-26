package fr.sitadigi.mareu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;

public interface MeetingApiServiceInterface {


    List<Meeting> getMeeting();
    List<Participant> getMailsParticipant();
    List<Room> getRoom();
    void addMeeting(Meeting meeting);
    void removeMeeting(Meeting meeting);
    void addMailParticipant(Participant participant);
    void removeMailParticipant(Participant participant);
    void addRoom(int id);
    void removeRoom(int id);
    List<Meeting> filterByRoom(Room room);
    List<Meeting> filterByStartDay(Date startedDate);
    List<Meeting> resetMeetingList();
    List<Room> getAvailableRoom(Meeting meeting);
    public Calendar setCalendar(int Year,int Month,int Day,int Hour,int Minute);
}
