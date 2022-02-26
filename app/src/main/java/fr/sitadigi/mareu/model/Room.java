package fr.sitadigi.mareu.model;

public class Room {
    private int id;
    private String nameRoom;

    public Room(int id, String nameRoom) {
        this.id = id;
        this.nameRoom = nameRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }
}
