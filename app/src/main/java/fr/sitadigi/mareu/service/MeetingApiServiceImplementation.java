package fr.sitadigi.mareu.service;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;

public class MeetingApiServiceImplementation implements MeetingApiServiceInterface {

    public List<Meeting> mMeetings = MeetingGenerator.generatorReunion();
    List<Participant> mailsParticipants = MeetingGenerator.generatorMailsParticipants();
    List<Room> mRooms = MeetingGenerator.generatorRoom();

    //Method for Meeting
    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void removeMeeting(Meeting meeting) {
        mMeetings.remove(meeting);

    }

    //Method for Participants
    @Override
    public List<Participant> getMailsParticipant() {
        return mailsParticipants;
    }

    @Override
    public void addMailParticipant(Participant participant) {
        mailsParticipants.add(participant);
    }

    @Override
    public void removeMailParticipant(Participant participant) {
        mailsParticipants.remove(participant);
    }

    // Method for Room
    @Override
    public List<Room> getRoom() {
        return mRooms;
    }

    @Override
    public void addRoom(int id) {
        mRooms.add(mRooms.get(id));
    }

    @Override
    public void removeRoom(int id) {
        mRooms.remove(mRooms.get(id));

    }

    // Filter method
    @Override
    public List<Meeting> filterByRoom(Room room) {
        List<Meeting> filterRoomList = new ArrayList<>();
        for (int i = 0; i < mMeetings.size(); i++) {
            if (mMeetings.get(i).getRoom() == room) {
                filterRoomList.add(mMeetings.get(i));
            }
        }
        return filterRoomList;
    }

    @Override
    public List<Meeting> filterByStartDay(Date startedDate) {
        List<Meeting> filterByStartDayList = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startedDate);
        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(mMeetings.get(i).getStartDate());
            boolean sameDate = (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) &&
                    (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
            if (sameDate) {
                filterByStartDayList.add(mMeetings.get(i));
            }
        }
        return filterByStartDayList;
    }

    @Override
    public List<Meeting> resetMeetingList() {
        return mMeetings;
    }

    @Override
    public List<Room> getAvailableRoom(Meeting meeting) {
        List<Room> avalaibleRoom = getRoom();
        Date startDate = meeting.getStartDate();
        Date endDate = meeting.getEndDate();
        for (int i = 0; i < mMeetings.size(); i++) {
            if ((startDate.equals(mMeetings.get(i).getStartDate()) || startDate.after(mMeetings.get(i).getStartDate()))
                    && (startDate.equals(mMeetings.get(i).getEndDate())) || (startDate.before(mMeetings.get(i).getEndDate()))) {
                avalaibleRoom.remove(mMeetings.get(i).getRoom());
            }
            else if ((endDate.equals(mMeetings.get(i).getStartDate()) || startDate.after(mMeetings.get(i).getStartDate()))
                    && (endDate.equals(mMeetings.get(i).getEndDate())) || (startDate.before(mMeetings.get(i).getEndDate()))) {
                avalaibleRoom.remove(mMeetings.get(i).getRoom());
            }


        }
        return avalaibleRoom;
    }
    @Override
    public Calendar setCalendar(int Year,int Month,int Day,int Hour,int Minute){
        Calendar cal =  Calendar.getInstance();
        cal.set(Year,Month,Day,Hour,Minute);
        return cal;

    }
}
