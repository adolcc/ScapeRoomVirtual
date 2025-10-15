package model;

import exception.*;
import java.util.ArrayList;
import java.util.List;


public class Room {

    private String name;
    private int level;
    private List<Clue> clues;
    private List<Decoration> decorations;
    private Long id;
    private Long escapeRoomId;
    private double price;

    public Room(String name, int level, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyRoomNameException();
        }
        this.name = name.trim();
        this.level = level;
        this.clues = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.price = price;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEscapeRoomId() {
        return escapeRoomId;
    }

    public void setEscapeRoomId(Long escapeRoomId) {
        this.escapeRoomId = escapeRoomId;
    }
    public Room (String name, int level){
        this(name,0,0);
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        if (id != null) {
            return id.equals(room.id);
        } else {
            return name.equalsIgnoreCase(room.name);
        }
    }


        @Override
        public int hashCode () {
            return id != null ? id.hashCode() : name.toLowerCase().hashCode();

        }

    public double getPrice() {
        return price;
    }

    public int getDifficultyLevel() {
        return level;
    }
}
