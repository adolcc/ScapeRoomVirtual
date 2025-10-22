package model;

public interface Observer {
    void update(String message, String source);
    String getEmail();
}