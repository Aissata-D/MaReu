package fr.sitadigi.mareu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Room;

public class MeetingApiServiceImplementation implements MeetingApiServiceInterface {

    public List<Meeting> mMeetings = MeetingGenerator.generatorReunion();
    List<Participant> mailsParticipants = MeetingGenerator.generatorMailsParticipants();
    List<Room> mRooms = MeetingGenerator.generatorRoom();
    List<String> mDurations = MeetingGenerator.generatoDuration();
    final String SELECT_DURATION = "Select Duration";
    final String SELECT_ROOM = "Select Room";
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
    public List<Participant> getMeetingParticipant() {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy");

        List<Meeting> filterByStartDayList = new ArrayList<>();
        Calendar cal1 = new GregorianCalendar();
        //dateFormat.format(meeting.getEndDate1().getTime())
        cal1.setTime(startedDate.getTime());
        for (Meeting meeting : mMeetings) {
            Calendar cal2 = new GregorianCalendar();
            cal2.setTime(meeting.getStartDate1().getTime());

            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) {
                filterByStartDayList.add(meeting);

            }
        }
        return filterByStartDayList;
    }

  /*  @Override
    public List<Meeting> resetMeetingList() {
        return mMeetings;
    }*/

    @Override
    public List<String> getDuration() {
        return mDurations;
    }

    @Override
    public void addInitialTextDuration() {
        mDurations.add(0, SELECT_DURATION);


    }

    @Override
    public void addInitialTextRoom() {
        Room roomInitialText = new Room(0,SELECT_ROOM);
        if (!mRooms.contains(roomInitialText)){
        mRooms.add(0, roomInitialText);}

    }

    @Override
    public List<Room> getAvailableRoom(Calendar startDate, Calendar endDate) {
        List<Room> availableRooms = new ArrayList<>();
        List<Room> notAvailableRooms = new ArrayList<>();
        if (startDate == null || endDate == null) {
            return availableRooms;
        }
        for (Meeting meeting : mMeetings) {

            if ((startDate.equals(meeting.getStartDate1()) || startDate.after(meeting.getStartDate1()))
                    && ((startDate.equals(meeting.getEndDate1())) || (startDate.before(meeting.getEndDate1())))) {
                notAvailableRooms.add(meeting.getRoom());
            } else if ((endDate.equals(meeting.getStartDate1()) || endDate.after(meeting.getStartDate1()))
                    && ((endDate.equals(meeting.getEndDate1())) || (endDate.before(meeting.getEndDate1())))) {
                notAvailableRooms.add(meeting.getRoom());
            } else if (startDate.before(meeting.getStartDate1()) && endDate.after(meeting.getEndDate1())) {
                notAvailableRooms.add(meeting.getRoom());
            }
        }
        for (Room room : mRooms) {
            if (!notAvailableRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /*@Override
    public Calendar setCalendar(int Year, int Month, int Day, int Hour, int Minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Year, Month, Day, Hour, Minute);
        return cal;

    }*/
}
