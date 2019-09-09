package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.abstractions.DataBuilder;
import godev.budgetgo.models.data.abstractions.OperationData;
import godev.budgetgo.models.data.abstractions.StorageData;

import java.time.LocalDate;


public class OperationBuilder implements DataBuilder<OperationData> {
    private int id;
    private StorageData storage;
    private int moneyDelta;
    private LocalDate date = LocalDate.now();
    private String description = "";
    private LocalDate creationDate = LocalDate.now();

    public OperationBuilder() {
        storage = new StorageBuilder().create();
    }

    int getId() {
        return id;
    }

    @Override
    public OperationBuilder setId(int id) {
        this.id = id;
        return this;
    }

    StorageData getStorage() {
        return storage;
    }

    public OperationBuilder setStorage(StorageData storage) {
        if (storage != null) this.storage = storage;
        return this;
    }

    int getMoneyDelta() {
        return moneyDelta;
    }

    public OperationBuilder setMoneyDelta(int moneyDelta) {
        this.moneyDelta = moneyDelta;
        return this;
    }

    LocalDate getDate() {
        return date;
    }

    public OperationBuilder setDate(LocalDate date) {
        if (date != null) this.date = date;
        return this;
    }

    String getDescription() {
        return description;
    }

    public OperationBuilder setDescription(String description) {
        if (description != null) this.description = description;
        return this;
    }

    LocalDate getCreationDate() {
        return creationDate;
    }

    public OperationBuilder setCreationDate(LocalDate creationDate) {
        if (creationDate != null) this.creationDate = creationDate;
        return this;
    }

    @Override
    public OperationData create() {
        return new Operation(this);
    }
}
