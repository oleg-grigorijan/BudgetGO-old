package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.StorageData;

import java.util.Objects;

public final class Storage implements StorageData {
    private final long id;
    private final String name;

    Storage(StorageBuilder builder) {
        id = builder.getId();
        name = builder.getName();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storage)) return false;
        Storage storage = (Storage) o;
        return id == storage.id &&
                name.equals(storage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
