package me.subkek.elyrestorer.type;

public enum TaksType {
    APPLY_SKIN (0),
    GET_SKIN (1),
    SEND_MESSAGE (2);

    private final int id;

    TaksType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name();
    }
}