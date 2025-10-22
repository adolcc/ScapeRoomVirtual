package ui.menu;

import exception.core.DuplicateResourceException;
import exception.core.ValidationException;
import constant.DifficultyLevel;

public class CreationHandlerMenu extends BaseHandlerMenu {

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
        } catch (DuplicateResourceException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear el Escape Room: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void createRoom() {
        try {
            System.out.println("\n🎯 Creando nueva sala . . .");
            String name = readStringInput("Nombre de la sala: ");
            System.out.println("Niveles de dificultad disponibles: ");
            for (DifficultyLevel level : DifficultyLevel.values()) {
                System.out.println(level.getLevelValue() + ". " + level.getDisplayName());
            }
            int levelInput = readIntInput("Nivel de la Sala (1 a 5): ");
            DifficultyLevel level = DifficultyLevel.fromInt(levelInput);
            double ticketPrice = readDoubleInput("Precio de la sala: ");
            roomService.createRoom(name, level, ticketPrice);
            System.out.println("✅ Sala '" + name + "' creada exitosamente.");
        } catch (DuplicateResourceException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
       } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear la sala: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void createClue() {
        try {
            System.out.println("\n🎯 Creando nueva pista . . .");
            String name = readStringInput("Tema de la pista: ");
            double price = readDoubleInput("Precio de la pista: ");
            clueService.createClue(name, price);
            System.out.println("✅ Pista '" + name + "' creada exitosamente.");
        } catch (DuplicateResourceException e) {
            System.out.println("❌ Error: Ya existe una pista con ese nombre.");
        } catch (ValidationException | IllegalArgumentException e) {
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
        } catch (DuplicateResourceException | ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado al crear la decoración: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scan.nextLine().trim();

                input = input.replace(',', '.');

                double value = Double.parseDouble(input);

                if (value < 0) {
                    System.out.println("❌ El precio no puede ser negativo.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingresa un precio válido.");
            } catch (Exception e) {
                System.out.println("❌ Error inesperado al leer el precio.");
                scan.nextLine();
            }
        }
    }
}
