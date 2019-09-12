package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.Identifiable;
import godev.budgetgo.models.data.Specification;
import godev.budgetgo.models.repositories.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

abstract class MySqlRepository<T extends Identifiable, V extends Specification<T>> implements Repository<T, V> {
    protected static String tableName;
    protected final Connection connection = Config.getConnectionFactory().createConnection();
    protected List<Consumer<T>> subscribersOnAdd = new ArrayList<>();
    protected List<Consumer<T>> subscribersOnRemove = new ArrayList<>();
    protected List<BiConsumer<T, T>> subscribersOnUpdate = new ArrayList<>();

    protected abstract T extract(ResultSet resultSet) throws SQLException;

    protected List<T> extractAll(PreparedStatement statement) throws SQLException {
        List<T> result = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) result.add(extract(resultSet));
        return result;
    }

    @Override
    public T get(long id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? extract(resultSet) : null;
        } catch (SQLException e) {
            // TODO: logging
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected long getGeneratedId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getLong(1);
        } else {
            throw new SQLException("Can't get generated id");
        }

    }

    @Override
    public void subscribe(Consumer<T> onAdd, Consumer<T> onRemove, BiConsumer<T, T> onUpdate) {
        if (onAdd != null) subscribersOnAdd.add(onAdd);
        if (onRemove != null) subscribersOnRemove.add(onRemove);
        if (onUpdate != null) subscribersOnUpdate.add(onUpdate);
    }
}