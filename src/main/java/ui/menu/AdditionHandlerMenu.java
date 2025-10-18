package ui.menu;

import exception.*;
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
//            case 2:
//                addClue();
//                break;
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

        } catch (EscapeRoomNotFoundException | RoomNotFoundException | EmptyNameException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
        pressEnterToContinue();
    }


//    private void addClue() {
//        try {
//            String roomName = readStringInput("🚪 Nombre de la Sala: ");
//            validateRoom(roomName);
//
//            String clueName = readStringInput("🔍 Nombre de la Pista: ");
//            validateClue(clueName);
//
//            roomService.addClueToRoom(roomName, clueName);
//            System.out.println("✅ Pista " + clueName + " añadida a la Sala " + roomName + ".");
//
//        } catch (RoomNotFoundException | ClueNotFoundException | EmptyNameException e) {
//            System.out.println(e.getMessage());
//        }  catch (Exception e) {
//            System.out.println("❌ Error inesperado: " + e.getMessage());
//        }
//        pressEnterToContinue();
//    }

    private void addDecoration() {
        try {
            String roomName = readStringInput("🚪 Nombre de la Sala: ");
            validateRoom(roomName);

            String decorationName = readStringInput("🖼️ Nombre de la Decoración: ");
            validateDecoration(decorationName);

            roomService.addDecorationToRoom(roomName, decorationName);
            System.out.println("✅ Decoración " + decorationName + " añadida a la sala " + roomName + ".");

        } catch (RoomNotFoundException | DecorationNotFoundException | EmptyNameException e) {
            System.out.println(e.getMessage());
        } catch(Exception e){
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private EscapeRoom validateEscapeRoom(String escapeRoomName) {
        if (escapeRoomName == null || escapeRoomName.trim().isEmpty()) {
            throw new EmptyNameException();
        }
        Optional<EscapeRoom> escapeRoomOpt = escapeRoomService.getEscapeRoom(escapeRoomName.trim());
        if (escapeRoomOpt.isEmpty()) {
            throw new EscapeRoomNotFoundException();
        }
        return escapeRoomOpt.get();
    }

    private Room validateRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new EmptyNameException();
        }
        Optional<Room> roomOpt = roomService.getRoom(roomName.trim());
        if (roomOpt.isEmpty()) {
            throw new RoomNotFoundException("❌ No se encontró la sala: " + roomName);
        }
        return roomOpt.get();
    }

//    private Clue validateClue(String clueName) {
//        //    todo -> añadir getClue a CLueServie y añadir/unificar excepciones
//        if (clueName == null || clueName.trim().isEmpty()) {
//            throw new EmptyNameException();
//        }
//        Optional<Clue> clueOpt = clueService.getClue(clueName.trim());
//        if (clueOpt.isEmpty()) {
//            throw new ClueNotFoundException("❌ No se encontró la pista: " + clueName);
//        }
//        return clueOpt.get();
//    }

    private Decoration validateDecoration(String decorationName) {
        if (decorationName == null || decorationName.trim().isEmpty()) {
            throw new EmptyNameException();
        }
        Optional<Decoration> decoOpt = decorationService.getDecoration(decorationName.trim());
        if (decoOpt.isEmpty()) {
            throw new DecorationNotFoundException("❌ No se encontró la decoración: " + decorationName);
        }
        return decoOpt.get();
    }

}