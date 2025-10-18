import ui.menu.MainMenu;

public class EscapeRoomApp {
    public static void main(String[] args) {
        try {
            MainMenu mainMenu = new MainMenu();
            mainMenu.display();
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
