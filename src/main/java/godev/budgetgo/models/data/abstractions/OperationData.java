package godev.budgetgo.models.data.abstractions;

import java.util.Date;

public interface OperationData extends Data {
    StorageData getStorage();

    int getMoneyDelta();

    Date getDate();

    String getDescription();

    Date getCreationDate();
}
