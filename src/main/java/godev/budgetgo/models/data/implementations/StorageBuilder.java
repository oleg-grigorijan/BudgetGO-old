package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;

import java.util.HashSet;
import java.util.Set;

public class StorageBuilder implements DataBuilder<Storage> {
    private long id = -1;
    private String name = "";
    private Set<User> users = new HashSet<>();

    @Override
    public StorageBuilder from(Storage entity) {
        id = entity.getId();
        name = entity.getName();
        users = entity.getUsers();
        return this;
    }

    long getId() {
        return id;
    }

    @Override
    public StorageBuilder setId(long id) {
        this.id = id;
        return this;
    }

    String getName() {
        return name;
    }

    public StorageBuilder setName(String name) {
        if (name == null) throw new NullPointerException();
        this.name = name;
        return this;
    }

    Set<User> getUsers() {
        return users;
    }

    public StorageBuilder addUser(User user) {
        if (user == null) throw new NullPointerException();
        users.add(user);
        return this;
    }

    @Override
    public Storage create() {
        return new Storage(this);
    }
}
