package ui.menu;

import java.util.Random;

public class NotificationHandlerMenu extends Menu {

    @Override
    public void display() {
        do {
            clearScreen();
            showHeader("ENVIAR COMUNICACIONES");

            System.out.println("1. 🏆 Registro de logros de un jugador");
            System.out.println("2. 💰 Código de descuento a jugador frecuente");
            System.out.println("3. 📧 Invitación a newsletter del Escape Room");
            System.out.println("0. ↩️  Volver al menú principal");
            System.out.println("════════════════════════════════════════");

            int option = readIntInput("Selecciona una opción: ");
            handleOption(option);
        }while (!exit) ;
    }

    @Override
    public void handleOption(int option) {
        switch (option) {
            case 1:
                sendAchievements();
                break;
            case 2:
                sendDiscountCode();
                break;
            case 3:
                sendNewsletterInvitation();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("❌ Opción no válida. Elija una opción entre 0 y 3.");
                pressEnterToContinue();
        }
    }

    private void sendAchievements() {
        System.out.println("\n🏆 Enviando logros...");
        String playerEmail = readStringInput("Email del jugador: ");
        System.out.println("✅ Logros enviados a: " + playerEmail);
        pressEnterToContinue();
    }

    private void sendDiscountCode() {
        System.out.println("\n💰 Enviando código de descuento...");
        String playerEmail = readStringInput("Email del jugador frecuente: ");
        String code = "DESC25" + (new Random().nextInt(9000) + 1000);
        System.out.println("✅ Código de descuento enviado a " + playerEmail);
        System.out.println("   Código: " + code + " (20% de descuento)" + "' enviado a: " + playerEmail);
        pressEnterToContinue();
    }

    private void sendNewsletterInvitation() {
        String email = readStringInput("📧 Email para suscripción: ");
        System.out.println("✅ Invitación a newsletter enviada a: " + email);
        System.out.println("   El jugador recibirá actualizaciones mensuales");
        pressEnterToContinue();
    }
}