package godev.budgetgo.models.repositories.abstractions;

import godev.budgetgo.models.data.abstractions.StorageData;

import java.time.LocalDate;

public interface OperationSpecification extends DataSpecification {
    OperationSpecification whereStorage(StorageData storage);

    OperationSpecification whereDateFrom(LocalDate date);

    OperationSpecification whereDateTo(LocalDate date);
}
