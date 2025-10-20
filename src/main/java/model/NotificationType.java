package model;

public enum NotificationType {
    EMAIL("Correo electrónico"),
    GIFT("Regalo"),
    ACHIEVEMENT("Logro"),
    TICKET("Entrada");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}