package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.implementations.*;
import godev.budgetgo.models.dbfactory.RepositoriesFactory;
import godev.budgetgo.models.dbfactory.implementations.MySqlDbFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoragesRepositoryTest {
    private static StoragesRepository storagesRep;
    private static UsersRepository usersRep;
    private static User userOleg;
    private static User userMaria;

    @BeforeAll
    static void init() {
        RepositoriesFactory repositoriesFactory = new MySqlDbFactory();
        storagesRep = repositoriesFactory.getStoragesRepository();
        usersRep = repositoriesFactory.getUsersRepository();
        clearTables();
        initUsers();
    }

    @AfterEach
    void clearStoragesTable() {
        storagesRep.removeAll(new StoragesSpecification());
    }

    @Test
    void storageAddTest() {
        Storage storage = storagesRep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .setCreator(userOleg)
                        .create()
        );

        assertEquals(storage, storagesRep.get(storage.getId()));
    }

    @Test
    void storageUpdateTest() {
        Storage storage = storagesRep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .setCreator(userOleg)
                        .create()
        );
        Storage updatedStorage = new StorageBuilder()
                .from(storage)
                .setName("Visa")
                .addUser(userMaria)
                .create();
        storagesRep.update(updatedStorage);

        assertNotEquals(storage, storagesRep.get(storage.getId()));
        assertEquals(updatedStorage, storagesRep.get(storage.getId()));
    }

    @Test
    void storageRemoveTest() {
        Storage storage = storagesRep.add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .setCreator(userOleg)
                        .create()
        );
        storagesRep.remove(storage);

        assertNull(storagesRep.get(storage.getId()));
    }

    @Test
    void storagesRemoveAllByIdTest() {
        StorageBuilder builder = new StorageBuilder()
                .setName("MasterCard")
                .addUser(userOleg)
                .setCreator(userOleg);

        Storage[] storages = {
                storagesRep.add(builder.create()),
                storagesRep.add(builder.create()),
                storagesRep.add(builder.create()),
                storagesRep.add(builder.create()),
        };

        StoragesSpecification spec = new StoragesSpecification()
                .whereId(storages[3].getId());

        storagesRep.removeAll(spec);

        for (Storage s : storages) {
            if (spec.specified(s)) {
                assertNull(storagesRep.get(s.getId()));
            } else {
                assertNotNull(storagesRep.get(s.getId()));
            }
        }
    }

    @Test
    void storagesRemoveAllByUserTest() {
        StorageBuilder builder = new StorageBuilder()
                .setName("MasterCard")
                .setCreator(userOleg);

        Storage[] storages = {
                storagesRep.add(builder.addUser(userOleg).addUser(userMaria).create()),
                storagesRep.add(builder.addUser(userOleg).create()),
                storagesRep.add(builder.addUser(userMaria).create()),
                storagesRep.add(builder.create()),
        };

        StoragesSpecification spec = new StoragesSpecification()
                .whereUser(userOleg);

        storagesRep.removeAll(spec);

        for (Storage s : storages) {
            if (spec.specified(s)) {
                assertNull(storagesRep.get(s.getId()));
            } else {
                assertNotNull(storagesRep.get(s.getId()));
            }
        }
    }

    static void clearTables() {
        storagesRep.removeAll(new StoragesSpecification());
        usersRep.removeAll(new UsersSpecification());
    }

    static void initUsers() {
        userOleg = usersRep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        userMaria = usersRep.add(
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
