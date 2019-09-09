package godev.budgetgo.models.data;

import godev.budgetgo.models.data.abstractions.OperationData;
import godev.budgetgo.models.data.abstractions.StorageData;
import godev.budgetgo.models.data.implementations.OperationBuilder;
import godev.budgetgo.models.data.implementations.StorageBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationBuilderTest {

    @Test
    void operationSimpleCreation() {
        int id = 1;
        StorageData storage = new StorageBuilder().setId(1).setName("My cash").create();
        int moneyDelta = -2000;
        LocalDate date = LocalDate.of(2019, 9, 9);
        String description = "My description";
        LocalDate creationDate = LocalDate.of(2019, 1, 9);

        OperationData operation = new OperationBuilder()
                .setId(id)
                .setStorage(storage)
                .setMoneyDelta(moneyDelta)
                .setDate(date)
                .setDescription(description)
                .setCreationDate(creationDate)
                .create();

        assertEquals(id, operation.getId());
        assertEquals(storage, operation.getStorage());
        assertEquals(moneyDelta, operation.getMoneyDelta());
        assertEquals(date, operation.getDate());
        assertEquals(description, operation.getDescription());
        assertEquals(creationDate, operation.getCreationDate());
    }

    @Test
    void operationDefaultCreation() {
        OperationData operation = new OperationBuilder().create();

        assertEquals(0, operation.getId());
        assertEquals(new StorageBuilder().create(), operation.getStorage());
        assertEquals(0, operation.getMoneyDelta());
        assertEquals(LocalDate.now(), operation.getDate());
        assertEquals("", operation.getDescription());
        assertEquals(LocalDate.now(), operation.getCreationDate());
    }

    @Test
    void operationCreationWithNulls() {
        OperationData operation = new OperationBuilder()
                .setId(0)
                .setStorage(null)
                .setMoneyDelta(0)
                .setDate(null)
                .setDescription(null)
                .setCreationDate(null)
                .create();

        assertEquals(0, operation.getId());
        assertEquals(new StorageBuilder().create(), operation.getStorage());
        assertEquals(0, operation.getMoneyDelta());
        assertEquals(LocalDate.now(), operation.getDate());
        assertEquals("", operation.getDescription());
        assertEquals(LocalDate.now(), operation.getCreationDate());
    }
}
