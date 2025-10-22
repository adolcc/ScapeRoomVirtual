package ui.menu;

import model.Ticket;
import service.TicketService;

import java.util.List;
import java.util.Optional;

public class TicketHandlerMenu extends Menu {
    private TicketService ticketService = TicketService.getInstance();

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
        try {
            String playerEmail = readStringInput("📧 Email del jugador: ");
            Ticket ticket = ticketService.createTicket(playerEmail);
            System.out.println("✅ Ticket generado exitosamente:");
            System.out.println("   ID: " + ticket.getId());
            System.out.println("   Jugador: " + ticket.getPlayerEmail());
            System.out.println("   Estado: " + ticket.getStatus());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void viewTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        System.out.println("\n📊 TICKETS GENERADOS:");
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets generados.");
        } else {
            tickets.forEach(ticket ->
                    System.out.println("🎫 " + ticket.getId() + " - " +
                            ticket.getPlayerEmail() + " - " + ticket.getStatus()));
        }
        pressEnterToContinue();
    }

    private void findTicket() {
        String ticketId = readStringInput("🔍 ID del ticket: ");
        Optional<Ticket> ticket = ticketService.findTicketById(ticketId);
        if (ticket.isPresent()) {
            Ticket t = ticket.get();
            System.out.println("✅ Ticket encontrado:");
            System.out.println("   ID: " + t.getId());
            System.out.println("   Jugador: " + t.getPlayerEmail());
            System.out.println("   Fecha: " + t.getCreationDate());
            System.out.println("   Estado: " + t.getStatus());
            System.out.println("   Válido: " + (t.isValid() ? "✅" : "❌"));
        } else {
            System.out.println("❌ No se encontró el ticket con ID: " + ticketId);
        }
        pressEnterToContinue();
    }
}