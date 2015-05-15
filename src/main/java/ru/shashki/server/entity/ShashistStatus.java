package ru.shashki.server.entity;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.05.15
 * Time: 18:16
 */
public enum ShashistStatus {
    PLAYING("Playing", "playing"),
    ONLINE("Online", "online"),
    OFFLINE("Offline", "offline");

    private final String label;
    private final String dbRepresentation;

    ShashistStatus(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public String getLabel() {
        return label;
    }

    public String getDbRepresentation() {
        return dbRepresentation;
    }
}
