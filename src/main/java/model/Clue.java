package model;

import java.util.Objects;

public class Clue {
    private String name;
    private double price;
    private Long id;
    private Long roomId;

    public Clue(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return Double.compare(price, clue.price) == 0 && Objects.equals(name, clue.name) && Objects.equals(id, clue.id) && Objects.equals(roomId, clue.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, id, roomId);
    }
}
