package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Observer {
    private Long id;
    private String name;
    private String email;
    private boolean newsletterSubscribed;
    private List<String> receivedNotifications = new ArrayList<>();


    public Player(String name, String email) {
        validateEmail(email);
        this.id = null;
        this.name = name;
        this.email = email;
        this.newsletterSubscribed = false;
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void update(String message, String source) {
        System.out.println("Jugador " + name + " (" + email + ") recibió notificación de " +
                source + ": " + message);
        receivedNotifications.add(message);
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public boolean isNewsletterSubscribed() {
        return this.newsletterSubscribed;
    }

    public void setNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player other = (Player) o;

        return this.email.equalsIgnoreCase(other.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email != null ? this.email.toLowerCase() : null);
    }
}