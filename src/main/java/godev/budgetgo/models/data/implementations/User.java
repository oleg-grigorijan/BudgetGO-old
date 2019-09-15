package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Identifiable;

import java.util.Objects;

public final class User implements Identifiable {
    private final long id;
    private final String email;
    private final String name;
    private final String surname;
    private final String passwordHash;
    private final String passwordSalt;

    User(UserBuilder builder) {
        id = builder.getId();
        email = builder.getEmail();
        name = builder.getName();
        surname = builder.getSurname();
        passwordHash = builder.getPasswordHash();
        passwordSalt = builder.getPasswordSalt();
    }

    @Override
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                email.equals(user.email) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                passwordHash.equals(user.passwordHash) &&
                passwordSalt.equals(user.passwordSalt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, surname, passwordHash, passwordSalt);
    }
}
