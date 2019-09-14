package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.connection.ConnectionFactory;
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

abstract class MySqlRepository<T extends Identifiable, S extends Specification<T>, C extends ConditionsFactory<S>> implements Repository<T, S> {
    C conditionsFactory;
    String tableName;
    final ConnectionFactory connectionFactory = Config.getConnectionFactory();
    List<Consumer<T>> subscribersOnAdd = new ArrayList<>();
    private List<Consumer<T>> subscribersOnRemove = new ArrayList<>();
    List<BiConsumer<T, T>> subscribersOnUpdate = new ArrayList<>();

    protected abstract T extract(ResultSet resultSet) throws SQLException;

    private List<T> extractAll(PreparedStatement statement) throws SQLException {
        List<T> result = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) result.add(extract(resultSet));
        return result;
    }

    @Override
    public T get(long id) {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * " +
                             "FROM " + tableName +
                             " WHERE id = ?")
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? extract(resultSet) : null;
        } catch (SQLException e) {
            // TODO: logging
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAll(S spec) {
        if (spec.getId() != null) {
            List<T> result = new ArrayList<>(1);
            T entity = get(spec.getId());
            if (entity != null && spec.specified(entity)) result.add(entity);
            return result;
        }

        Conditions conditions = conditionsFactory.createFrom(spec);

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * " +
                             "FROM " + tableName + " " +
                             conditions.toSql())
        ) {
            int i = 1;
            for (StatementSetter s : conditions.getSetters()) s.accept(statement, i++);
            return extractAll(statement);

        } catch (SQLException e) {
            // TODO: logging
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(T entity) {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE " +
                             "FROM " + tableName +
                             " WHERE id = ?")
        ) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();

            subscribersOnRemove.forEach((s) -> s.accept(entity));

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAll(S spec) {
        if (spec.getId() != null) {
            T entity = get(spec.getId());
            if (entity != null && spec.specified(entity)) remove(entity);
            return;
        }

        Conditions conditions = conditionsFactory.createFrom(spec);

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statementSelect = connection.prepareStatement(
                     "SELECT * " +
                             "FROM " + tableName + " " +
                             conditions.toSql());
             PreparedStatement statementDelete = connection.prepareStatement(
                     "DELETE " +
                             "FROM " + tableName + " "
                             + conditions.toSql())
        ) {
            int i = 1;
            for (StatementSetter s : conditions.getSetters()) {
                s.accept(statementSelect, i);
                s.accept(statementDelete, i);
                i++;
            }
            List<T> removed = extractAll(statementSelect);
            statementDelete.executeUpdate();

            subscribersOnRemove.forEach((s) -> {
                for (T u : removed) s.accept(u);
            });

        } catch (SQLException e) {
            // TODO: logging
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    long getGeneratedId(PreparedStatement statement) throws SQLException {
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