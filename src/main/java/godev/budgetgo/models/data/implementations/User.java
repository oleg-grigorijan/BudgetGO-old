package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.abstractions.UserData;

import java.util.Objects;

public final class User implements UserData {
    private final int id;
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
    public int getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
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
                surname.equals(user.surname);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, surname);
    }

}
