package godev.budgetgo.models.repositories;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoragesRepositoryTest {

    private static StoragesRepository repository = Config.getStoragesRepository();
    private static User userOleg;
    private static User userMaria;

    @BeforeAll
    static void init() {
        Config.runTestMode();
        clearTables();
        initUsers();
    }

    @AfterEach
    void clearStoragesTable() {
        Config.getStoragesRepository().removeAll(new StoragesSpecification());
    }

    @Test
    void storageAddTest() {
        Storage storage = repository.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );

        assertEquals(storage, repository.get(storage.getId()));
    }

    @Test
    void storageRemoveTest() {
        Storage storage = repository.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );
        repository.remove(storage);

        assertNull(repository.get(storage.getId()));
    }

    @Test
    void storageRemoveAll() {
        Storage storageA = repository.add(
                new StorageBuilder()
                        .setName("A")
                        .addUser(userOleg)
                        .addUser(userMaria)
                        .create()
        );
        Storage storageB = repository.add(
                new StorageBuilder()
                        .setName("B")
                        .addUser(userOleg)
                        .create()
        );
        Storage storageC = repository.add(
                new StorageBuilder()
                        .setName("C")
                        .addUser(userMaria)
                        .create()
        );
        repository.removeAll(new StoragesSpecification().whereUser(userOleg));

        assertNull(repository.get(storageA.getId()));
        assertNull(repository.get(storageB.getId()));
        assertNotNull(repository.get(storageC.getId()));

        repository.removeAll(new StoragesSpecification().whereId(storageC.getId()));

        assertNull(repository.get(storageC.getId()));
    }

    @Test
    void storageUpdate() {
        Storage storage = repository.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );
        Storage updatedStorage = new StorageBuilder()
                .from(storage)
                .addUser(userMaria)
                .create();
        repository.update(updatedStorage);

        assertNotEquals(storage, repository.get(storage.getId()));
        assertEquals(updatedStorage, repository.get(storage.getId()));

        repository.update(storage);

        assertEquals(storage, repository.get(storage.getId()));
        assertNotEquals(updatedStorage, repository.get(storage.getId()));
    }

    static void clearTables() {
        Config.getUsersRepository().removeAll(new UsersSpecification());
        Config.getStoragesRepository().removeAll(new StoragesSpecification());
    }

    static void initUsers() {
        userOleg = Config.getUsersRepository().add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        userMaria = Config.getUsersRepository().add(new UserBuilder()
                .setEmail("maria.golomako@gmail.com")
                .setName("Maria")
                .setSurname("Golomako")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );
    }
}
