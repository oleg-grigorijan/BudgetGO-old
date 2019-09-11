package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.Identifiable;
import godev.budgetgo.models.data.Specification;
import godev.budgetgo.models.repositories.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

abstract class MySqlRepository<T extends Identifiable, V extends Specification<T>> implements Repository<T, V> {
    protected List<Consumer<T>> subscribersOnAdd = new ArrayList<>();
    protected List<Consumer<T>> subscribersOnRemove = new ArrayList<>();
    protected List<BiConsumer<T, T>> subscribersOnUpdate = new ArrayList<>();

    protected abstract T extract(ResultSet resultSet);

    protected List<T> extractAll(PreparedStatement statement) throws SQLException {
        List<T> result = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) result.add(extract(resultSet));
        return result;
    }

    @Override
    public void subscribe(Consumer<T> onAdd, Consumer<T> onRemove, BiConsumer<T, T> onUpdate) {
        if (onAdd != null) subscribersOnAdd.add(onAdd);
        if (onRemove != null) subscribersOnRemove.add(onRemove);
        if (onUpdate != null) subscribersOnUpdate.add(onUpdate);
    }
}