package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.DataBuilder;
import godev.budgetgo.models.data.Identifiable;
import godev.budgetgo.models.data.Specification;

import java.util.List;

public interface Repository<T extends Identifiable, V extends Specification> {
    T create(DataBuilder<T> newDataBuilder);

    void remove(T data);

    T edit(T oldData, DataBuilder<T> newDataBuilder);

    List<T> get(V spec);

    V createSpecification();
}
