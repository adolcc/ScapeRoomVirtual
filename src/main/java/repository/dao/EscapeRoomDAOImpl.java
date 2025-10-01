package repository.dao;

import model.EscapeRoom;

import java.util.HashMap;
import java.util.Map;

public class EscapeRoomDAOImpl extends GenericDAO<EscapeRoom, Long> {

    private final Map<Long, EscapeRoom> database = new HashMap<>();
    private Long nextId = 1L;
}

@Override
public EscapeRoom save (EscapeRoom escapeRoom) {

}
