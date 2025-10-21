package model;

import constant.FieldName;
import exception.factory.ExceptionFactory;

import java.util.ArrayList;
import java.util.List;


public class Room {

    private String name;
    private DifficultyLevel level;
    private List<Clue> clues;
    private List<Decoration> decorations;
    private Long id;
    private Long escapeRoomId;
    private double price;

    public Room(String name, DifficultyLevel level, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        if (price <=0){
            throw ExceptionFactory.invalidPrice();
        }
        this.name = name.trim();
        this.level = level;
        this.clues = new ArrayList<>();
        this.decorations = new ArrayList<>();
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public DifficultyLevel getLevel() {
        return this.level;
    }

    public List<Clue> getClues() {
        return this.clues;
    }

    public List<Decoration> getDecorations() {
        return this.decorations;
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
    }

    public void setDecorations(List<Decoration> decorations) {
        this.decorations = decorations;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEscapeRoomId() {
        return this.escapeRoomId;
    }

    public void setEscapeRoomId(Long escapeRoomId) {
        this.escapeRoomId = escapeRoomId;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    public void setLevel(DifficultyLevel level) {
        this.level = level;
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
        return this.price;
    }
    public int getLevelValue() {
        return level != null ? level.getLevelValue() : 0;
    }
}
