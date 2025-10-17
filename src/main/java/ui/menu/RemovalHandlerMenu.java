package ui.menu;

import service.ClueService;
import service.DecorationService;
import service.EscapeRoomService;
import service.RoomService;

public class RemovalHandlerMenu extends Menu {

    EscapeRoomService escapeRoomService;
    RoomService roomService;
    ClueService clueService;
    DecorationService decorationService;

    public RemovalHandlerMenu() {
        this.escapeRoomService = new EscapeRoomService();
        this.roomService = new RoomService();
        this.clueService = new ClueService();
        this.decorationService = new DecorationService();
    }

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("ELIMINAR . . . ");

            System.out.println("1. 🏰 Escape Room.");
            System.out.println("2. 🚪 Sala.");
            System.out.println("3. 🔍 Pista de una sala.");
            System.out.println("4. 🖼️ Objeto de decoración de una sala.");
            System.out.println("0. ↩️ Volver al menú principal.");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        } while (!exit);
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                deleteEscapeRoom();
                break;
            case 2:
                deleteRoom();
                break;
//            case 3:
//                deleteClue();
//                break;
            case 4:
                deleteDecoration();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 4.");
                pressEnterToContinue();
        }
    }

    private void deleteEscapeRoom() {
        try {
            String name = readStringInput("Ingrese el nombre del Escape Room a eliminar: ");
            System.out.println("\n🗑️  Eliminando Escape Room " + name + " . . .");

            boolean deleted = escapeRoomService.deleteEscapeRoom(name);

            if (deleted) {
                System.out.println("✅ Escape Room eliminado exitosamente.");
            } else {
                System.out.println("❌ No se encontró el Escape Room: " + name);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar el Escape Room: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void deleteRoom() {
        try {
            String name = readStringInput("Ingrese el nombre de la Sala a eliminar:");
            System.out.println("\n🗑️  Eliminando sala " + name + " . . .");

            boolean deleted = roomService.deleteRoom(name);

            if (deleted) {
                System.out.println("✅ Sala eliminada exitosamente.");
            } else {
                System.out.println("❌ No se encontró la sala: " + name);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar la sala: " + e.getMessage());
        }
        pressEnterToContinue();
    }

//    private void deleteClue() {
//        try {
//            String name = readStringInput("Ingrese el nombre de la Pista a eliminar:");
//            System.out.println("\n🗑️  Eliminando pista " + name + " . . .");
//
//            //todo -> añadir deleteClue en clueService
//            boolean deleted = clueService.deleteClue(name);
//
//            if (deleted) {
//                System.out.println("✅ Pista eliminada exitosamente.");
//            } else {
//                System.out.println("❌ No se encontró la pista: " + name);
//            }
//        } catch (Exception e) {
//            System.out.println("❌ Error al eliminar pista: " + e.getMessage());
//        }
//        pressEnterToContinue();
//    }

    private void deleteDecoration() {
        try {
            String name = readStringInput("Ingrese el nombre del objeto de Decoración a eliminar:");
            System.out.println("\n🗑️  Eliminando objeto de decoración " + name + " . . .");

            boolean deleted = roomService.deleteRoom(name);

            if (deleted) {
                System.out.println("✅ Decoración eliminada exitosamente.");
            } else {
                System.out.println("❌ No se encontró la decoración: " + name);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar decoración: " + e.getMessage());
        }
        pressEnterToContinue();
    }
}
