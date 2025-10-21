package constant;

public enum EntityType {

    PLAYER("Jugador"),
    ESCAPE_ROOM("Escape Room"),
    ROOM("Sala"),
    CLUE("Pista"),
    DECORATION("Decoración");

    private final String displayName;

    EntityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}


