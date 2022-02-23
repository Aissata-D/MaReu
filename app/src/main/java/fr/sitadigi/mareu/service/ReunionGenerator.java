package fr.sitadigi.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;

public  class ReunionGenerator {
    //Declaration de la liste des réunion et de la liste des mail des participants
    public static List<Participant> MAILS_PARTICIPANTS = Arrays.asList();
    public static List<Meeting> sMeetings =  Arrays.asList();

// Methodes retournant les listes
    public static List<Meeting> generatorReunion(){
       return new ArrayList<>(sMeetings);
   }

    public static List<Participant> generatorMailsParticipants(){
        return new ArrayList<>(MAILS_PARTICIPANTS);
    }
}
//public  static Participant mail1 = new Participant("diassana@yahoo") ;
// public  Meeting reunion1 = new Meeting("paris","sujet-de coonvert",MAILS_PARTICIPANTS);