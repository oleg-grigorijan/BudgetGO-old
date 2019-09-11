package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.Identifiable;
import godev.budgetgo.models.data.Specification;

import java.util.List;

public interface Repository<T extends Identifiable, V extends Specification<T>> extends RepositoryEventsNotifier<T> {
    T add(T entity);

    void remove(T entity);

    void removeAll(V spec);

    void update(T updatedEntity);

    T get(long id);

    List<T> getAll(V spec);
}
