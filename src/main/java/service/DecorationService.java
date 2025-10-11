package service;

import exception.DuplicateNameException;
import model.Decoration;
import repository.dao.DecorationDAOImpl;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

public class DecorationService {

    private final GenericDAO<Decoration, Long> decorationDAO;

    public DecorationService() {
        this.decorationDAO = new DecorationDAOImpl();
    }

    private void checkNotDuplicateName(String name) {
        if (decorationDAO.findByName(name).isPresent()) {
            throw new DuplicateNameException();
        }
    }

    public Decoration createDecoration(String name, String material, double price) {
        checkNotDuplicateName(name);
        Decoration decoration = new Decoration(name, material, price);
        return decorationDAO.save(decoration);
    }

    public Decoration addDecorationToRoom(Long decorationId, Long roomId) {
        Optional<Decoration> decorationOp = decorationDAO.findById(decorationId);
        if (decorationOp.isPresent()) {
            Decoration decoration = decorationOp.get();
            decoration.setRoomId(roomId);
            return decorationDAO.save(decoration);
        }
        throw new IllegalArgumentException("No se encontró la decoración con ID: " + decorationId + ".");
    }

    public Decoration removeDecorationFromRoom(Long decorationId) {
        Optional<Decoration> decorationOp = decorationDAO.findById(decorationId);
        if (decorationOp.isPresent()) {
            Decoration decoration = decorationOp.get();
            decoration.setRoomId(null);
            return decorationDAO.save(decoration);
        }
        throw new IllegalArgumentException("No se encontró la decoración con ID: " + decorationId + ".");
    }

    public List<Decoration> getDecorations() {
        return decorationDAO.findAll();
    }

    public Optional<Decoration> getDecoration(Long id) {
        return decorationDAO.findById(id);
    }
    public Optional<Decoration> getDecoration(String name) {
        return decorationDAO.findByName(name);
    }
    public boolean deleteDecoration(Long id) {
        return decorationDAO.delete(id);
    }
    public boolean deleteDecoration(String name) {
        Optional<Decoration> decoration = decorationDAO.findByName(name);
        return decoration.map(d -> decorationDAO.delete(d.getId())).orElse(false);
    }


}
