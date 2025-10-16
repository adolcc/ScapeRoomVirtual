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
            case 3:
                deleteClue();
                break;
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
        String name = readStringInput("Ingrese el nombre del Escape Room a eliminar: ");
        System.out.println("\n🗑️  Eliminando Escape Room " + name + " . . .");
        escapeRoomService.deleteEscapeRoom(name);
        System.out.println("✅ Escape Room eliminado exitosamente.");
        pressEnterToContinue();
    }

    private void deleteRoom() {
        String name = readStringInput("Ingrese el nombre de la Sala a eliminar:");
        System.out.println("\n🗑️  Eliminando sala " + name + " . . .");
        roomService.deleteRoom(name);
        System.out.println("✅ Sala eliminada exitosamente.");
        pressEnterToContinue();
    }

    private void deleteClue() {
        String name = readStringInput("Ingrese el nombre de la Pista a eliminar:");
        System.out.println("\n🗑️  Eliminando pista " + name + " . . .");
    //    clueService.deleteClue(name);
    //    System.out.println("✅ Pista eliminada exitosamente.");
        System.out.println(" ❌ En desarrollo . . .");
        pressEnterToContinue();
    }

    private void deleteDecoration() {
        String name = readStringInput("Ingrese el nombre del objeto de Decoración a eliminar:");
        System.out.println("\n🗑️  Eliminando objeto de decoración " + name + " . . .");
        decorationService.deleteDecoration(name);
        System.out.println("✅ Objeto de decoración eliminado exitosamente.");
        pressEnterToContinue();
    }
}
