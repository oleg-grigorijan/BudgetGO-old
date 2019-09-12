package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoragesSpecificationTest {
    @Test
    void fullSpecificationTest() {
        long storageId = 1;
        long userId = 1;
        DataBuilder<User> userBuilder = new UserBuilder().setId(userId);

        Specification<Storage> specification = new StoragesSpecification()
                .whereId(storageId)
                .whereUser(userBuilder.create());

        Storage correctStorage = new StorageBuilder()
                .setId(storageId)
                .addUser(userBuilder.create())
                .create();

        Storage[] incorrectStorages = {
                new StorageBuilder()
                        .setId(storageId + 1)
                        .addUser(userBuilder.create())
                        .create(),
                new StorageBuilder()
                        .setId(storageId)
                        .addUser(userBuilder.setId(userId + 1).create())
                        .create(),
                new StorageBuilder()
                        .setId(storageId)
                        .create(),
                new StorageBuilder().create()
        };

        assertTrue(specification.specified(correctStorage));
        for (Storage s : incorrectStorages) assertFalse(specification.specified(s));
    }

    @Test
    void emptySpecificationTest() {
        Specification<Storage> specification = new StoragesSpecification();

        long storageId = 1;
        long userId = 1;
        DataBuilder<User> userBuilder = new UserBuilder().setId(userId);
        Storage[] storages = {
                new StorageBuilder()
                        .setId(storageId)
                        .addUser(userBuilder.create())
                        .create(),
                new StorageBuilder()
                        .setId(storageId + 1)
                        .addUser(userBuilder.create())
                        .create(),
                new StorageBuilder()
                        .setId(storageId)
                        .addUser(userBuilder.setId(userId + 1).create())
                        .create(),
                new StorageBuilder()
                        .setId(storageId)
                        .create(),
                new StorageBuilder().create()
        };

        for (Storage s : storages) assertTrue(specification.specified(s));
    }
}
