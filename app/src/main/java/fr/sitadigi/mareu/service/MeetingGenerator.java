package fr.sitadigi.mareu.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
            new Participant(1,"Aissata","aissata@yahoo.fr"),
            new Participant(2,"Alice","alice@yahoo.fr"),
            new Participant(3,"Jean","jean@yahoo.fr"),
            new Participant(4,"Marie","marie@yahoo.fr")


    );
    //static  int year;
    //static int annee = year-1900;
   static Date dateDebut = new Date((2022-1900),01,28,14,45);
   static Date dateFin = new Date((2022-1900),01,28,15,10);

    static Calendar dateDebut1 = Calendar.getInstance();
    static Calendar dateFin1 = Calendar.getInstance();
    static Calendar dateDebut2 = Calendar.getInstance();
    static Calendar dateFin2 = Calendar.getInstance();
    static Calendar dateDebut3 = Calendar.getInstance();
    static Calendar dateFin3 = Calendar.getInstance();
    static String sujet= "Sujet resto";
    //Set date
    static String  dt ;
    static String  dt1 ;
    SimpleDateFormat sdf ;
    Calendar c;
    Calendar c1;
    void setDate (){
       dateDebut1.set(2022,01,28,15,10);
       dateFin1.set(2022,01,28,15,45);
        dateDebut2.set(2022,02,1,15,10);
        dateFin2.set(2022,02,1,15,45);
        dateDebut3.set(2022,03,2,15,10);
        dateFin3.set(2022,03,2,15,45);
        /*
        dt = "2022-02-26";  // Start date
        dt1 = "2022-02-26";  // Start date
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();
        c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(dt1));
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c1.add(Calendar.DATE, 1);
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        dt1 = sdf.format(c1.getTime());  // dt is now the new date
        dateDebut=c.getTime();
        dateFin=c1.getTime();*/
    }

    public static List<Meeting> sMeetings =  Arrays.asList(

           // new Meeting(1,dateDebut,dateFin, sRooms.get(0), sujet,MAILS_PARTICIPANTS),
            new Meeting(1,dateDebut1,dateFin1,sRooms.get(0),"sujet1",MAILS_PARTICIPANTS),
            new Meeting(2,dateDebut2,dateFin2,sRooms.get(1),"sujet2",MAILS_PARTICIPANTS),
            new Meeting(3,dateDebut3,dateFin3,sRooms.get(2),"sujet3",MAILS_PARTICIPANTS)


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