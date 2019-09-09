package godev.budgetgo.models.repositories.abstractions;

import godev.budgetgo.models.data.abstractions.StorageData;

import java.util.Date;

public interface OperationSpecification extends DataSpecification {
    OperationSpecification whereStorage(StorageData storage);

    OperationSpecification whereDateFrom(Date date);

    OperationSpecification whereDateTo(Date date);
}
