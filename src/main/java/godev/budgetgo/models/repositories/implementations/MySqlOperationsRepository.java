package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.Operation;
import godev.budgetgo.models.data.implementations.OperationBuilder;
import godev.budgetgo.models.data.implementations.OperationsSpecification;
import godev.budgetgo.models.repositories.OperationsRepository;

import java.sql.*;

public class MySqlOperationsRepository extends MySqlRepository<Operation, OperationsSpecification, OperationsConditionsFactory> implements OperationsRepository {
    public MySqlOperationsRepository() {
        tableName = "operations";
        conditionsFactory = new OperationsConditionsFactory();
    }

    @Override
    protected Operation extract(ResultSet resultSet) throws SQLException {
        return new OperationBuilder()
                .setId(resultSet.getLong("id"))
                .setStorage(Config.getStoragesRepository().get(resultSet.getLong("storage_id")))
                .setMoneyDelta(resultSet.getLong("money_delta"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setDescription(resultSet.getString("description"))
                .setCreationDate(resultSet.getDate("creation_date").toLocalDate())
                .setCreator(Config.getUsersRepository().get(resultSet.getLong("creator_id")))
                .create();
    }

    @Override
    public Operation add(Operation entity) {

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT " +
                             "INTO operations (storage_id, money_delta, date, " +
                             "description, creation_date, creator_id) " +
                             "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, entity.getStorage().getId());
            statement.setLong(2, entity.getMoneyDelta());
            statement.setDate(3, Date.valueOf(entity.getDate()));
            statement.setString(4, entity.getDescription());
            statement.setDate(5, Date.valueOf(entity.getCreationDate()));
            statement.setLong(6, entity.getCreator().getId());
            statement.executeUpdate();

            Operation result = new OperationBuilder()
                    .from(entity)
                    .setId(getGeneratedId(statement))
                    .create();

            subscribersOnAdd.forEach(s -> s.accept(result));

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Operation updatedEntity) {
        Operation oldEntity = get(updatedEntity.getId());

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE operations " +
                             "SET storage_id = ?, money_delta = ?, date = ?, " +
                             "description = ?, creation_date = ?, creator_id = ? " +
                             "WHERE id = ?")
        ) {
            statement.setLong(1, updatedEntity.getStorage().getId());
            statement.setLong(2, updatedEntity.getMoneyDelta());
            statement.setDate(3, Date.valueOf(updatedEntity.getDate()));
            statement.setString(4, updatedEntity.getDescription());
            statement.setDate(5, Date.valueOf(updatedEntity.getCreationDate()));
            statement.setLong(6, updatedEntity.getCreator().getId());
            statement.setLong(7, oldEntity.getId());
            statement.executeUpdate();

            subscribersOnUpdate.forEach((s) -> s.accept(oldEntity, updatedEntity));

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }
}
