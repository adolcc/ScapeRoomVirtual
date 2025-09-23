package models;

public class Clue {
    private String tema;
    private double precio;

    public Clue(String tema, double precio) {
        this.tema = tema;
        this.precio = precio;
    }

    public String getTema() {
        return tema;
    }

    public double getPrecio() {
        return precio;
    }
}