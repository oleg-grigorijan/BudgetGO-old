package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Specification;
import godev.budgetgo.models.data.UserData;

public class UsersSpecification implements Specification<UserData> {
    private Long id;
    private String email;

    public UsersSpecification whereId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UsersSpecification whereEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean specified(UserData entity) {
        return (id == null || entity.getId() == id)
                && (email == null || entity.getEmail().equals(email));
    }
}
