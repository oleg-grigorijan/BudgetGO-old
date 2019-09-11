package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.StorageData;
import godev.budgetgo.models.data.UserData;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Storage implements StorageData {
    private final long id;
    private final String name;
    private final List<UserData> users;

    Storage(StorageBuilder builder) {
        id = builder.getId();
        name = builder.getName();
        users = Collections.unmodifiableList(builder.getUsers());
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
    public List<UserData> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storage)) return false;
        Storage storage = (Storage) o;
        return id == storage.id &&
                name.equals(storage.name) &&
                users.equals(storage.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }
}
