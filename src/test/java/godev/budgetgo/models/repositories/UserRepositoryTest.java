package godev.budgetgo.models.repositories;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static UsersRepository rep = Config.getUsersRepository();

    @BeforeAll
    static void setTestMode() {
        Config.runTestMode();
        clearTables();
    }

    @AfterEach
    void clearUsersTable() {
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }

    @Test
    void userAddTest() {
        User user = rep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );

        assertEquals(user, rep.get(user.getId()));
    }

    @Test
    void userUpdateTest() {
        User user = rep.add(
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

        rep.update(updatedUser);

        assertEquals(updatedUser, rep.get(user.getId()));
    }

    @Test
    void userRemoveTest() {
        User user = rep.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        rep.remove(user);

        assertNull(rep.get(user.getId()));
    }

    @Test
    void usersRemoveAllByIdTest() {
        UserBuilder builder = new UserBuilder()
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456");

        User[] users = {
                rep.add(builder.setEmail("example1@example.com").create()),
                rep.add(builder.setEmail("example2@example.com").create()),
                rep.add(builder.setEmail("example3@example.com").create()),
                rep.add(builder.setEmail("example4@example.com").create()),
        };

        UsersSpecification spec = new UsersSpecification()
                .whereId(users[2].getId());

        rep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(rep.get(u.getId()));
            } else {
                assertNotNull(rep.get(u.getId()));
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
                rep.add(builder.setEmail("example1@example.com").create()),
                rep.add(builder.setEmail(email).create()),
                rep.add(builder.setEmail("example2@example.com").create()),
                rep.add(builder.setEmail("example3@example.com").create()),
        };

        UsersSpecification spec = new UsersSpecification()
                .whereEmail(email);

        rep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(rep.get(u.getId()));
            } else {
                assertNotNull(rep.get(u.getId()));
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
                rep.add(builder.setEmail("example1@example.com").create()),
                rep.add(builder.setEmail("example2@example.com").create()),
                rep.add(builder.setEmail("example3@example.com").create()),
                rep.add(builder.setEmail("example4@example.com").create()),
        };

        Storage storage = Config.getStoragesRepository().add(
                new StorageBuilder()
                        .setName("Card")
                        .addUser(users[1])
                        .addUser(users[2])
                        .create());

        UsersSpecification spec = new UsersSpecification()
                .withStorageAccess(storage);

        rep.removeAll(spec);

        for (User u : users) {
            if (spec.specified(u)) {
                assertNull(rep.get(u.getId()));
            } else {
                assertNotNull(rep.get(u.getId()));
            }
        }
    }

    private static void clearTables() {
        Config.getStoragesRepository().removeAll(new StoragesSpecification());
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }
}
