package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;
import godev.budgetgo.models.data.StorageData;
import godev.budgetgo.models.data.UserData;

import java.util.ArrayList;
import java.util.List;

public class StorageBuilder implements DataBuilder<StorageData> {
    private long id;
    private String name = "";
    private List<UserData> users = new ArrayList<>();

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
        if (name != null) this.name = name;
        return this;
    }

    List<UserData> getUsers() {
        return users;
    }

    public StorageBuilder addUser(UserData user) {
        if (user != null) users.add(user);
        return this;
    }

    @Override
    public StorageData create() {
        return new Storage(this);
    }
}
