package ui.menu;

import exception.DecorationNotFoundException;
import exception.EscapeRoomNotFoundException;
import exception.RoomNotFoundException;
import model.Clue;
import model.Decoration;
import model.EscapeRoom;
import model.Room;
import service.ClueService;
import service.DecorationService;
import service.EscapeRoomService;
import service.RoomService;

import java.util.Optional;

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
        try {
            String escapeRoomName = readStringInput("🏰 Nombre del Escape Room: ");
            validateEscapeRoom(escapeRoomName);

            String roomName = readStringInput("🚪 Nombre de la Sala: ");
            Room room = validateRoom(roomName);

            escapeRoomService.addRoomToEscapeRoom(escapeRoomName, room);
            System.out.println("✅ Sala " + roomName + " añadida al Escape Room " + escapeRoomName + ".");

        } catch (EscapeRoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
    }


    private void addClue() {
        try {
            String roomName = readStringInput("🚪 Nombre de la Sala: ");
            Room room = validateRoom(roomName);

            String clueName = readStringInput("🔍 Nombre de la Pista: ");
            Clue clue = validateClue(clueName);

            //todo añadir addClueToRoom a RoomService
            roomService.addClueToRoom(roomName, clueName);
            System.out.println("✅ Pista " + clueName + " añadida a la Sala " + roomName + ".");

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
    }

    private void addDecoration() {
        try {
            String roomName = readStringInput("🚪 Nombre de la Sala: ");
            validateRoom(roomName);

            String decorationName = readStringInput("🖼️ Nombre de la Decoración: ");
            validateDecoration(decorationName);

            roomService.addDecorationToRoom(roomName, decorationName);
            System.out.println("✅ Decoración " + decorationName + " añadida a la sala " + roomName + ".");

        } catch (DecorationNotFoundException e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
    }

    private void validateEscapeRoom(String escapeRoomName) {
        Optional<EscapeRoom> escapeRoomOpt = escapeRoomService.getEscapeRoom(escapeRoomName);
        if (escapeRoomOpt.isEmpty()) {
            throw new EscapeRoomNotFoundException();
        }
    }

    private Room validateRoom(String roomName) {
        Optional<Room> roomOpt = roomService.getRoom(roomName);
        if (roomOpt.isEmpty()) {
            throw new RoomNotFoundException("❌ No se encontró la sala: " + roomName);
        }
        return roomOpt.get();
    }

    private Clue validateClue(String clueName) {
        //    todo -> añadir getClue a CLueServie y añadir/unificar excepciones
        Optional<Clue> clueOpt = clueService.getClue(clueName);
        if (clueOpt.isEmpty()) {
            throw new NotFoundException("❌ No se encontró la pista: " + clueName);
        }
        return clueOpt.get();
    }

    private Decoration validateDecoration(String decorationName) {
        Optional<Decoration> decoOpt = decorationService.getDecoration(decorationName);
        if (decoOpt.isEmpty()) {
            throw new DecorationNotFoundException("❌ No se encontró la decoración: " + decorationName);
        }
        return decoOpt.get();
    }

}