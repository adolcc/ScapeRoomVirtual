package model;

import exception.*;
import java.util.ArrayList;
import java.util.List;


public class Room {

    private String name;
    private int level;
    private List<Clue> clues;
    private List<Decoration> decorations;
    private long id;


    public Room(String name, int level) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyRoomNameException();
        }
        this.name = name.trim();
        this.level = level;
        this.clues = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }

    public void addClue(Clue clue) {
        clues.add(clue);
    }

    public void addDecoration(Decoration decoration) {
        decorations.add(decoration);
    }

    public void setId(long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }
     public void setName (String name){
        this.name = name;
     }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return name.equalsIgnoreCase(room.name);
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }
}