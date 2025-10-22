package ui.menu;

import constant.EntityType;
import constant.FieldName;
import exception.core.NotFoundException;
import exception.core.ValidationException;
import exception.factory.ExceptionFactory;
import model.Clue;
import model.Decoration;
import model.EscapeRoom;
import model.Room;

import java.util.Optional;

public class AdditionHandlerMenu extends BaseHandlerMenu {

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

        } catch (NotFoundException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
        pressEnterToContinue();
    }


    private void addClue() {
        try {
            String roomName = readStringInput("🚪 Nombre de la Sala: ");
            validateRoom(roomName);

            String clueName = readStringInput("🔍 Nombre de la Pista: ");
            validateClue(clueName);

            roomService.addClueToRoom(roomName, clueName);
            System.out.println("✅ Pista " + clueName + " añadida a la Sala " + roomName + ".");

        } catch (NotFoundException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }  catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
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

        } catch (NotFoundException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch(Exception e){
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private EscapeRoom validateEscapeRoom(String escapeRoomName) {
        if (escapeRoomName == null || escapeRoomName.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        Optional<EscapeRoom> escapeRoomOpt = escapeRoomService.getEscapeRoom(escapeRoomName.trim());
        if (escapeRoomOpt.isEmpty()) {
            throw ExceptionFactory.notFound(EntityType.ESCAPE_ROOM, escapeRoomName);
        }
        return escapeRoomOpt.get();
    }

    private Room validateRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        Optional<Room> roomOpt = roomService.getRoom(roomName.trim());
        if (roomOpt.isEmpty()) {
            throw ExceptionFactory.notFound(EntityType.ROOM, roomName);
        }
        return roomOpt.get();
    }

    private Clue validateClue(String clueName) {
        if (clueName == null || clueName.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        Optional<Clue> clueOpt = clueService.getClue(clueName.trim());
        if (clueOpt.isEmpty()) {
            throw ExceptionFactory.notFound(EntityType.CLUE, clueName);
        }
        return clueOpt.get();
    }

    private Decoration validateDecoration(String decorationName) {
        if (decorationName == null || decorationName.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        Optional<Decoration> decoOpt = decorationService.getDecoration(decorationName.trim());
        if (decoOpt.isEmpty()) {
            throw ExceptionFactory.notFound(EntityType.DECORATION, decorationName);
        }
        return decoOpt.get();
    }
}