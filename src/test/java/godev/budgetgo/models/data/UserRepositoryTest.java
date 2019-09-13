package godev.budgetgo.models.data;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.User;
import godev.budgetgo.models.data.implementations.UserBuilder;
import godev.budgetgo.models.data.implementations.UsersSpecification;
import godev.budgetgo.models.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @BeforeAll
    static void setTestMode() {
        Config.runTestMode();
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }

    @AfterEach
    void clearUsersTable() {
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }

    @Test
    void userAddTest() {
        UsersRepository usersRepository = Config.getUsersRepository();
        User user = usersRepository.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );

        assertEquals(user, usersRepository.get(user.getId()));
    }

    @Test
    void userRemoveTest() {
        UsersRepository usersRepository = Config.getUsersRepository();
        User user = usersRepository.add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        usersRepository.remove(user);

        assertNull(usersRepository.get(user.getId()));
    }

    @Test
    void usersRemoveAllTest() {
        UsersRepository usersRepository = Config.getUsersRepository();
        User oleg = usersRepository.add(new UserBuilder()
                .setEmail("oleg.grigorijan@gmail.com")
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );
        User maria = usersRepository.add(new UserBuilder()
                .setEmail("maria.golomako@gmail.com")
                .setName("Maria")
                .setSurname("Golomako")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );

        usersRepository.removeAll(new UsersSpecification()
                .whereId(oleg.getId())
                .whereEmail(maria.getEmail())
        );

        assertNotNull(usersRepository.get(oleg.getId()));
        assertNotNull(usersRepository.get(maria.getId()));

        usersRepository.removeAll(new UsersSpecification()
                .whereEmail(oleg.getEmail())
        );

        assertNull(usersRepository.get(oleg.getId()));
        assertNotNull(usersRepository.get(maria.getId()));

        usersRepository.removeAll(new UsersSpecification().whereId(maria.getId()));

        assertNull(usersRepository.get(maria.getId()));

        // TODO: usersSpecification.withStorageAccess() test
    }

    @Test
    void userUpdateTest() {
        UsersRepository usersRepository = Config.getUsersRepository();
        String email = "oleg.grigorijan@gmail.com";
        String name = "Oleg";
        String surname = "Grigorijan";
        String passwordHash = "abcdef";
        String passwordSalt = "123456";
        User user = usersRepository.add(
                new UserBuilder()
                        .setEmail(email)
                        .setName(name)
                        .setSurname(surname)
                        .setPasswordHash(passwordHash)
                        .setPasswordSalt(passwordSalt)
                        .create()
        );

        String prefix = "updated";
        User updatedUser = new UserBuilder()
                .setId(user.getId())
                .setEmail(prefix + email)
                .setName(prefix + name)
                .setSurname(prefix + surname)
                .setPasswordHash(prefix + passwordHash)
                .setPasswordSalt(prefix + passwordSalt)
                .create();

        usersRepository.update(updatedUser);

        assertEquals(updatedUser, usersRepository.get(user.getId()));
    }

}
