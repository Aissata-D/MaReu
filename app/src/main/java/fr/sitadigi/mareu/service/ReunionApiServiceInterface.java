package fr.sitadigi.mareu.service;

import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;

public interface ReunionApiServiceInterface {


    List<Meeting> getReunion();
    List<Participant> getMailsParticipant();
    void addReunion(Meeting meeting);
    void removeReunion(Meeting meeting);
    void addMailParticipant(Participant participant);
    void removeMailParticipant(Participant participant);
}
