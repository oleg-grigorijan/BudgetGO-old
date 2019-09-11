package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.StorageBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageBuilderTest {

    @Test
    void storageSimpleCreation() {
        int id = 1;
        String name = "My cash";

        StorageData storage = new StorageBuilder()
                .setId(id)
                .setName(name)
                .create();

        assertEquals(id, storage.getId());
        assertEquals(name, storage.getName());
    }

    @Test
    void storageDefaultCreation() {
        StorageData storage = new StorageBuilder().create();

        assertEquals(0, storage.getId());
        assertEquals("", storage.getName());
    }

    @Test
    void storageCreationWithNulls() {
        StorageData storage = new StorageBuilder()
                .setId(0)
                .setName(null)
                .create();

        assertEquals(0, storage.getId());
        assertEquals("", storage.getName());
    }
}
