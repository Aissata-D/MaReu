package fr.sitadigi.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;
import fr.sitadigi.mareu.model.Room;

public  class MeetingGenerator {
    //Declaration de la liste des réunion et de la liste des mail des participants
    public static List<Room> sRooms = Arrays.asList(
            new Room(1,"Salle 1"),
            new Room(2,"Salle 2"),
            new Room(3,"Salle 3"),
            new Room(4,"Salle 4"),
            new Room(5,"Salle 5"),
            new Room(6,"Salle 6"),
            new Room(7,"Salle 7"),
            new Room(8,"Salle 8"),
            new Room(9,"Salle 9"),
            new Room(10,"Salle 10")
            );


    public static List<Participant> MAILS_PARTICIPANTS = Arrays.asList(
            new Participant(1,"Aissata","souare@yahoo")
    );
    static Date dateDebut = new Date();
    static Date dateFin = new Date();
    static String sujet;

    public static List<Meeting> sMeetings =  Arrays.asList(

            new Meeting(1,dateDebut,dateFin, sRooms.get(0), sujet,MAILS_PARTICIPANTS)
    );

// Methodes retournant les listes
    public static List<Meeting> generatorReunion(){
       return new ArrayList<>(sMeetings);
   }

    public static List<Room> generatorRoom(){
        return new ArrayList<>(sRooms);
    }
    public static List<Participant> generatorMailsParticipants(){
        return new ArrayList<>(MAILS_PARTICIPANTS);
    }
}
//public  static Participant mail1 = new Participant("diassana@yahoo") ;
// public  Meeting reunion1 = new Meeting("paris","sujet-de coonvert",MAILS_PARTICIPANTS);