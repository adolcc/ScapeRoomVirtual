package model;

import java.util.Objects;

public class EscapeRoom {

    private String name;

    public EscapeRoom(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        EscapeRoom other = (EscapeRoom) o;
        return this.name.equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name.toLowerCase());
    }
}
