package dtos;

import java.util.Objects;

public class EscapeRoomDTO {
    private Integer id;
    private String name;

    public EscapeRoomDTO(){}

    public EscapeRoomDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return this.id; }
    public String getName() { return this.name; }
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EscapeRoomDTO other = (EscapeRoomDTO) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
