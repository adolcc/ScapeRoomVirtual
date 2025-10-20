package ui.menu;

import model.*;

import java.util.List;
import java.util.Optional;

public class ViewHandlerMenu extends BaseHandlerMenu {

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("VER INFORMACIÓN . . .");

            System.out.println("1. 🏰 Listado de Escape Rooms activos");
            System.out.println("2. 🚪 Listado de Salas");
            System.out.println("3. 🔍 Listado de Pistas");
            System.out.println("4. 🖼️ Listado de Objetos de Decoración");
            System.out.println("5. 📊 Resumen General del Inventario");
            System.out.println("6. 💰 Activos por Sala");
            System.out.println("0. ↩️  Volver al menú principal");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        } while (!exit);
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                listEscapeRooms();
                break;
            case 2:
                listRooms();
                break;
            case 3:
                listClues();
                break;
            case 4:
                listDecorations();
                break;
            case 5:
                showInventorySummary();
                break;
            case 6:
                showRoomAssets();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 6.");
                pressEnterToContinue();
        }
    }

    private void listEscapeRooms() {
        try {
            List<EscapeRoom> escapeRooms = escapeRoomService.getEscapeRooms();

            System.out.println("\n🏰 LISTADO DE ESCAPE ROOMS");
            System.out.println("┌─────┬────────────────────────────┬──────────────┐");
            System.out.println("│ ID  │ NOMBRE                     │  # SALAS     │");
            System.out.println("├─────┼────────────────────────────┼──────────────┤");

            if (escapeRooms.isEmpty()) {
                System.out.println("│" + centerText("No hay escape rooms registrados", 43) + "│");
            } else {
                for (EscapeRoom escapeRoom : escapeRooms) {
                    int roomCount = escapeRoom.getRooms() != null ? escapeRoom.getRooms().size() : 0;

                    System.out.printf("│ %-3d │ %-26s │ %-12d │%n",
                            escapeRoom.getId(),
                            truncate(escapeRoom.getName(), 26),
                            roomCount);
                }
            }
            System.out.println("└─────┴────────────────────────────┴──────────────┘");
            System.out.println("Total: " + escapeRooms.size() + " escape rooms");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar los escape rooms: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void listRooms() {
        try {
            List<Room> rooms = roomService.getRooms();

            System.out.println("\n🚪 LISTADO DE SALAS");
            System.out.println("┌─────┬────────────────────────────┬────────────────────┬───────────┬────────────┬────────────┬─────────────────┐");
            System.out.println("│ ID  │ NOMBRE                     │    DIFICULTAD      │   PRECIO  │  # PISTAS  │# DECORACIÓN│ ESCAPE ROOM     │");
            System.out.println("├─────┼────────────────────────────┼────────────────────┼───────────┼────────────┼────────────┼─────────────────┤");
            if (rooms.isEmpty()) {
                System.out.println("│" + centerText("No hay salas registradas", 103) + "│");
            } else {
                for (Room room : rooms) {
                    int clueCount = room.getClues() != null ? room.getClues().size() : 0;
                    int decorationCount = room.getDecorations() != null ? room.getDecorations().size() : 0;
                    String escapeRoomName = "Sin asignar";

                    if (room.getEscapeRoomId() != null) {
                        try {
                            Optional<EscapeRoom> escapeRoom = escapeRoomService.getEscapeRoom(room.getEscapeRoomId());
                            if (escapeRoom.isPresent()) {
                                escapeRoomName = truncate(escapeRoom.get().getName(), 15);
                            }
                        } catch (Exception e) {
                            escapeRoomName = "Error.";
                        }
                    }

                    System.out.printf("│ %-3d │ %-26s │ %-16s │ %-9.2f │ %-10d │ %-10d │ %-15s │%n",
                            room.getId() != null ? room.getId() : 0,
                            truncate(room.getName(), 26),
                            truncateWithEmoji(room.getLevel().getDisplayName(), 19),
                            room.getPrice(),
                            clueCount,
                            decorationCount,
                            escapeRoomName);
                }
            }
            System.out.println("└─────┴────────────────────────────┴────────────────────┴───────────┴────────────┴────────────┴─────────────────┘");
            System.out.println("Total: " + rooms.size() + " salas.");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar las salas: " + e.getMessage());
            System.err.println("Error técnico: " + e.getClass().getSimpleName());
        }
        pressEnterToContinue();
    }

    private void listClues() {
        try {
            List<Clue> clues = clueService.getClues();

            System.out.println("\n🔍 LISTADO DE PISTAS");
            System.out.println("┌─────┬────────────────────────────┬───────────┬─────────────────┐");
            System.out.println("│ ID  │ NOMBRE                     │   PRECIO  │ SALA ASIGNADA   │");
            System.out.println("├─────┼────────────────────────────┼───────────┼─────────────────┤");

            if (clues.isEmpty()) {
                System.out.println("│" + centerText("No hay pistas registradas", 65) + "│");
            } else {
                for (Clue clue : clues) {
                    String roomName = "Sin asignar";

                    if (clue.getRoomId() != null) {
                        Optional<Room> room = roomService.getRoom(clue.getRoomId());
                        if (room.isPresent()) {
                            roomName = truncate(room.get().getName(), 15);
                        }
                    }

                    System.out.printf("│ %-3d │ %-26s │ %-9.2f │ %-15s │%n",
                            clue.getId() != null ? clue.getId() : 0,
                            truncate(clue.getName(),26),
                            clue.getPrice(),
                            roomName);
                }
            }
            System.out.println("└─────┴────────────────────────────┴───────────┴─────────────────┘");
            System.out.println("Total: " + clues.size() + " pistas.");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar las pistas: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void listDecorations() {
        try {
            List<Decoration> decorations = decorationService.getDecorations();

            System.out.println("\n🖼️ LISTADO DE DECORACIONES");
            System.out.println("┌─────┬────────────────────────────┬────────────────────────────┬───────────┬─────────────────┐");
            System.out.println("│ ID  │ NOMBRE                     │ MATERIAL                   │   PRECIO  │ SALA ASIGNADA   │");
            System.out.println("├─────┼────────────────────────────┼────────────────────────────┼───────────┼─────────────────┤");

            if (decorations.isEmpty()) {
                System.out.println("│" + centerText("No hay decoraciones registradas", 97) + "│");
            } else {
                for (Decoration decoration : decorations) {
                    String roomName = "Sin asignar";

                    if (decoration.getRoomId() != null) {
                        Optional<Room> room = roomService.getRoom(decoration.getRoomId());
                        if (room.isPresent()) {
                            roomName = truncate(room.get().getName(), 15);
                        }
                    }

                    System.out.printf("│ %-3d │ %-26s │ %-26s │ %-9.2f │ %-15s │%n",
                            decoration.getId(),
                            truncate(decoration.getName(), 26),
                            truncate(decoration.getMaterial(), 26),
                            decoration.getPrice(),
                            roomName);
                }
            }
            System.out.println("└─────┴────────────────────────────┴────────────────────────────┴───────────┴─────────────────┘");
            System.out.println("Total: " + decorations.size() + " decoraciones");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar las decoraciones: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void showInventorySummary() {
        try {
            String summary = inventoryService.generateInventorySummary();
            String healthStatus = inventoryService.getInventoryHealthStatus();
            InventoryStats stats = inventoryService.getInventoryStats();

            double averagePrice = inventoryService.getAverageRoomPrice();
            Room mostExpensive = inventoryService.getMostExpensiveRoom();
            Room cheapest = inventoryService.getCheapestRoom();

            System.out.println("\n📊 RESUMEN GENERAL DEL INVENTARIO");
            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.println("║                ESTADÍSTICAS GLOBALES                 ║");
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.printf("║ 🏰  Escape Rooms: %-34d ║%n", escapeRoomService.getEscapeRooms().size());
            System.out.printf("║ 🚪  Salas: %-41d ║%n", stats.getRoomCount());
            System.out.printf("║ 🔍  Pistas: %-40d ║%n", stats.getClueCount());
            System.out.printf("║ 🖼️  Decoraciones: %-35d ║%n", stats.getDecorationCount());
            System.out.println("║                                                      ║");
            System.out.printf("║ 💰  Valor total del inventario: %-20.2f € ║%n", stats.getTotalValue());
            System.out.printf("║ 📈  Precio promedio de salas: %-19.2f € ║%n", averagePrice);
            System.out.printf("║ 🏷️  Sala más cara: %-31s ║%n",
                    mostExpensive != null ? truncate(mostExpensive.getName(), 30) : "N/A");
            System.out.printf("║ 💸  Sala más económica: %-26s ║%n",
                    cheapest != null ? truncate(cheapest.getName(), 25) : "N/A");
            System.out.println("║                                                      ║");
            System.out.printf("║ 🩺  Estado del inventario: %-25s ║%n", healthStatus);
            System.out.println("╚══════════════════════════════════════════════════════╝");
        } catch (Exception e) {
            System.out.println("❌ Error al generar el resumen: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    public void showRoomAssets() {
        try {
            List<RoomAssets> roomAssets = inventoryService.getRoomAssetsDetails();
            System.out.println("\n💰 ACTIVOS POR SALA");
            System.out.println("┌────────────────────────────┬─────────────────┐");
            System.out.println("│ NOMBRE DE LA SALA          │ VALOR TOTAL (€) │");
            System.out.println("├────────────────────────────┼─────────────────┤");

            if (roomAssets.isEmpty()) {
                System.out.println("│" + centerText("No hay salas con activos", 53) + "│");
            } else {
                for (RoomAssets asset : roomAssets) {
                    System.out.printf("│ %-26s │ %15.2f │%n",
                            truncate(asset.getRoomName(), 26),
                            asset.getTotalAssets());
                }
            }
            System.out.println("└────────────────────────────┴─────────────────┘");

            double total = roomAssets.stream().mapToDouble(RoomAssets::getTotalAssets).sum();
            System.out.printf("Total general: %.2f €%n", total);

        } catch (Exception e) {
            System.out.println("❌ Error al cargar los activos por sala: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        String plainText = text.replaceAll("[^\\p{ASCII}]", "X");
        if (plainText.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
    private String truncateWithEmoji(String text, int maxLength) {
        if (text == null) return "";

        int visualLength = 0;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (Character.isHighSurrogate(c)) {
                visualLength += 2;
            } else {
                visualLength += 1;
            }

            if (visualLength <= maxLength) {
                result.append(c);
            } else {
                break;
            }
        }

        String truncated = result.toString();
        if (truncated.length() < text.length()) {
            while (truncated.length() > 0 && Character.isHighSurrogate(truncated.charAt(truncated.length() - 1))) {
                truncated = truncated.substring(0, truncated.length() - 1);
            }
            return truncated + "...";
        }

        int padding = maxLength - visualLength;
        if (padding > 0) {
            return truncated + " ".repeat(padding);
        }

        return truncated;
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return truncate(text, width);
        }

        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }


}