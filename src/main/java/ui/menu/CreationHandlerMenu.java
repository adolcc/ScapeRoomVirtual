package ui.menu;

import exception.*;
import service.ClueService;
import service.DecorationService;
import service.EscapeRoomService;
import service.RoomService;

public class CreationHandlerMenu extends Menu {

    EscapeRoomService escapeRoomService;
    RoomService roomService;
    ClueService clueService;
    DecorationService decorationService;

    public CreationHandlerMenu() {
        this.escapeRoomService = new EscapeRoomService();
        this.roomService = new RoomService();
        this.clueService = new ClueService();
        this.decorationService = new DecorationService();
    }

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("CREAR . . .");
            System.out.println("1. 🏰 Nuevo Escape Room.");
            System.out.println("2. 🚪 Nueva sala.");
            System.out.println("3. 🔍 Nueva pista.");
            System.out.println("4. 🖼️ Nuevo objeto de decoración.");
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
                createEscapeRoom();
                break;
            case 2:
                createRoom();
                break;
            case 3:
                createClue();
                break;
            case 4:
                createDecoration();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 4.");
                pressEnterToContinue();
                break;
        }
    }

    private void createEscapeRoom() {
        try {
            System.out.println("\n🎯 Creando nuevo Escape Room . . .");
            String name = readStringInput("Nombre del Escape Room: ");
            escapeRoomService.createEscapeRoom(name);
            System.out.println("✅ Escape Room '" + name + "' creado exitosamente.");
        } catch (DuplicateEscapeRoomNameException e) {
            System.out.println("❌ Error: Ya existe un Escape Room con ese nombre.");
        } catch (EmptyEscapeRoomNameException | NullEscapeRoomNameException e) {
            System.out.println("❌ Error: El nombre del Escape Room no puede estar vacío.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear el Escape Room: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void createRoom() {
        try {
            System.out.println("\n🎯 Creando nueva sala . . .");
            String name = readStringInput("Nombre de la sala: ");
            int level = readIntInput("Nivel de la Sala (1 a 5): ");
            double ticketPrice = readDoubleInput("Precio de la sala: ");
            roomService.createRoom(name, level, ticketPrice);
            System.out.println("✅ Sala '" + name + "' creada exitosamente.");
        } catch (DuplicateRoomNameException e) {
            System.out.println("❌ Error: Ya existe una sala con ese nombre.");
        } catch (EmptyRoomNameException | NullEscapeRoomNameException e) {
            System.out.println("❌ Error: El nombre de la sala no puede estar vacío.");
        } catch (InvalidPriceException e) {
            System.out.println("❌ Error: El precio debe ser mayor a 0.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear la sala: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void createClue() {
        try {
            System.out.println("\n🎯 Creando nueva pista . . .");
            String name = readStringInput("Tema de la pista: ");
            double price = readIntInput("Precio de la pista: ");
            clueService.createClue(name, price);
            System.out.println("✅ Pista '" + name + "' creada exitosamente.");
        } catch (DuplicateClueNameException e) {
            System.out.println("❌ Error: Ya existe una pista con ese nombre.");
        } catch (EmptyClueNameException | NullClueNameException e) {
            System.out.println("❌ Error: El nombre de la pista no puede estar vacío.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear la pista: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void createDecoration() {
        try {
            System.out.println("\n🎯 Creando objeto de decoración . . .");
            String name = readStringInput("Nombre del objeto: ");
            String material = readStringInput("Material: ");
            double price = readDoubleInput("Precio: ");
            decorationService.createDecoration(name, material, price);
            System.out.println("✅ Decoración '" + name + "' creada exitosamente.");
        } catch (DuplicateNameException e) {
            System.out.println("❌ Error: Ya existe un objeto de decoración con ese nombre.");
        } catch (EmptyNameException | NullNameException e) {
            System.out.println("❌ Error: El nombre y material no pueden estar vacíos.");
        } catch (InvalidPriceException e) {
            System.out.println("❌ Error: El precio debe ser mayor a 0.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear la decoración: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                while (!scan.hasNextDouble()) {
                    System.out.println("❌ Por favor, ingresa un precio válido.");
                    scan.next();
                    System.out.print(prompt);
                }
                double input = scan.nextDouble();
                scan.nextLine();
                return input;
            } catch (Exception e) {
                System.out.println("❌ Error al leer el precio: " + e.getMessage());
                scan.nextLine();
            }
        }
    }
}
