package ui.menu;

public class TicketHandlerMenu extends Menu {

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("GESTIÓN DE TICKETS");

            System.out.println("1. 🎫 Generar ticket de venta");
            System.out.println("2. 📊 Ver tickets generados");
            System.out.println("3. 🔍 Buscar ticket por ID");
            System.out.println("0. ↩️  Volver al menú principal");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        } while (!exit);
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                generateTicket();
                break;
            case 2:
                viewTickets();
                break;
            case 3:
                findTicket();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 3.");
                pressEnterToContinue();
        }
    }

    private void generateTicket() {
        System.out.println("\n🎫 Generando ticket...");
        // TODO: Integrar con TicketService
        System.out.println("✅ Ticket generado exitosamente.");
        pressEnterToContinue();
    }

    private void viewTickets() {
        System.out.println("\n📊 Listando tickets...");
        // TODO: Integrar con TicketService
        System.out.println("✅ Funcionalidad en desarrollo.");
        pressEnterToContinue();
    }

    private void findTicket() {
        System.out.println("\n🔍 Buscando ticket...");
        Long ticketId = readIntInput("ID del ticket: ").longValue();
        // TODO: Integrar con TicketService
        System.out.println("✅ Funcionalidad en desarrollo.");
        pressEnterToContinue();
    }
}