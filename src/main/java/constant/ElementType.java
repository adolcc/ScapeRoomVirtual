package constant;

public enum ElementType {
    CLUES("pistas"),
    DECORATIONS("objetos de decoración");

    private final String displayName;

    ElementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
