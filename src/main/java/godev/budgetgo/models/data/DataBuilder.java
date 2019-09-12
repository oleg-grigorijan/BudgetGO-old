package godev.budgetgo.models.data;

public interface DataBuilder<T extends Identifiable> {
    DataBuilder<T> from(T entity);

    DataBuilder<T> setId(long id);

    T create();
}
