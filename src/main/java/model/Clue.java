package model;

import java.util.Objects;

public class Clue {
    private String name;
    private double price;
    public Clue(String name,double price) {
        this.name = name;
        this.price= price;
    }
    public Clue ( String name){
        this.name = name;
        this.price = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(){
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return Objects.equals(name, clue.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Clue{" +
                "name='" + name + '\'' +", price=" + price +
                '}';
    }
}
