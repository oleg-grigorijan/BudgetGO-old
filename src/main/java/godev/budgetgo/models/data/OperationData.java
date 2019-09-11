package godev.budgetgo.models.data;

import java.time.LocalDate;

public interface OperationData extends Identifiable {
    StorageData getStorage();

    int getMoneyDelta();

    LocalDate getDate();

    String getDescription();

    LocalDate getCreationDate();
}
