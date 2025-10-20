package model;

import java.util.UUID;

public class Player {
    private String id;
    private String name;
    private String email;
    private boolean newsletterSubscribed;

    public Player(String name, String email) {
        validateEmail(email);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.newsletterSubscribed = false;
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public boolean isNewsletterSubscribed() {
        return newsletterSubscribed;
    }

    public void setNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", newsletterSubscribed=" + newsletterSubscribed +
                '}';
    }
}