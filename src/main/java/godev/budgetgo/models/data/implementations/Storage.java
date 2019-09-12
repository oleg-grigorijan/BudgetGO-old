package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Identifiable;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class Storage implements Identifiable {
    private final long id;
    private final String name;
    private final Set<User> users;

    Storage(StorageBuilder builder) {
        id = builder.getId();
        name = builder.getName();
        users = Collections.unmodifiableSet(builder.getUsers());
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
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
