package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;

import java.util.HashSet;
import java.util.Set;

public class StorageBuilder implements DataBuilder<Storage> {
    private long id = -1;
    private String name = "";
    private Set<User> users = new HashSet<>();
    private User creator = new UserBuilder().create();

    @Override
    public StorageBuilder from(Storage entity) {
        id = entity.getId();
        name = entity.getName();
        users.addAll(entity.getUsers());
        creator = entity.getCreator();
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

    User getCreator() {
        return creator;
    }

    public StorageBuilder setCreator(User creator) {
        if (creator == null) throw new NullPointerException();
        this.creator = creator;
        return this;
    }

    @Override
    public Storage create() {
        return new Storage(this);
    }
}
