package ui.menu;

import service.ClueService;
import service.DecorationService;
import service.EscapeRoomService;
import service.RoomService;

public abstract class BaseHandlerMenu extends Menu {
    protected EscapeRoomService escapeRoomService;
    protected RoomService roomService;
    protected ClueService clueService;
    protected DecorationService decorationService;

    public BaseHandlerMenu() {
        this.escapeRoomService = new EscapeRoomService();
        this.roomService = new RoomService();
        this.clueService = new ClueService();
        this.decorationService = new DecorationService();
    }
}
