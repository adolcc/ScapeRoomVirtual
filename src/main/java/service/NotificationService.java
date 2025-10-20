package service;

import model.Notification;
import model.Player;

import java.util.*;
import java.util.stream.Collectors;

public class NotificationService {
    private static NotificationService instance;
    private Map<String, Player> playersByEmail;
    private List<Notification> notifications;

    private NotificationService() {
        this.playersByEmail = new HashMap<>();
        this.notifications = new ArrayList<>();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public boolean registerPlayer(String name, String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Verificar duplicados
        if (playersByEmail.containsKey(email.toLowerCase())) {
            return false;
        }

        try {
            Player player = new Player(name, email);
            playersByEmail.put(email.toLowerCase(), player);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Player findPlayerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return playersByEmail.get(email.toLowerCase());
    }

    public boolean updateSubscription(String email, boolean subscribe) {
        Player player = findPlayerByEmail(email);
        if (player == null) {
            return false;
        }

        player.setNewsletterSubscribed(subscribe);
        return true;
    }

    public List<Player> getSubscribedPlayers() {
        return playersByEmail.values().stream()
                .filter(Player::isNewsletterSubscribed)
                .collect(Collectors.toList());
    }

    public boolean sendNotification(String email, String subject, String message) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        Player player = findPlayerByEmail(email);
        if (player == null) {
            return false;
        }

        String id = UUID.randomUUID().toString();
        Notification notification = new Notification(
            id, email, subject, message, Notification.NotificationType.EMAIL
        );

        notifications.add(notification);
        System.out.println("Notificación enviada: " + notification);
        return true;
    }

    public boolean sendGift(String email, String giftName, String description) {
        Player player = findPlayerByEmail(email);
        if (player == null) {
            return false;
        }

        String subject = "¡Has recibido un regalo!";
        String message = "Regalo: " + giftName + "\n" + description;
        String id = UUID.randomUUID().toString();

        Notification notification = new Notification(
            id, email, subject, message, Notification.NotificationType.GIFT
        );

        notifications.add(notification);
        System.out.println("Regalo enviado: " + notification);
        return true;
    }

    public boolean registerAchievement(String email, String achievementName, String description) {
        Player player = findPlayerByEmail(email);
        if (player == null) {
            return false;
        }

        String subject = "¡Nuevo logro desbloqueado!";
        String message = "Has conseguido: " + achievementName + "\n" + description;
        String id = UUID.randomUUID().toString();

        Notification notification = new Notification(
            id, email, subject, message, Notification.NotificationType.ACHIEVEMENT
        );

        notifications.add(notification);
        System.out.println("Logro registrado: " + notification);
        return true;
    }

    public boolean associateTicket(String email, String ticketId) {
        Player player = findPlayerByEmail(email);
        if (player == null || ticketId == null || ticketId.trim().isEmpty()) {
            return false;
        }

        String subject = "Ticket asociado: #" + ticketId;
        String message = "Se ha asociado el ticket #" + ticketId + " a tu cuenta.";
        String id = UUID.randomUUID().toString();

        Notification notification = new Notification(
            id, email, subject, message, Notification.NotificationType.TICKET
        );

        notifications.add(notification);
        System.out.println("Ticket asociado: " + notification);
        return true;
    }

    public List<Notification> getNotificationsByEmail(String email) {
        if (email == null) {
            return Collections.emptyList();
        }

        return notifications.stream()
                .filter(n -> n.getRecipientEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}