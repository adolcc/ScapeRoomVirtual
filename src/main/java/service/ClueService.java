package service;

import exception.DuplicateClueNameException;
import exception.EmptyClueNameException;
import exception.NullClueNameException;
import model.Clue;

import java.util.HashSet;
import java.util.Set;

public class ClueService {

    private Set<Clue> clueSet;

    public ClueService() {
        this.clueSet = new HashSet<>();
    }

    public void checkNotNullName(String name) {
        if (name == null) {
            throw new NullClueNameException();
        }
    }

    public void checkNotEmptyName(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyClueNameException();
        }
    }

    public void checkNotDuplicateName(String name) {
        if (clueSet.contains(new Clue(name, 0))) {
            throw new DuplicateClueNameException();
        }
    }

    public void createClue(String name, double price) {
        checkNotNullName(name);
        checkNotEmptyName(name);
        checkNotDuplicateName(name);
        clueSet.add(new Clue(name, price));
    }

    public Set<Clue> getClues() {
        return clueSet;
    }

    public Clue findByName(String name) {
        return clueSet.stream()
                .filter(clue -> clue.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
