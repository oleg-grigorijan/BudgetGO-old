package godev.budgetgo.models.repositories;

public interface RepositoryListener<T> {
    void onCreate(T newData);

    void onRemove(T removedData);

    void onEdit(T oldData, T newData);
}
