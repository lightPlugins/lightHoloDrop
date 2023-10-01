package de.lightplugins.enums;

public enum PersistentDataPaths {

    ashuraBoxID("ASHURA_BOX_ID"),
    ;

    private String type;
    PersistentDataPaths(String type) { this.type = type; }
    public String getType() {

        return type;
    }
}
