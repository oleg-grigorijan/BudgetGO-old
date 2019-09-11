package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Specification;
import godev.budgetgo.models.data.StorageData;
import godev.budgetgo.models.data.UserData;

public class StoragesSpecification implements Specification<StorageData> {
    private Long id;
    private UserData user;

    public StoragesSpecification whereId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public StoragesSpecification whereUser(UserData user) {
        this.user = user;
        return this;
    }

    public UserData getUser() {
        return user;
    }

    @Override
    public boolean specified(StorageData entity) {
        return (id == null || entity.getId() == id)
                && (user == null || entity.getUsers().contains(user));
    }
}
