package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.DataBuilder;
import godev.budgetgo.models.data.UserData;

public class UserBuilder implements DataBuilder<UserData> {
    private long id = -1;
    private String email = "";
    private String name = "";
    private String surname = "";
    private String passwordHash = "";
    private String passwordSalt = "";

    @Override
    public UserBuilder from(UserData entity) {
        id = entity.getId();
        email = entity.getEmail();
        name = entity.getName();
        surname = entity.getSurname();
        passwordHash = entity.getPasswordHash();
        passwordSalt = entity.getPasswordSalt();
        return this;
    }

    @Override
    public UserData create() {
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
        if (email != null) this.email = email;
        return this;
    }

    String getName() {
        return name;
    }

    public UserBuilder setName(String name) {
        if (name != null) this.name = name;
        return this;
    }

    String getSurname() {
        return surname;
    }

    public UserBuilder setSurname(String surname) {
        if (surname != null) this.surname = surname;
        return this;
    }

    String getPasswordHash() {
        return passwordHash;
    }

    public UserBuilder setPasswordHash(String passwordHash) {
        if (passwordHash != null) this.passwordHash = passwordHash;
        return this;
    }

    String getPasswordSalt() {
        return passwordSalt;
    }

    public UserBuilder setPasswordSalt(String passwordSalt) {
        if (passwordSalt != null) this.passwordSalt = passwordSalt;
        return this;
    }
}
