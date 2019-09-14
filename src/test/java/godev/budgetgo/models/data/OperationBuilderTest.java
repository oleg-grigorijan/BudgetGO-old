package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationBuilderTest {

    @Test
    void operationSimpleCreationTest() {
        int id = 1;
        Storage storage = new StorageBuilder()
                .setId(1)
                .setName("My cash")
                .create();
        int moneyDelta = -2000;
        LocalDate date = LocalDate.of(2019, 9, 9);
        String description = "My description";
        LocalDate creationDate = LocalDate.of(2019, 1, 9);
        User creator = new UserBuilder()
                .setId(1)
                .create();

        Operation operation = new OperationBuilder()
                .setId(id)
                .setStorage(storage)
                .setMoneyDelta(moneyDelta)
                .setDate(date)
                .setDescription(description)
                .setCreationDate(creationDate)
                .setCreator(creator)
                .create();

        assertEquals(id, operation.getId());
        assertEquals(storage, operation.getStorage());
        assertEquals(moneyDelta, operation.getMoneyDelta());
        assertEquals(date, operation.getDate());
        assertEquals(description, operation.getDescription());
        assertEquals(creationDate, operation.getCreationDate());
        assertEquals(creator, operation.getCreator());
    }

    @Test
    void operationDefaultCreationTest() {
        Operation operation = new OperationBuilder().create();

        assertEquals(-1, operation.getId());
        assertEquals(new StorageBuilder().create(), operation.getStorage());
        assertEquals(0, operation.getMoneyDelta());
        assertEquals(LocalDate.now(), operation.getDate());
        assertEquals("", operation.getDescription());
        assertEquals(LocalDate.now(), operation.getCreationDate());
        assertEquals(new UserBuilder().create(), operation.getCreator());
    }

    @Test
    void operationCreationWithNullsTest() {
        OperationBuilder builder = new OperationBuilder();

        assertThrows(NullPointerException.class, () -> builder.setStorage(null));
        assertThrows(NullPointerException.class, () -> builder.setDate(null));
        assertThrows(NullPointerException.class, () -> builder.setDescription(null));
        assertThrows(NullPointerException.class, () -> builder.setCreationDate(null));
        assertThrows(NullPointerException.class, () -> builder.setCreator(null));
    }

    @Test
    void operationCreationFromAnotherTest() {
        int id = 1;
        Storage storage = new StorageBuilder()
                .setId(1)
                .setName("My cash")
                .create();
        int moneyDelta = -2000;
        LocalDate date = LocalDate.of(2019, 9, 9);
        String description = "My description";
        LocalDate creationDate = LocalDate.of(2019, 1, 9);
        User creator = new UserBuilder()
                .setId(1)
                .create();

        Operation existingOperation = new OperationBuilder()
                .setId(id)
                .setStorage(storage)
                .setMoneyDelta(moneyDelta)
                .setDate(date)
                .setDescription(description)
                .setCreationDate(creationDate)
                .setCreator(creator)
                .create();

        Operation operation = new OperationBuilder()
                .from(existingOperation)
                .create();

        assertEquals(existingOperation, operation);
    }
}
