package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.StorageBuilder;
import godev.budgetgo.models.data.implementations.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageBuilderTest {

    @Test
    void storageSimpleCreation() {
        int id = 1;
        String name = "My cash";
        UserBuilder userBuilder = new UserBuilder();
        UserData[] users = {
                userBuilder.setId(1).create(),
                userBuilder.setId(2).create(),
                userBuilder.setId(3).create(),
        };

        StorageData storage = new StorageBuilder()
                .setId(id)
                .setName(name)
                .addUser(users[0])
                .addUser(users[1])
                .addUser(users[2])
                .create();

        assertEquals(id, storage.getId());
        assertEquals(name, storage.getName());
        assertEquals(Arrays.asList(users), storage.getUsers());
    }

    @Test
    void storageDefaultCreation() {
        StorageData storage = new StorageBuilder().create();

        assertEquals(-1, storage.getId());
        assertEquals("", storage.getName());
        assertTrue(storage.getUsers().isEmpty());
    }

    @Test
    void storageCreationWithNulls() {
        StorageData storage = new StorageBuilder()
                .setId(0)
                .setName(null)
                .addUser(null)
                .create();

        assertEquals(0, storage.getId());
        assertEquals("", storage.getName());
        assertTrue(storage.getUsers().isEmpty());
    }

    @Test
    void storageCreationFromAnother() {
        int id = 1;
        String name = "My cash";
        UserBuilder userBuilder = new UserBuilder();
        UserData[] users = {
                userBuilder.setId(1).create(),
                userBuilder.setId(2).create(),
                userBuilder.setId(3).create(),
        };

        StorageData existingStorage = new StorageBuilder()
                .setId(id)
                .setName(name)
                .addUser(users[0])
                .addUser(users[1])
                .addUser(users[2])
                .create();

        StorageData storage = new StorageBuilder().from(existingStorage).create();

        assertEquals(existingStorage, storage);
    }
}
