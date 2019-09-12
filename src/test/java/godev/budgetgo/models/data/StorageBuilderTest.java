package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.Storage;
import godev.budgetgo.models.data.implementations.StorageBuilder;
import godev.budgetgo.models.data.implementations.User;
import godev.budgetgo.models.data.implementations.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class StorageBuilderTest {

    @Test
    void storageSimpleCreation() {
        int id = 1;
        String name = "My cash";
        UserBuilder userBuilder = new UserBuilder();
        User[] users = {
                userBuilder.setId(1).create(),
                userBuilder.setId(2).create(),
                userBuilder.setId(3).create(),
        };

        Storage storage = new StorageBuilder()
                .setId(id)
                .setName(name)
                .addUser(users[0])
                .addUser(users[1])
                .addUser(users[2])
                .create();

        assertEquals(id, storage.getId());
        assertEquals(name, storage.getName());
        assertEquals(new HashSet<>(Arrays.asList(users)), storage.getUsers());
    }

    @Test
    void storageDefaultCreation() {
        Storage storage = new StorageBuilder().create();

        assertEquals(-1, storage.getId());
        assertEquals("", storage.getName());
        assertTrue(storage.getUsers().isEmpty());
    }

    @Test
    void storageCreationWithNulls() {
        StorageBuilder builder = new StorageBuilder();

        assertThrows(NullPointerException.class, () -> builder.setName(null));
        assertThrows(NullPointerException.class, () -> builder.addUser(null));
    }

    @Test
    void storageCreationFromAnother() {
        int id = 1;
        String name = "My cash";
        UserBuilder userBuilder = new UserBuilder();
        User[] users = {
                userBuilder.setId(1).create(),
                userBuilder.setId(2).create(),
                userBuilder.setId(3).create(),
        };

        Storage existingStorage = new StorageBuilder()
                .setId(id)
                .setName(name)
                .addUser(users[0])
                .addUser(users[1])
                .addUser(users[2])
                .create();

        Storage storage = new StorageBuilder().from(existingStorage).create();

        assertEquals(existingStorage, storage);
    }
}
