package model;

public enum NotificationMessage {
    EMAIL("Correo electrónico"),
    GIFT("Regalo"),
    ACHIEVEMENT("Logro"),
    TICKET("Tiquet"),
    NEWSLETTER("Boletin informativo");

    private final String displayName;

    NotificationMessage(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
