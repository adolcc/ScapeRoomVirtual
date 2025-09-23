package services;

import exceptions.*;
import models.EscapeRoom;
import models.Room;

import java.util.HashSet;
import java.util.Set;

public class EscapeRoomService {

    private Set<EscapeRoom> escapeRoomSet;

    public EscapeRoomService() {
        this.escapeRoomSet = new HashSet<>();
    }

    // Validaciones de nombre
    public void checkNotNullName(String name) {
        if (name == null) {
            throw new NullEscapeRoomNameException("El nombre del Escape Room no puede ser nulo.");
        }
    }

    public void checkNotEmptyName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            throw new EmptyEscapeRoomNameException("El nombre del Escape Room no puede estar vacío.");
        }
    }

    public void checkNotDuplicateName(String name) {
        if (escapeRoomSet.contains(new EscapeRoom(name))) {
            throw new DuplicateEscapeRoomNameException("El nombre elegido corresponde a un Escape Room existente.");
        }
    }

    // Crear Escape Room
    public void createEscapeRoom(String name) {
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        escapeRoomSet.add(new EscapeRoom(name.trim()));
    }

    // Obtener Escape Room por nombre
    public EscapeRoom getEscapeRoom(String name) {
        return escapeRoomSet.stream()
                .filter(er -> er.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }

    // Obtener todos los Escape Rooms
    public Set<EscapeRoom> getEscapeRooms() {
        return escapeRoomSet;
    }

    // Añadir sala a un Escape Room
    public void addRoomToEscapeRoom(String escapeRoomName, Room room) {
        EscapeRoom escapeRoom = getEscapeRoom(escapeRoomName);
        if (escapeRoom == null) {
            throw new RuntimeException("Escape Room no encontrado.");
        }

        String nameRoom = room.getName().trim();
        if (nameRoom.isEmpty()) {
            throw new EmptyRoomNameException("El nombre de la sala no puede estar vacío.");
        }

        boolean duplicado = escapeRoom.getRooms().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(nameRoom));
        if (duplicado) {
            throw new DuplicateRoomNameException("Ya existe una sala con ese nombre en el Escape Room.");
        }

        if (room.getClues().size() < 2) {
            throw new InsufficientCluesException("La sala debe contener al menos dos pistas.");
        }

        if (room.getDecorations().size() < 2) {
            throw new InsufficientDecorationsException("La sala debe contener al menos dos objetos de decoración.");
        }

        escapeRoom.addRoom(room);
    }
}
