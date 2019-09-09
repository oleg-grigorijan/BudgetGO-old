package godev.budgetgo.models.repositories.abstractions;

import godev.budgetgo.models.data.abstractions.Data;
import godev.budgetgo.models.data.abstractions.DataBuilder;

import java.util.List;

public interface Repository<T extends Data, V extends DataSpecification> {
    T create(DataBuilder<T> newDataBuilder);

    void remove(T data);

    T edit(T oldData, DataBuilder<T> newDataBuilder);

    List<T> get(V spec);

    V createSpecification();
}
