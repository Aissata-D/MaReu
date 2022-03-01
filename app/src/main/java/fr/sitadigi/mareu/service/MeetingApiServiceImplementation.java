package fr.sitadigi.mareu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;

public class MeetingApiServiceImplementation implements MeetingApiServiceInterface {

    public List<Meeting> mMeetings = MeetingGenerator.generatorReunion();
    List<Participant> mailsParticipants = MeetingGenerator.generatorMailsParticipants();
    List<Room> mRooms = MeetingGenerator.generatorRoom();
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

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
    public void addRoom(Room room) {
        mRooms.add(room);
    }

    @Override
    public void removeRoom(Room room) {
        mRooms.remove(room);

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
    public List<Meeting> filterByStartDay(Calendar startedDate) {
        List<Meeting> filterByStartDayList = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startedDate.getTime());
        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(mMeetings.get(i).getStartDate1().getTime());
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
    public List<Room> getAvailableRoom(Calendar startDate,Calendar endDate) {
        List<Room> avalaibleRoom = getRoom();
       // Date startDate = meeting.getStartDate();
        //Date endDate = meeting.getEndDate();

        for (int i = 0; i < mMeetings.size(); i++) {
            if (startDate!=null && endDate!=null &&mMeetings.get(i).getStartDate1()!=null
                    && mMeetings.get(i).getEndDate1()!=null) {
                if ((startDate.equals(mMeetings.get(i).getStartDate1()) || startDate.after(mMeetings.get(i).getStartDate1()))
                        && ((startDate.equals(mMeetings.get(i).getEndDate1())) || (startDate.before(mMeetings.get(i).getEndDate1())))) {
                    avalaibleRoom.remove(mMeetings.get(i).getRoom());
                } else if ((endDate.equals(mMeetings.get(i).getStartDate1()) || startDate.after(mMeetings.get(i).getStartDate1()))
                        && ((endDate.equals(mMeetings.get(i).getEndDate1())) || (startDate.before(mMeetings.get(i).getEndDate1())))) {
                    avalaibleRoom.remove(mMeetings.get(i).getRoom());
                }
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
