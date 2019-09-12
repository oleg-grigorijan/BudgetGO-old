package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;

import java.time.LocalDate;


public class OperationBuilder implements DataBuilder<Operation> {
    private long id = -1;
    private Storage storage = new StorageBuilder().create();
    private int moneyDelta = 0;
    private LocalDate date = LocalDate.now();
    private String description = "";
    private LocalDate creationDate = LocalDate.now();

    @Override
    public OperationBuilder from(Operation entity) {
        id = entity.getId();
        storage = entity.getStorage();
        moneyDelta = entity.getMoneyDelta();
        date = entity.getDate();
        description = entity.getDescription();
        creationDate = entity.getCreationDate();
        return this;
    }

    long getId() {
        return id;
    }

    @Override
    public OperationBuilder setId(long id) {
        this.id = id;
        return this;
    }

    Storage getStorage() {
        return storage;
    }

    public OperationBuilder setStorage(Storage storage) {
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
    public Operation create() {
        return new Operation(this);
    }
}
