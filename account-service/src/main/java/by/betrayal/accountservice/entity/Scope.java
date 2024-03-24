package by.betrayal.accountservice.entity;

public enum Scope {
    READ_USER("user:read"),
    CREATE_USER("user:create"),
    UPDATE_USER("user:update"),
    DELETE_USER("user:delete");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
