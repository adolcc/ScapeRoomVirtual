package service;

import model.Player;
import model.Ticket;
import repository.dao.PlayerDAO;

import java.util.*;


public class TicketService {
    private static TicketService instance;
    private PlayerDAO playerDAO;

    private Map<String, Ticket> ticketsById;
    private Map<String, List<Ticket>> ticketsByPlayerEmail;

    private TicketService() {
        this.playerDAO = new PlayerDAO();
        this.ticketsById = new HashMap<>();
        this.ticketsByPlayerEmail = new HashMap<>();
    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public Ticket createTicket(String playerEmail) {

        Optional<Player> playerOpt = playerDAO.findByEmail(playerEmail);
        if (playerOpt.isEmpty()) {
            throw new IllegalArgumentException("No existe un jugador con email: " + playerEmail);
        }

        Ticket ticket = new Ticket(playerEmail);

        ticketsById.put(ticket.getId(), ticket);
        ticketsByPlayerEmail
                .computeIfAbsent(playerEmail.toLowerCase(), k -> new ArrayList<>())
                .add(ticket);
        return ticket;
    }

    public Optional<Ticket> findTicketById(String ticketId) {
        return Optional.ofNullable(ticketsById.get(ticketId));
    }

    public List<Ticket> findAllTickets() {
        return new ArrayList<>(ticketsById.values());
    }
}
