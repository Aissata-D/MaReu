package fr.sitadigi.mareu.service;

import java.util.List;

import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.model.Meeting;

public class ReunionApiServiceImplementation implements ReunionApiServiceInterface {

   public List<Meeting> mMeetings = ReunionGenerator.generatorReunion();
    List<Participant> mailsParticipants = ReunionGenerator.generatorMailsParticipants();

    @Override
    public List<Meeting> getReunion() {
        return mMeetings;
    }

    @Override
    public List<Participant> getMailsParticipant() {
        return mailsParticipants;
    }

    @Override
    public void addReunion(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void removeReunion(Meeting meeting) {
        mMeetings.remove(meeting);

    }

    @Override
    public void addMailParticipant(Participant participant) {
        mailsParticipants.add(participant);
    }

    @Override
    public void removeMailParticipant(Participant participant) {
        mailsParticipants.remove(participant);
    }
}
