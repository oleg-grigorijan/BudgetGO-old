package godev.budgetgo.models.data;

import godev.budgetgo.models.data.implementations.User;
import godev.budgetgo.models.data.implementations.UserBuilder;
import godev.budgetgo.models.data.implementations.UsersSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersSpecificationTest {
    @Test
    void fullSpecificationTest() {
        long id = 1;
        String email = "oleg.grigorijan@gmail.com";

        Specification<User> specification = new UsersSpecification()
                .whereId(id)
                .whereEmail(email);

        User correctUser = new UserBuilder().setId(id).setEmail(email).create();
        User[] incorrectUsers = {
                new UserBuilder().setId(id + 1).setEmail(email).create(),
                new UserBuilder().setId(id).setEmail("incorrect" + email).create(),
                new UserBuilder().create()
        };

        assertTrue(specification.specified(correctUser));
        for (User u : incorrectUsers) assertFalse(specification.specified(u));
    }

    @Test
    void emptySpecificationTest() {
        Specification<User> specification = new UsersSpecification();

        User[] users = {
                new UserBuilder().setId(1).setEmail("example@example.com").create(),
                new UserBuilder().create()
        };

        for (User u : users) assertTrue(specification.specified(u));
    }
}
