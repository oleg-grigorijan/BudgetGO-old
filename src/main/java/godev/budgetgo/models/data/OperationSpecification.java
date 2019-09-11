package godev.budgetgo.models.data;

import java.time.LocalDate;

public interface OperationSpecification extends Specification {
    OperationSpecification whereStorage(StorageData storage);

    OperationSpecification whereDateFrom(LocalDate date);

    OperationSpecification whereDateTo(LocalDate date);
}
