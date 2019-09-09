package godev.budgetgo.models.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import godev.budgetgo.models.data.abstractions.UserData;
import godev.budgetgo.models.data.implementations.UserBuilder;
import org.junit.jupiter.api.Test;

class UserBuilderTest {

    @Test
    void userSimpleCreation() {
        int id = 1;
        String email = "oleg.grigorijan@gmail.com";
        String name = "Oleg";
        String surname = "Grigorijan";

        UserData user = new UserBuilder()
                .setId(id)
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .create();

        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
    }

    @Test
    void userDefaultCreation() {
        UserData user = new UserBuilder().create();

        assertEquals(0, user.getId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getName());
        assertEquals("", user.getSurname());
    }

    @Test
    void userCreationWithNulls() {
        UserData user = new UserBuilder()
                .setId(0)
                .setEmail(null)
                .setName(null)
                .setSurname(null)
                .create();

        assertEquals(0, user.getId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getName());
        assertEquals("", user.getSurname());
    }
}
