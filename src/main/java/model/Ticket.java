package model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Ticket {
    private String id;
    private String playerEmail;
    private LocalDateTime creationDate;
    private TicketStatus status;
    private String qrCode;


    public Ticket(String playerEmail) {
        if (playerEmail == null || playerEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("El email del jugador no puede ser nulo o vacío");
        }

        this.id = generateUniqueId();
        this.playerEmail = playerEmail.toLowerCase();
        this.creationDate = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
        this.qrCode = generateQRCode();
    }

    private String generateUniqueId() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateQRCode() {
        return "QR-" + this.id + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public String getId() { return id; }
    public String getPlayerEmail() { return playerEmail; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public TicketStatus getStatus() { return status; }
    public String getQrCode() { return qrCode; }

    public void setId(String id) { this.id = id; }

    public boolean isValid() {
        return this.status == TicketStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Ticket[id=%s, player=%s, status=%s, qrCode=%s]", id, playerEmail, status, qrCode);
    }
}
