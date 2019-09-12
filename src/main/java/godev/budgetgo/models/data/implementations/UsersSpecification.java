package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Specification;

public class UsersSpecification implements Specification<User> {
    private Long id;
    private String email;
    private Storage storage;

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

    public UsersSpecification withStorageAccess(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean specified(User entity) {
        return (id == null || entity.getId() == id)
                && (email == null || entity.getEmail().equals(email))
                && (storage == null || storage.getUsers().contains(entity));
    }
}
