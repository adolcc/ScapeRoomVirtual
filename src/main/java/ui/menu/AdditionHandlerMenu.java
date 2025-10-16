package ui.menu;

import model.Clue;
import model.Decoration;
import model.Room;
import service.ClueService;
import service.DecorationService;
import service.EscapeRoomService;
import service.RoomService;

import java.util.Optional;
import java.util.Set;

public class AdditionHandlerMenu extends Menu {

    EscapeRoomService escapeRoomService;
    RoomService roomService;
    ClueService clueService;
    DecorationService decorationService;

    public AdditionHandlerMenu() {
        this.escapeRoomService = new EscapeRoomService();
        this.roomService = new RoomService();
        this.clueService = new ClueService();
        this.decorationService = new DecorationService();
    }

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("AÑADIR . . .");
            System.out.println("1. 🚪 Sala a Escape Room existentes.");
            System.out.println("2. 🔍 Pista a sala existente.");
            System.out.println("3. 🖼️ Objeto de decoración a sala existente.");
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
                addRoom();
                break;
            case 2:
                addClue();
                break;
            case 3:
                addDecoration();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 3.");
                pressEnterToContinue();
                break;
        }
    }

    private void addRoom() {
        String escapeRoomName = readStringInput("🏰 Nombre del Escape Room: ");
        escapeRoomService.getEscapeRoom(escapeRoomName);
        String roomName = readStringInput("🚪 Nombre de la Sala: ");
    //    escapeRoomService.addRoomToEscapeRoom(escapeRoomName, roomName);
    //    System.out.println("✅ Sala " + roomName + " añadida al Escape Room " + escapeRoomName + ".");
    //    todo -> modificar mét addRoomToEscapeRoom para que reciba roomName en lugar de Room
        System.out.println(" ❌ En desarrollo . . .");
          }

    private void addClue() {
        String roomName = readStringInput("🚪 Nombre de la Sala: ");
        roomService.getRoom(roomName);
        String clueName = readStringInput("🔍 Nombre de la Pista: ");
    //    clueService.getClue(clueName);
    //    System.out.println("✅ Pista " + clueName + " añadida a la sala " + roomName + ".");
    //    todo -> añadir getClue a CLueServie
    }

    private void addDecoration() {
        String roomName = readStringInput("🚪 Nombre de la Sala: ");
        roomService.getRoom(roomName);
        String decorationName = readStringInput("🖼️ Nombre de la Decoración: ");
        decorationService.getDecoration(decorationName);
        System.out.println("✅ Decoración " + decorationName + " añadida a la sala " + roomName + ".");
    }
}