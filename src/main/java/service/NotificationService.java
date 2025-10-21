package service;

import model.Notification;
import model.NotificationType;
import model.Observer;
import model.Subject;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Notification currentNotification;

    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void sendAchievementNotification(String email, String achievementName) {
        sendNotification(email, "¡Nuevo logro desbloqueado!",
                "Has conseguido: " + achievementName,
                NotificationType.ACHIEVEMENT);
    }

    public void sendTicketNotification(String email, String subject, String message) {
        sendNotification(email, subject, message, NotificationType.TICKET);
    }

    public void sendGiftNotification(String email, String giftName) {
        sendNotification(email, "¡Has recibido un regalo!",
                "Te han enviado: " + giftName,
                NotificationType.GIFT);
    }

    public Notification getCurrentNotification() {
        return currentNotification;
    }

    public void sendNotification(String recipientEmail, String subject, String message, NotificationType type) {
        String id = String.valueOf(System.currentTimeMillis());
        currentNotification = new Notification(id, recipientEmail, subject, message, type);
        notifyObservers();
    }

    public void sendNewsletterToAll(String subject, String message) {
        sendNotification("all", subject, message, NotificationType.NEWSLETTER);
    }

    public void sendPersonalNotification(String email, String subject, String message) {
        sendNotification(email, subject, message, NotificationType.EMAIL);
    }
}