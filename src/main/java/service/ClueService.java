package service;

import constant.EntityType;
import exception.factory.ExceptionFactory;
import model.Clue;
import repository.dao.ClueDAO;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

public class ClueService {

    private final GenericDAO<Clue, Long> clueDAO;

    public ClueService() {
        this.clueDAO = new ClueDAO();
    }

    public void checkNotDuplicateName(String name) {
        if (clueDAO.findByName(name).isPresent()) {
            throw ExceptionFactory.duplicateValue(EntityType.CLUE, name);
        }
    }

    public Clue createClue(String name, double price) {
        checkNotDuplicateName(name);
        Clue clue = new Clue(name, price);
        return clueDAO.save(clue);
    }

    public List<Clue> getClues() {
        return clueDAO.findAll();
    }

    public Optional<Clue> getClue(Long id) { return clueDAO.findById(id); }
    public Optional<Clue> getClue(String name) { return clueDAO.findByName(name); }
    public boolean deleteClue(Long id) { return clueDAO.delete(id); }
    public boolean deleteClue(String name) {
        Optional<Clue> clue = clueDAO.findByName(name);
        return clue.map(c -> clueDAO.delete(c.getId())).orElse(false);
    }
}
