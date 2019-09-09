package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.abstractions.DataBuilder;
import godev.budgetgo.models.data.abstractions.StorageData;

public class StorageBuilder implements DataBuilder<StorageData> {
    private int id;
    private String name = "";

    int getId() {
        return id;
    }

    @Override
    public StorageBuilder setId(int id) {
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

    @Override
    public StorageData create() {
        return new Storage(this);
    }
}
