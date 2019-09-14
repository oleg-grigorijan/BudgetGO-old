package godev.budgetgo.models.repositories;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoragesRepositoryTest {
    private static StoragesRepository rep = Config.getStoragesRepository();
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
        Storage storage = rep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );

        assertEquals(storage, rep.get(storage.getId()));
    }

    @Test
    void storageUpdateTest() {
        Storage storage = rep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );
        Storage updatedStorage = new StorageBuilder()
                .from(storage)
                .setName("Visa")
                .addUser(userMaria)
                .create();
        rep.update(updatedStorage);

        assertNotEquals(storage, rep.get(storage.getId()));
        assertEquals(updatedStorage, rep.get(storage.getId()));
    }

    @Test
    void storageRemoveTest() {
        Storage storage = rep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .create()
        );
        rep.remove(storage);

        assertNull(rep.get(storage.getId()));
    }

    @Test
    void storagesRemoveAllByIdTest() {
        StorageBuilder builder = new StorageBuilder()
                .setName("MasterCard")
                .addUser(userOleg);

        Storage[] storages = {
                rep.add(builder.create()),
                rep.add(builder.create()),
                rep.add(builder.create()),
                rep.add(builder.create()),
        };

        StoragesSpecification spec = new StoragesSpecification()
                .whereId(storages[3].getId());

        rep.removeAll(spec);

        for (Storage s : storages) {
            if (spec.specified(s)) {
                assertNull(rep.get(s.getId()));
            } else {
                assertNotNull(rep.get(s.getId()));
            }
        }
    }

    @Test
    void storagesRemoveAllByUserTest() {
        StorageBuilder builder = new StorageBuilder()
                .setName("MasterCard");

        Storage[] storages = {
                rep.add(builder.addUser(userOleg).addUser(userMaria).create()),
                rep.add(builder.addUser(userOleg).create()),
                rep.add(builder.addUser(userMaria).create()),
                rep.add(builder.create()),
        };

        StoragesSpecification spec = new StoragesSpecification()
                .whereUser(userOleg);

        rep.removeAll(spec);

        for (Storage s : storages) {
            if (spec.specified(s)) {
                assertNull(rep.get(s.getId()));
            } else {
                assertNotNull(rep.get(s.getId()));
            }
        }
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
        userMaria = Config.getUsersRepository().add(
                new UserBuilder()
                        .setEmail("maria.golomako@gmail.com")
                        .setName("Maria")
                        .setSurname("Golomako")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
    }
}
