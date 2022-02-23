package fr.sitadigi.mareu.events;

import fr.sitadigi.mareu.model.Meeting;

public class DeleteReunionEvent {

    /**
     * Meeting to delete
     */
    public Meeting mMeeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteReunionEvent(Meeting meeting) {
        mMeeting = meeting;
    }


}
