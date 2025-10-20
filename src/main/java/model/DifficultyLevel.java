package model;

public enum DifficultyLevel {
    VERY_EASY(1),
    EASY(2),
    MEDIUM(3),
    HARD(4),
    VERY_HARD(5);

    private final int level;

    DifficultyLevel(int level) {
        this.level = level;
    }

    public int getLevelValue() {
        return level;
    }

    public static DifficultyLevel fromInt(int level) {
        for (DifficultyLevel difficulty : values()) {
            if (difficulty.getLevelValue() == level) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Nivel de dificultad no válido: " + level);
    }
}


