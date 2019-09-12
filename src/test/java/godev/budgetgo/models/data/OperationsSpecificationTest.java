package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationsSpecificationTest {
    @Test
    void fullSpecificationTest() {
        long id = 1;
        Storage storage = new StorageBuilder().setId(1).create();
        LocalDate dateFrom = LocalDate.of(2019, 9, 10);
        LocalDate dateTo = LocalDate.of(2019, 9, 12);

        Specification<Operation> specification = new OperationsSpecification()
                .whereId(id)
                .whereStorage(storage)
                .whereDateFrom(dateFrom)
                .whereDateTo(dateTo);

        OperationBuilder operationBuilder = new OperationBuilder().setId(id).setStorage(storage);
        Operation[] correctOperations = {
                operationBuilder
                        .setDate(dateFrom)
                        .create(),
                operationBuilder
                        .setDate(dateTo)
                        .create(),
                operationBuilder
                        .setDate(dateFrom.plusDays(1))
                        .create()
        };
        Operation[] incorrectOperations = {
                operationBuilder
                        .setId(id + 1)
                        .create(),
                operationBuilder
                        .setId(id)
                        .setStorage(new StorageBuilder().create())
                        .create(),
                operationBuilder
                        .setStorage(storage)
                        .setDate(dateFrom.minusDays(1))
                        .create(),
                operationBuilder
                        .setDate(dateTo.plusDays(1))
                        .create(),
                new OperationBuilder().create()
        };

        for (Operation o : correctOperations) assertTrue(specification.specified(o));
        for (Operation o : incorrectOperations) assertFalse(specification.specified(o));
    }

    @Test
    void emptySpecificationTest() {
        Specification<Operation> specification = new OperationsSpecification();

        Operation[] operations = {
                new OperationBuilder()
                        .setId(1)
                        .setStorage(new StorageBuilder().create())
                        .setDate(LocalDate.of(2019, 9, 11))
                        .create(),
                new OperationBuilder()
                        .create()
        };

        for (Operation o : operations) assertTrue(specification.specified(o));
    }

}
