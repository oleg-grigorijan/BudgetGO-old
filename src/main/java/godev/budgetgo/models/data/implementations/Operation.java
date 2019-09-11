package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.abstractions.OperationData;
import godev.budgetgo.models.data.abstractions.StorageData;

import java.time.LocalDate;
import java.util.Objects;

public final class Operation implements OperationData {
    private final long id;
    private final StorageData storage;
    private final int moneyDelta;
    private final LocalDate date;
    private final String description;
    private final LocalDate creationDate;

    Operation(OperationBuilder builder) {
        id = builder.getId();
        storage = builder.getStorage();
        moneyDelta = builder.getMoneyDelta();
        date = builder.getDate();
        description = builder.getDescription();
        creationDate = builder.getCreationDate();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public StorageData getStorage() {
        return storage;
    }

    @Override
    public int getMoneyDelta() {
        return moneyDelta;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                moneyDelta == operation.moneyDelta &&
                storage.equals(operation.storage) &&
                date.equals(operation.date) &&
                description.equals(operation.description) &&
                creationDate.equals(operation.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storage, moneyDelta, date, description, creationDate);
    }
}
