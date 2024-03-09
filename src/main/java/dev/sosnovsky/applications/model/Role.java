package dev.sosnovsky.applications.model;

import lombok.ToString;

@ToString
public enum Role {
    USER,
    OPERATOR,
    ADMINISTRATOR
    /*USER("user"),
    OPERATOR("operator"),
    ADMINISTRATOR("administrator");

    private final String name;

    private Role(String name) {
        this.name = name;
    }

    public boolean equalRole(String otherRole) {
        return name.equals(otherRole);
    }*/
}
