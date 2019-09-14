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
        Storage storage = new StorageBuilder()
                .setId(1)
                .create();
        LocalDate dateFrom = LocalDate.of(2019, 9, 10);
        LocalDate dateTo = LocalDate.of(2019, 9, 12);

        Specification<Operation> spec = new OperationsSpecification()
                .whereId(id)
                .whereStorage(storage)
                .whereDateFrom(dateFrom)
                .whereDateTo(dateTo);

        OperationBuilder builder = new OperationBuilder().setId(id).setStorage(storage);
        Operation[] correctOperations = {
                builder.setDate(dateFrom).create(),
                builder.setDate(dateTo).create(),
                builder.setDate(dateFrom.plusDays(1)).create()
        };

        Operation[] incorrectOperations = {
                builder.setId(id + 1).create(),
                builder.setId(id).setStorage(new StorageBuilder().create()).create(),
                builder.setStorage(storage).setDate(dateFrom.minusDays(1)).create(),
                builder.setDate(dateTo.plusDays(1)).create(),
                new OperationBuilder().create()
        };

        for (Operation o : correctOperations) assertTrue(spec.specified(o));
        for (Operation o : incorrectOperations) assertFalse(spec.specified(o));
    }

    @Test
    void emptySpecificationTest() {
        Specification<Operation> spec = new OperationsSpecification();

        Operation[] operations = {
                new OperationBuilder()
                        .setId(1)
                        .setStorage(new StorageBuilder().create())
                        .setDate(LocalDate.of(2019, 9, 11))
                        .create(),
                new OperationBuilder()
                        .create()
        };

        for (Operation o : operations) assertTrue(spec.specified(o));
    }

}
