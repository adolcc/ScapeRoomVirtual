package ui.menu;

import model.Clue;
import model.Decoration;
import model.EscapeRoom;
import model.Room;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 5.");
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
            System.out.println("│ ID  │ TEMA                       │   PRECIO  │ SALA ASIGNADA   │");
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
            List<EscapeRoom> escapeRooms = escapeRoomService.getEscapeRooms();
            List<Room> rooms = roomService.getRooms();
            List<Clue> clues = clueService.getClues();
            List<Decoration> decorations = decorationService.getDecorations();

            System.out.println("\n📊 RESUMEN GENERAL DEL INVENTARIO");
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║                ESTADÍSTICAS GLOBALES           ║");
            System.out.println("╠════════════════════════════════════════════════╣");
            System.out.printf("║ 🏰  Escape Rooms: %-28d ║%n", escapeRooms.size());
            System.out.printf("║ 🚪  Salas: %-35d ║%n", rooms.size());
            System.out.printf("║ 🔍  Pistas: %-34d ║%n", 0); // Temporal
            System.out.printf("║ 🖼️  Decoraciones: %-29d ║%n", decorations.size());
            System.out.println("║                                                ║");

            // TODO: Calcular valor total cuando esté implementado
            double totalValue = calculateTotalValue(rooms, clues, decorations);
            System.out.printf("║ 💰  Valor total del inventario: %-15.2f € ║%n", totalValue);
            System.out.println("╚════════════════════════════════════════════════╝");

        } catch (Exception e) {
            System.out.println("❌ Error al generar el resumen: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private double calculateTotalValue(List<Room> rooms, List<Clue> clues, List<Decoration> decorations) {
        double total = 0.0;

        for (Room room : rooms) {
            total += room.getPrice();
        }

        for (Clue clue : clues) {
            total += clue.getPrice();
        }

        for (Decoration decoration : decorations) {
            total += decoration.getPrice();
        }
        return total;
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

        // Contar emojis como 2 caracteres para el cálculo de longitud
        int visualLength = 0;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Si es emoji (caracteres Unicode fuera del BMP), contar como 2
            if (Character.isHighSurrogate(c)) {
                visualLength += 2;
            } else {
                // Caracteres normales contar como 1
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
            // Asegurarse de no cortar en medio de un emoji
            while (truncated.length() > 0 && Character.isHighSurrogate(truncated.charAt(truncated.length() - 1))) {
                truncated = truncated.substring(0, truncated.length() - 1);
            }
            return truncated + "...";
        }

        // Rellenar con espacios para alinear
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