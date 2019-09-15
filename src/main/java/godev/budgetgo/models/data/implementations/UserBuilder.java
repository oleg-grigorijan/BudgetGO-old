package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;

public class UserBuilder implements DataBuilder<User> {
    private long id = -1;
    private String email = "";
    private String name = "";
    private String surname = "";
    private String passwordHash = "";
    private String passwordSalt = "";
    private static User removedUser = new UserBuilder()
            .setId(0)
            .setName("deleted user")
            .create();

    @Override
    public UserBuilder from(User entity) {
        id = entity.getId();
        email = entity.getEmail();
        name = entity.getName();
        surname = entity.getSurname();
        passwordHash = entity.getPasswordHash();
        passwordSalt = entity.getPasswordSalt();
        return this;
    }

    @Override
    public User create() {
        return new User(this);
    }

    long getId() {
        return id;
    }

    @Override
    public UserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    String getEmail() {
        return email;
    }

    public UserBuilder setEmail(String email) {
        if (email == null) throw new NullPointerException();
        this.email = email;
        return this;
    }

    String getName() {
        return name;
    }

    public UserBuilder setName(String name) {
        if (name == null) throw new NullPointerException();
        this.name = name;
        return this;
    }

    String getSurname() {
        return surname;
    }

    public UserBuilder setSurname(String surname) {
        if (surname == null) throw new NullPointerException();
        this.surname = surname;
        return this;
    }

    String getPasswordHash() {
        return passwordHash;
    }

    public UserBuilder setPasswordHash(String passwordHash) {
        if (passwordHash == null) throw new NullPointerException();
        this.passwordHash = passwordHash;
        return this;
    }

    String getPasswordSalt() {
        return passwordSalt;
    }

    public UserBuilder setPasswordSalt(String passwordSalt) {
        if (passwordSalt == null) throw new NullPointerException();
        this.passwordSalt = passwordSalt;
        return this;
    }

    public static User getRemovedUser() {
        return removedUser;
    }
}
