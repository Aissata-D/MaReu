package fr.sitadigi.mareu.service;

import java.util.Calendar;
import java.util.List;

import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Room;

public interface MeetingApiServiceInterface {


    List<Meeting> getMeeting();

    List<Participant> getMailsParticipant();

    List<Room> getRoom();

    void addMeeting(Meeting meeting);

    void removeMeeting(Meeting meeting);

    void addMailParticipant(Participant participant);

    void removeMailParticipant(Participant participant);

    void addRoom(Room room);

    void removeRoom(Room room);

    List<Meeting> filterByRoom(Room room);

    List<Meeting> filterByStartDay(Calendar startedDate);

   // List<Meeting> resetMeetingList();

    List<String> getDuration();

    void addInitialTextDuration();
    void addInitialTextRoom();


    List<Room> getAvailableRoom(Calendar startDate, Calendar endDate);

  /*  public Calendar setCalendar(int Year, int Month, int Day, int Hour, int Minute);*/
}
