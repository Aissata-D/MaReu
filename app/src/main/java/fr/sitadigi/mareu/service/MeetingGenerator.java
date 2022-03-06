package fr.sitadigi.mareu.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
            new Participant(1,"Aissata","aissata@yahoo.fr"),
            new Participant(2,"Alice","alice@yahoo.fr"),
            new Participant(3,"Jean","jean@yahoo.fr"),
            new Participant(4,"Marie","marie@yahoo.fr")


    );
    //static  int year;
    //static int annee = year-1900;
   //static Date dateDebut = new Date((2022-1900),01,28,14,45);
   //static Date dateFin = new Date((2022-1900),01,28,15,10);

    static Calendar dateDebut1 = Calendar.getInstance();
    static Calendar dateFin1 = Calendar.getInstance();
    static Calendar dateDebut2 = Calendar.getInstance();
    static Calendar dateFin2 = Calendar.getInstance();
    static Calendar dateDebut3 = Calendar.getInstance();
    static Calendar dateFin3 = Calendar.getInstance();
    static String sujet= "Sujet resto";
    //Set date




    public static List<Meeting> sMeetings =  Arrays.asList(

           // new Meeting(1,dateDebut,dateFin, sRooms.get(0), sujet,MAILS_PARTICIPANTS),
            new Meeting(1,new GregorianCalendar(2022,1,28,15,10)
                    ,new GregorianCalendar(2022,1,28,15,45),sRooms.get(0),"Sujet 1",MAILS_PARTICIPANTS),
            new Meeting(2,new GregorianCalendar(2022,2,28,16,25)
                    ,new GregorianCalendar(2022,2,28,18,50),sRooms.get(1),"Sujet 2",MAILS_PARTICIPANTS),
            new Meeting(3,new GregorianCalendar(2022,3,28,17,20)
                    ,new GregorianCalendar(2022,3,28,18,5),sRooms.get(2),"Sujet 3",MAILS_PARTICIPANTS)


    );
    public static List<String> Duration= Arrays.asList(
            "30 min","1h00","1h15","1h30","1h45","2h00","2h15","2h30"
            ,"2h45","3h00","3h15","3h30","3h45","4h00"

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
    public static List<String> generatoDuration(){
        return new ArrayList<>(Duration);
    }
}
//public  static Participant mail1 = new Participant("diassana@yahoo") ;
// public  Meeting reunion1 = new Meeting("paris","sujet-de coonvert",MAILS_PARTICIPANTS);