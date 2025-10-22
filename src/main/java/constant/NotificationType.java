package constant;

public enum NotificationType {
    EMAIL("Correo electrónico"),
    GIFT("Regalo"),
    ACHIEVEMENT("Logro"),
    TICKET("Tiquet");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
