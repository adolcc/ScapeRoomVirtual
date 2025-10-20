package model;

public enum DifficultyLevel {
    VERY_EASY(1, "🌱 Principiante"),
    EASY(2, "🎯 Aventurero"),
    MEDIUM(3, "⚔️  Experto"),
    HARD(4, "🔥 Élite"),
    VERY_HARD(5, "💀 Maestro");

    private final int level;
    private String displayName;

    DifficultyLevel(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }

    public int getLevelValue() {
        return this.level;
    }

    public String getDisplayName() { return this.displayName; }

    public static DifficultyLevel fromInt(int level) {
        for (DifficultyLevel difficulty : values()) {
            if (difficulty.getLevelValue() == level) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Nivel de dificultad no válido: " + level);
    }
}


