package ui.menu;

public class MainMenu extends Menu {

    private CreationHandlerMenu creationHandler;
    private AdditionHandlerMenu additionHandler;
    private RemovalHandlerMenu removalHandler;
    private ViewHandlerMenu viewHandler;
    private NotificationHandlerMenu notificationHandler;
    private TicketHandlerMenu ticketHandler;

    public MainMenu() {
        this.creationHandler = new CreationHandlerMenu();
        this.additionHandler = new AdditionHandlerMenu();
        this.removalHandler = new RemovalHandlerMenu();
        this.viewHandler = new ViewHandlerMenu();
        this.notificationHandler = new NotificationHandlerMenu();
        this.ticketHandler = new TicketHandlerMenu();
    }

    @Override
    public void display() {
        do {
            clearScreen();
            System.out.println("Bienvenido al Sistema de Gestión del Escape Room.");
            showHeader("ESCAPE ROOM MASTER");
            System.out.println("1. 🆕 Crear.");
            System.out.println("2. ➕ Añadir.");
            System.out.println("3. 🗑️ Eliminar.");
            System.out.println("4. 👁️ Ver.");
            System.out.println("5. 📤 Enviar.");
            System.out.println("6. 🎫 Gestión de Tickets.");
            System.out.println("0. 🚪 Salir.");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        } while (!exit);
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                creationHandler.display();
                break;
            case 2:
                additionHandler.display();
                break;
            case 3:
                removalHandler.display();
                break;
            case 4:
                viewHandler.display();
                break;
            case 5:
                notificationHandler.display();
                break;
            case 6:
                ticketHandler.display();
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
