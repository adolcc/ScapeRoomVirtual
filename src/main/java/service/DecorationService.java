package service;

import exception.DuplicateNameException;
import model.Decoration;
import repository.dao.DecorationDAO;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

public class DecorationService {

    private final GenericDAO<Decoration, Long> decorationDAO;

    public DecorationService() {
        this.decorationDAO = new DecorationDAO();
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
