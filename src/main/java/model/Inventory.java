package model;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;


public final class Inventory {
    private Inventory() {
        throw new AssertionError("No se pueden instanciar las clases de utilidad");
    }

    public static double calculateTotalValue(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        double total = 0.0;

        if (rooms != null) {
            total += rooms.stream().mapToDouble(Room::getPrice).sum();
        }
        if (clues != null) {
            total += clues.stream().mapToDouble(Clue::getPrice).sum();
        }
        if (decorations != null) {
            total += decorations.stream().mapToDouble(Decoration::getPrice).sum();
        }

        return total;
    }

    public static InventoryStats generateStats(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        int roomCount = rooms != null ? rooms.size() : 0;
        int clueCount = clues != null ? clues.size() : 0;
        int decorationCount = decorations != null ? decorations.size() : 0;
        double totalValue = calculateTotalValue(rooms, clues, decorations);

        return new InventoryStats(roomCount, clueCount, decorationCount, totalValue);
    }

    public static double calculateAssetsByRoom(Room room, List<Clue> allClues, List<Decoration> allDecorations) {
        if (room == null) {
            return 0.0;
        }

        double total = room.getPrice();

        if (allClues != null) {
            total += allClues.stream()
                    .filter(clue -> room.getId() != null && room.getId().equals(clue.getRoomId()))
                    .mapToDouble(Clue::getPrice)
                    .sum();
        }

        if (allDecorations != null) {
            total += allDecorations.stream()
                    .filter(decoration -> room.getId() != null && room.getId().equals(decoration.getRoomId()))
                    .mapToDouble(Decoration::getPrice)
                    .sum();
        }

        return total;
    }
        public static double calculateAssetsByRoomList(List<Room> rooms, List<Clue> allClues, List<Decoration> allDecorations) {
        if (rooms == null || rooms.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Room room : rooms) {
            total += calculateAssetsByRoom(room, allClues, allDecorations);
        }
        return total;
    }

    public static Optional<Room> findRoomByName(List<Room> rooms, String roomName) {
        if (rooms == null || roomName == null) {
            return Optional.empty();
        }
        return rooms.stream()
                .filter(room -> room.getName().equalsIgnoreCase(roomName))
                .findFirst();
    }

    public static Optional<Clue> findClueByName(List<Clue> clues, String clueName) {
        if (clues == null || clueName == null) {
            return Optional.empty();
        }
        return clues.stream()
                .filter(clue -> clue.getName().equalsIgnoreCase(clueName))
                .findFirst();
    }

    public static Optional<Decoration> findDecorationByName(List<Decoration> decorations, String decorationName) {
        if (decorations == null || decorationName == null) {
            return Optional.empty();
        }
        return decorations.stream()
                .filter(decoration -> decoration.getName().equalsIgnoreCase(decorationName))
                .findFirst();
    }

    public static String generateSummary(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        InventoryStats stats = generateStats(rooms, clues, decorations);
        return String.format("Inventario - Salas: %d, Pistas: %d, Decoraciones: %d, Valor Total: %.2f€",
                stats.getRoomCount(),
                stats.getClueCount(),
                stats.getDecorationCount(),
                stats.getTotalValue());
    }

    public static List<RoomAssets> getRoomAssetsDetails(List<Room> rooms, List<Clue> allClues, List<Decoration> allDecorations) {
        if (rooms == null) {
            return List.of();
        }

        return rooms.stream()
                .map(room -> new RoomAssets(
                        room.getName(),
                        calculateAssetsByRoom(room, allClues, allDecorations)
                ))
                .collect(Collectors.toList());
    }


    public static double calculateAverageRoomPrice(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return 0.0;
        }
        return rooms.stream()
                .mapToDouble(Room::getPrice)
                .average()
                .orElse(0.0);
    }

    public static Optional<Room> findMostExpensiveRoom(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return Optional.empty();
        }
        return rooms.stream()
                .max((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()));
    }

    public static Optional<Room> findCheapestRoom(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return Optional.empty();
        }
        return rooms.stream()
                .min((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()));
    }

    public static String evaluateInventoryHealth(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        InventoryStats stats = generateStats(rooms, clues, decorations);

        if (stats.getTotalValue() == 0) {
            return "CRÍTICO - Inventario vacío";
        } else if (stats.getRoomCount() == 0) {
            return "ALTO RIESGO - No hay salas";
        } else if (stats.getTotalValue() < 1000) {
            return "BAJO - Valor total insuficiente";
        } else {
            return "SALUDABLE - Inventario en buen estado";
        }
    }
}


