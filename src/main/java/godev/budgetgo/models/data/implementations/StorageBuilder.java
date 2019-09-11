package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;
import godev.budgetgo.models.data.StorageData;

public class StorageBuilder implements DataBuilder<StorageData> {
    private long id;
    private String name = "";

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

    @Override
    public StorageData create() {
        return new Storage(this);
    }
}
