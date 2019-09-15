package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.*;
import godev.budgetgo.models.repositories.StoragesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class MySqlStoragesRepository extends MySqlRepository<Storage, StoragesSpecification, StoragesConditionsFactory> implements StoragesRepository {
    public MySqlStoragesRepository() {
        conditionsFactory = new StoragesConditionsFactory();
        tableName = "storages";
    }

    @Override
    protected Storage extract(ResultSet resultSet) throws SQLException {
        long creatorId = resultSet.getLong("creator_id");
        User creator = creatorId == 0 ?
                UserBuilder.getRemovedUser() : Config.getUsersRepository().get(creatorId);

        long entityId = resultSet.getLong("id");
        StorageBuilder builder = new StorageBuilder()
                .setId(entityId)
                .setName(resultSet.getString("name"))
                .setCreator(creator);

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT user_id " +
                             "FROM users_storages " +
                             "WHERE storage_id = ?")
        ) {
            statement.setLong(1, entityId);
            ResultSet resultSetUsers = statement.executeQuery();
            while (resultSetUsers.next()) {
                builder.addUser(Config.getUsersRepository()
                        .get(resultSetUsers.getInt("user_id"))
                );
            }
        }

        return builder.create();
    }

    @Override
    public Storage add(Storage entity) {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT " +
                             "INTO storages (name, creator_id) " +
                             "VALUE (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getCreator().getId());
            statement.executeUpdate();
            long storageId = getGeneratedId(statement);

            for (User u : entity.getUsers()) {
                addToUsersStorages(storageId, u.getId());
            }

            Storage result = new StorageBuilder()
                    .from(entity)
                    .setId(storageId)
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
    public void update(Storage updatedEntity) {
        Storage oldEntity = get(updatedEntity.getId());

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE storages " +
                             "SET name = ?, creator_id = ? " +
                             "WHERE id = ?")
        ) {
            statement.setString(1, updatedEntity.getName());
            statement.setLong(2, updatedEntity.getCreator().getId());
            statement.setLong(3, oldEntity.getId());
            statement.executeUpdate();

            Set<User> oldUsers = oldEntity.getUsers();
            Set<User> updatedUsers = updatedEntity.getUsers();
            if (!oldUsers.equals(updatedUsers)) {
                updateUsersAccess(oldEntity.getId(), oldUsers, updatedUsers);
            }

            subscribersOnUpdate.forEach(s -> s.accept(oldEntity, updatedEntity));

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }


    private void updateUsersAccess(long storageId, Set<User> oldUsers, Set<User> updatedUsers) throws SQLException {
        for (User oldU : oldUsers) {
            if (!updatedUsers.contains(oldU)) {
                removeFromUsersStorages(storageId, oldU.getId());
            }
        }

        for (User updU : updatedUsers) {
            if (!oldUsers.contains(updU)) {
                addToUsersStorages(storageId, updU.getId());
            }
        }
    }

    private void removeFromUsersStorages(long storageId, long userId) throws SQLException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE " +
                             "FROM users_storages " +
                             "WHERE user_id = ? AND storage_id = ?")
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, storageId);
            statement.executeUpdate();
        }
    }


    private void addToUsersStorages(long storageId, long userId) throws SQLException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT " +
                             "INTO users_storages (user_id, storage_id) " +
                             "VALUES (?, ?)")
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, storageId);
            statement.executeUpdate();
        }
    }
}
