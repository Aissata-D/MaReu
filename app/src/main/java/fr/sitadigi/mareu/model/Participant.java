package fr.sitadigi.mareu.model;

public class Participant {

    private int id;
    private String nameParticipant;
    private String adresseMail;

    public Participant(int id, String nameParticipant, String adresseMail) {
        this.id = id;
        this.nameParticipant = nameParticipant;
        this.adresseMail = adresseMail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameParticipant() {
        return nameParticipant;
    }

    public void setNameParticipant(String nameParticipant) {
        this.nameParticipant = nameParticipant;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }
}
