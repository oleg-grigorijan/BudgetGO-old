package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Identifiable;

import java.time.LocalDate;
import java.util.Objects;

public final class Operation implements Identifiable {
    private final long id;
    private final Storage storage;
    private final int moneyDelta;
    private final LocalDate date;
    private final String description;
    private final LocalDate creationDate;
    private final User creator;

    Operation(OperationBuilder builder) {
        id = builder.getId();
        storage = builder.getStorage();
        moneyDelta = builder.getMoneyDelta();
        date = builder.getDate();
        description = builder.getDescription();
        creationDate = builder.getCreationDate();
        creator = builder.getCreator();
    }

    @Override
    public long getId() {
        return id;
    }

    public Storage getStorage() {
        return storage;
    }

    public int getMoneyDelta() {
        return moneyDelta;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public User getCreator() {
        return creator;
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
                creationDate.equals(operation.creationDate) &&
                creator.equals(operation.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storage, moneyDelta, date, description, creationDate, creator);
    }
}
