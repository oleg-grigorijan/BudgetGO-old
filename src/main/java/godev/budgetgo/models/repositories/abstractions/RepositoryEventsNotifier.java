package godev.budgetgo.models.repositories.abstractions;

public interface RepositoryEventsNotifier<T> {
    void subscribe(RepositoryListener<T> listener);

    void unsubscribe(RepositoryListener listener);
}
