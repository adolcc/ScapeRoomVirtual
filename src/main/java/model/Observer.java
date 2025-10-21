package model;

public interface Observer {
    void update(Subject subject);
    String getEmail();
}