package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Specification;

public class StoragesSpecification implements Specification<Storage> {
    private Long id;
    private User user;

    public StoragesSpecification whereId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public StoragesSpecification whereUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean specified(Storage entity) {
        return (id == null || entity.getId() == id)
                && (user == null || entity.getUsers().contains(user));
    }
}
