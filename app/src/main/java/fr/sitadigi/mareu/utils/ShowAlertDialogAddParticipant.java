package fr.sitadigi.mareu.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import fr.sitadigi.mareu.di.Injection;
import fr.sitadigi.mareu.model.Participant;
import fr.sitadigi.mareu.service.MeetingApiServiceInterface;

public class ShowAlertDialogAddParticipant {

    MeetingApiServiceInterface mApiServiceInterface;
    private List<Participant> mParticipants;
    final boolean[] checkedInfos = new boolean[]{false, false, false, false};
    private List<Participant> mParticipantSelecteds = new ArrayList<>();
    MaterialTextView mListeParticipant;

    public ShowAlertDialogAddParticipant(List<Participant> participantSelecteds, MaterialTextView listeParticipant) {
        mParticipantSelecteds = participantSelecteds;
        mListeParticipant = listeParticipant;
    }

    public void showAlertDialogAddParticipant(final Activity activity) {
        mApiServiceInterface = Injection.getService();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mParticipants = mApiServiceInterface.getMeetingParticipant();
        // Set Title.
        builder.setTitle("Select participant");
        // Add a list of participants name
        final String[] participant = {mParticipants.get(0).getNameParticipant(),
                mParticipants.get(1).getNameParticipant(), mParticipants.get(2).getNameParticipant(),
                mParticipants.get(3).getNameParticipant()};
        builder.setMultiChoiceItems(participant, checkedInfos, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedInfos[which] = isChecked;
                for (int i = 0; i < participant.length; i++) {
                    if (checkedInfos[i] && !mParticipantSelecteds.contains(mParticipants.get(i))) {
                        mParticipantSelecteds.add(mParticipants.get(i));
                    } else if (!checkedInfos[i] && mParticipantSelecteds.contains(mParticipants.get(i))) {
                        mParticipantSelecteds.remove(mParticipants.get(i));
                    }
                }
            }
        });

        builder.setCancelable(true);
        // Create "Yes" button with OnClickListener.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Close Dialog
                dialog.dismiss();
                // Fill string that contains participant name
                String s = null;
                for (int i = 0; i < participant.length; i++) {
                    if (checkedInfos[i]) {
                        if (s == null) {
                            s = participant[i];
                            // mParticipantSelecteds.add(mParticipants.get(i));
                        } else {
                            s += ", " + participant[i];
                        }
                    }
                }
                s = s == null ? "" : s;
                //  SET mListeParticipant
                mListeParticipant.setText(s);
            }
        });

        // Create "Cancel" button with OnClickListener.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(activity, "You choose Cancel button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel dialog
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
    }
}
