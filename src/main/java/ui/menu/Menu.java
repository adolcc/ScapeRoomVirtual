package ui.menu;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner scan;
    protected boolean exit;

    public Menu() {
        this.scan = new Scanner(System.in);
        this.exit = false;
    }

    public abstract void display();
    public abstract void handleOption(int option);

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected void showHeader(String title) {
        int totalWidth = 40;
        String paddedTitle = centerText(title, totalWidth);

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║" + paddedTitle + "║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    protected int readIntInput(String prompt) {
        System.out.print(prompt);
        while ((!scan.hasNextInt())) {
            System.out.println("❌ Por favor, ingresa un número válido.");
            scan.next();
            System.out.print(prompt);
        }
        int input = scan.nextInt();
        scan.nextLine();
        return input;
    }

    protected String readStringInput(String prompt) {
        System.out.println(prompt);
        return scan.nextLine().trim();
    }

    protected void pressEnterToContinue() {
        System.out.println("\n↵ Presione Enter para continuar . . . ");
        scan.nextLine();
    }
}
