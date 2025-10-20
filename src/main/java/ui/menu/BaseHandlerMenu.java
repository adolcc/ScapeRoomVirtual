package ui.menu;

import repository.dao.ClueDAO;
import repository.dao.DecorationDAO;
import repository.dao.RoomDAO;
import service.*;

public abstract class BaseHandlerMenu extends Menu {
    protected EscapeRoomService escapeRoomService;
    protected RoomService roomService;
    protected ClueService clueService;
    protected DecorationService decorationService;
    protected InventoryService inventoryService;

    public BaseHandlerMenu() {
        this.escapeRoomService = new EscapeRoomService();
        this.roomService = new RoomService();
        this.clueService = new ClueService();
        this.decorationService = new DecorationService();

        RoomDAO roomDAO = new RoomDAO();
        ClueDAO clueDAO = new ClueDAO();
        DecorationDAO decorationDAO = new DecorationDAO();
        this.inventoryService = new InventoryService(roomDAO, clueDAO, decorationDAO);

    }
}
