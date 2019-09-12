package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.User;
import godev.budgetgo.models.data.implementations.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBuilderTest {

    @Test
    void userSimpleCreation() {
        int id = 1;
        String email = "oleg.grigorijan@gmail.com";
        String name = "Oleg";
        String surname = "Grigorijan";
        String passwordHash = "PasswordHashExample";
        String passwordSalt = "PasswordSaltExample";

        User user = new UserBuilder()
                .setId(id)
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setPasswordHash(passwordHash)
                .setPasswordSalt(passwordSalt)
                .create();


        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(passwordSalt, user.getPasswordSalt());
    }

    @Test
    void userDefaultCreation() {
        User user = new UserBuilder().create();

        assertEquals(-1, user.getId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getName());
        assertEquals("", user.getSurname());
        assertEquals("", user.getPasswordHash());
        assertEquals("", user.getPasswordSalt());
    }

    @Test
    void userCreationWithNulls() {
        User user = new UserBuilder()
                .setId(0)
                .setEmail(null)
                .setName(null)
                .setSurname(null)
                .setPasswordHash(null)
                .setPasswordSalt(null)
                .create();

        assertEquals(0, user.getId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getName());
        assertEquals("", user.getSurname());
        assertEquals("", user.getPasswordHash());
        assertEquals("", user.getPasswordSalt());
    }

    @Test
    void userCreationFromAnother() {
        int id = 1;
        String email = "oleg.grigorijan@gmail.com";
        String name = "Oleg";
        String surname = "Grigorijan";
        String passwordHash = "PasswordHashExample";
        String passwordSalt = "PasswordSaltExample";

        User existingUser = new UserBuilder()
                .setId(id)
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setPasswordHash(passwordHash)
                .setPasswordSalt(passwordSalt)
                .create();

        User user = new UserBuilder().from(existingUser).create();

        assertEquals(existingUser, user);
    }
}
