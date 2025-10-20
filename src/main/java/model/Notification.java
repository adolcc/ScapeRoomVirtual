package model;

import java.time.LocalDateTime;

public class Notification {
    private String id;
    private String recipientEmail;
    private String subject;
    private String message;
    private LocalDateTime sendDate;
    private NotificationType type;

    public Notification(String id, String recipientEmail, String subject, String message, NotificationType type) {
        if (recipientEmail == null || recipientEmail.isEmpty()) {
            throw new IllegalArgumentException("El email del destinatario no puede ser nulo o vacío");
        }
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.message = message;
        this.sendDate = LocalDateTime.now();
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public NotificationType getType() {
        return type;
    }
}