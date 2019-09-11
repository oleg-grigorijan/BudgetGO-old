package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.StorageBuilder;
import godev.budgetgo.models.data.implementations.StoragesSpecification;
import godev.budgetgo.models.data.implementations.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoragesSpecificationTest {
    @Test
    void fullSpecificationTest() {
        long storageId = 1;
        long userId = 1;
        DataBuilder<UserData> userBuilder = new UserBuilder().setId(userId);

        Specification<StorageData> specification = new StoragesSpecification()
                .whereId(storageId)
                .whereUser(userBuilder.create());

        StorageData correctStorage = new StorageBuilder()
                .setId(storageId)
                .addUser(userBuilder.create())
                .create();

        StorageData[] incorrectStorages = {
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
        for (StorageData s : incorrectStorages) assertFalse(specification.specified(s));
    }

    @Test
    void emptySpecificationTest() {
        Specification<StorageData> specification = new StoragesSpecification();

        long storageId = 1;
        long userId = 1;
        DataBuilder<UserData> userBuilder = new UserBuilder().setId(userId);
        StorageData[] storages = {
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

        for (StorageData s : storages) assertTrue(specification.specified(s));
    }
}
