package godev.budgetgo.models.repositories;

public interface RepositoryEventsNotifier<T> {
    void subscribe(RepositoryListener<T> listener);

    void unsubscribe(RepositoryListener listener);
}
