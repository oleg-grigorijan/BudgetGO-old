package godev.budgetgo.models.data.abstractions;

public interface DataBuilder<T extends Data> {
    DataBuilder<T> setId(int id);

    T create();
}