package fr.sitadigi.mareu.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Room;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;
import fr.sitadigi.mareu.service.MeetingGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingApiServiceUnitfaceTest {
    MeetingApiServiceInterface service;
    List<Meeting> mMeetings;

    @Before
    public void setUp() throws Exception {
        service = Injection.getNewInstanceApiService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMeeting() {
        mMeetings = MeetingGenerator.sMeetings;
        assertEquals(mMeetings, service.getMeeting());

    }

    @Test
    public void getMailsParticipant() {
        List<Participant> participants = MeetingGenerator.MAILS_PARTICIPANTS;
        assertEquals(participants, service.getMailsParticipant());
    }

    @Test
    public void getRoom() {
        List<Room> rooms = MeetingGenerator.sRooms;
        assertEquals(rooms, service.getRoom());
    }

    @Test
    public void addMeeting() {
        Calendar dateDebut1 = Calendar.getInstance();
        Calendar dateFin1 = Calendar.getInstance();
        dateDebut1.set(2023, 01, 28, 15, 10);
        dateFin1.set(2023, 01, 28, 15, 45);
        Room room1 = new Room(1, "Salle test");
        List<Participant> participant = service.getMailsParticipant();
        Meeting meeting = new Meeting(1, dateDebut1, dateFin1, room1, "Sujet 1"
                , participant);
        service.addMeeting(meeting);
        assertTrue(service.getMeeting().contains(meeting));
    }

    @Test
    public void removeMeeting() {

        Meeting meeting1 = service.getMeeting().get(0);
        service.removeMeeting(meeting1);
        assertFalse(service.getMeeting().contains(meeting1));
    }

    @Test
    public void addMailParticipant() {

        Participant participant1 = new Participant(1, "Jeanne", "jeanne@gmail.com");

        service.addMailParticipant(participant1);
        assertTrue(service.getMailsParticipant().contains(participant1));
    }

    @Test
    public void removeMailParticipant() {
        Participant participant = service.getMailsParticipant().get(0);
        service.removeMailParticipant(participant);
        assertFalse(service.getMeeting().contains(participant));
    }

    @Test
    public void addRoom() {
        Room room = new Room(1, "Salle test");
        service.addRoom(room);
        assertTrue(service.getRoom().contains(room));

    }

    @Test
    public void removeRoom() {
        Room room = service.getRoom().get(0);
        service.removeRoom(room);
        assertFalse(service.getRoom().contains(room));
    }

    @Test
    public void filterByRoom() {
        Room room = service.getRoom().get(0);
        List<Meeting> meetings = service.filterByRoom(room);
        assertEquals(room, meetings.get(0).getRoom());
    }

    @Test
    public void filterByStartDay() {
        Calendar dateDebut1 = new GregorianCalendar(2022, 1, 28);


        // List<Meeting> meetings1= service.getMeeting();
        List<Meeting> meetings = service.filterByStartDay(dateDebut1);
        assertEquals(1, meetings.size());
        assertTrue(meetings.contains(service.getMeeting().get(0)));
        assertFalse(meetings.contains(service.getMeeting().get(1)));
        assertFalse(meetings.contains(service.getMeeting().get(2)));
        assertEquals(meetings.get(0).getStartDate1().get(Calendar.DAY_OF_YEAR),
                service.getMeeting().get(0).getStartDate1().get(Calendar.DAY_OF_YEAR));
        assertEquals(meetings.get(0).getStartDate1().get(Calendar.YEAR),
                service.getMeeting().get(0).getStartDate1().get(Calendar.YEAR));

    }

    @Test
    public void getAvailableRoom() {
        Calendar dateDebut1 = new GregorianCalendar(2022, 1, 28, 15, 10);
        Calendar dateFin1 = new GregorianCalendar(2022, 1, 28, 15, 45);
        // Calendar dateFin1 = Calendar.getInstance();
        //dateDebut1.set();
        //dateFin1.set(2022,1,28,15,45);
        // creer les meeting à tester ici
        List<Room> avalaibleRoom = service.getAvailableRoom(dateDebut1, dateFin1);// Date of meeting get(0)
        assertFalse(avalaibleRoom.contains(service.getMeeting().get(0).getRoom()));
        assertTrue(avalaibleRoom.contains(service.getMeeting().get(1).getRoom()));

        List<Room> avalaibleRoom1 = service.getAvailableRoom(service.getMeeting().get(1).getStartDate1()
                , service.getMeeting().get(1).getEndDate1()); //Date of meeting get(1)
        assertTrue(avalaibleRoom1.contains(service.getMeeting().get(0).getRoom()));
        assertFalse(avalaibleRoom1.contains(service.getMeeting().get(1).getRoom()));

        assertTrue(avalaibleRoom1.contains(service.getMeeting().get(2).getRoom()));


    }

    @Test
    public void resetMeetingList() {
    }


    @Test
    public void setCalendar() {
    }
}