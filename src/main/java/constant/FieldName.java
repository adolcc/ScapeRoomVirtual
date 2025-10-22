package constant;

public enum FieldName {
    NAME("nombre"),
    EMAIL("email"),
    PRICE("precio");

    private final String displayName;

    FieldName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
