package me.subkek.elyrestorer.type;

import java.util.Objects;

public enum TaksType {
    APPLY_SKIN (0),
    GET_SKIN (1);

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