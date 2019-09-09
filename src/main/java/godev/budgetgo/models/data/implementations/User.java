package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.abstractions.UserData;

public final class User implements UserData {
    private final int id;
    private final String email;
    private final String name;
    private final String surname;

    User(UserBuilder builder) {
        id = builder.getId();
        email = builder.getEmail();
        name = builder.getName();
        surname = builder.getSurname();
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
}
