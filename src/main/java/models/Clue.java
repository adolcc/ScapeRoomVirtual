package models;

public class Clue {
    private String theme;
    private double price;

    public Clue(String theme, double price) {
        this.theme = theme;
        this.price = price;
    }

    public String getTheme() {
        return theme;
    }

    public double getPrice() {
        return price;
    }
}
