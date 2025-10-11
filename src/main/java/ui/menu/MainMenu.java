package ui.menu;

import javax.swing.text.View;

public class MainMenu extends Menu {

    private CreateMenu createMenu;
    private AddMenu addMenu;
    private DeleteMenu deleteMenu;
    private ViewMenu viewMenu;
    private SendMenu sendMenu;
    private TicketMenu ticketMenu;

    public MainMenu() {
        this.createMenu = new CreateMenu();
        this.addMenu = new AddMenu();
        this.deleteMenu = new DeleteMenu();
        this.viewMenu = new ViewMenu();
        this.sendMenu = new SendMenu();
        this.ticketMenu = new TicketMenu();
    }

    @Override
    public void display() {
        while (!exit) {
            clearScreen();
            System.out.println("Bienvenido al Sistema de Gestión del Escape Room.");
            showHeader("ESCAPE ROOM MASTER");
            System.out.println("1. 🆕 Crear.");
            System.out.println("2. ➕ Añadir.");
            System.out.println("3. 🗑️  Eliminar.");
            System.out.println("4. 👁️  Ver.");
            System.out.println("5. 📤 Enviar.");
            System.out.println("6. 🎫 Gestión de Tickets.");
            System.out.println("0. 🚪 Salir.");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        }
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                createMenu.display();
                break;
            case 2:
                addMenu.display();
                break;
            case 3:
                deleteMenu.diplay();
                break;
            case 4:
                viewMenu.display();
                break;
            case 5:
                sendMenu.display();
                break;
            case 6:
                ticketMenu.display();
                break;
            case 0:
                exit = true;
                System.out.println("Gracias por su visita 👋 Hasta pronto ¡!");
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 6.");
                pressEnterToContinue();
                break;
        }
    }
}
