package godev.budgetgo.models.data.abstractions;

import java.time.LocalDate;

public interface OperationData extends Data {
    StorageData getStorage();

    int getMoneyDelta();

    LocalDate getDate();

    String getDescription();

    LocalDate getCreationDate();
}
