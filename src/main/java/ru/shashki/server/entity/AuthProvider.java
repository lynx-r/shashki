package ru.shashki.server.entity;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 11.05.15
 * Time: 17:58
 */
public enum AuthProvider {
    VK("Vkontakte", "vk");

    private String label;
    private String dbRepresentation;

    AuthProvider(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDbRepresentation() {
        return dbRepresentation;
    }

    public void setDbRepresentation(String dbRepresentation) {
        this.dbRepresentation = dbRepresentation;
    }
}
