package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.Identifiable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RepositoryEventsNotifier<T extends Identifiable> {
    void subscribe(Consumer<T> onAdd, Consumer<T> onRemove, BiConsumer<T, T> onUpdate);
}
