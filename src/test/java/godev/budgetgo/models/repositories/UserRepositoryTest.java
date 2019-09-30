package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.implementations.*;
import godev.budgetgo.models.dbfactory.RepositoriesFactory;
import godev.budgetgo.models.dbfactory.implementations.MySqlDbFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static UsersRepository usersRep;
    private static StoragesRepository storagesRep;

    @BeforeAll
    static void init() {
        RepositoriesFactory repositoriesFactory = new MySqlDbFactory();
        usersRep = repositoriesFactory.getUsersRepository();
        storagesRep = repositoriesFactory.getStoragesRepository();
        clearTables();
    }

    @AfterEach
    void clearUsersTable() {
        usersRep.removeAll(new UsersSpecification());
    }

    @Test
    void userAddTest() {
        User user = usersRep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );

        assertEquals(user, usersRep.get(user.getId()));
    }

    @Test
    void userUpdateTest() {
        User user = usersRep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );

        User updatedUser = new UserBuilder()
                .setId(user.getId())
                .setEmail("maria.golomako@gmail.com")
                .setName("Maria")
                .setSurname("Golomako")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create();

        usersRep.update(updatedUser);

        assertEquals(updatedUser, usersRep.get(user.getId()));
    }

    @Test
    void userRemoveTest() {
        User user = usersRep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        usersRep.remove(user);

        assertNull(usersRep.get(user.getId()));
    }

    @Test
    void usersRemoveAllByIdTest() {
        UserBuilder builder = new UserBuilder()
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456");

        User[] users = {
                usersRep.add(builder.setEmail("example1@example.com").create()),
                usersRep.add(builder.setEmail("example2@example.com").create()),
                usersRep.add(builder.setEmail("example3@example.com").create()),
                usersRep.add(builder.setEmail("example4@example.com").create()),
        };

        UsersSpecification spec = new UsersSpecification()
                .whereId(users[2].getId());

        usersRep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(usersRep.get(u.getId()));
            } else {
                assertNotNull(usersRep.get(u.getId()));
            }
        }
    }

    @Test
    void usersRemoveAllByEmailTest() {
        UserBuilder builder = new UserBuilder()
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456");
        String email = "oleg.grigorijan@gmail.com";

        User[] users = {
                usersRep.add(builder.setEmail("example1@example.com").create()),
                usersRep.add(builder.setEmail(email).create()),
                usersRep.add(builder.setEmail("example2@example.com").create()),
                usersRep.add(builder.setEmail("example3@example.com").create()),
        };

        UsersSpecification spec = new UsersSpecification()
                .whereEmail(email);

        usersRep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(usersRep.get(u.getId()));
            } else {
                assertNotNull(usersRep.get(u.getId()));
            }
        }
    }

    @Test
    void usersRemoveAllByStorageAccessTest() {
        UserBuilder builder = new UserBuilder()
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456");

        User[] users = {
                usersRep.add(builder.setEmail("example1@example.com").create()),
                usersRep.add(builder.setEmail("example2@example.com").create()),
                usersRep.add(builder.setEmail("example3@example.com").create()),
                usersRep.add(builder.setEmail("example4@example.com").create()),
        };

        Storage storage = storagesRep.add(
                new StorageBuilder()
                        .setName("Card")
                        .addUser(users[1])
                        .addUser(users[2])
                        .setCreator(users[1])
                        .create());

        UsersSpecification spec = new UsersSpecification()
                .withStorageAccess(storage);

        usersRep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(usersRep.get(u.getId()));
            } else {
                assertNotNull(usersRep.get(u.getId()));
            }
        }
    }

    private static void clearTables() {
        storagesRep.removeAll(new StoragesSpecification());
        usersRep.removeAll(new UsersSpecification());
    }
}
