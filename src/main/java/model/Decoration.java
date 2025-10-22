package model;

import constant.FieldName;
import exception.factory.ExceptionFactory;

import java.util.Objects;

public class Decoration {

    private String name;
    private String material;
    private double price;
    private Long id;
    private Long roomId;

    public Decoration(String name, String material, double price) {
        validateName(name);
        validateName(material);
        validatePrice(price);

        this.name = name;
        this.material = material;
        this.price = price;
        this.id = null;
    }

    public String getName() {
        return this.name;
    }

    public String getMaterial() {
        return this.material;
    }

    public double getPrice() {
        return this.price;
    }

    public Long getId() {
        return this.id;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    private void validateName(String name) {
        if (name == null) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
        if (name.trim().isEmpty()) {
            throw ExceptionFactory.requiredField(FieldName.NAME);
        }
    }

    private void validatePrice(double price) {
        if (price <= 0) {
            throw ExceptionFactory.invalidPrice();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decoration other = (Decoration) o;

        return this.name.equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name != null ? this.name.toLowerCase() : null);
    }
}