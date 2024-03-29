package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.implementations.User;
import godev.budgetgo.models.data.implementations.UserBuilder;
import godev.budgetgo.models.data.implementations.UsersSpecification;
import godev.budgetgo.models.dbfactory.DbFactory;
import godev.budgetgo.models.repositories.UsersRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUsersRepository extends MySqlRepository<User, UsersSpecification, UsersConditionsFactory> implements UsersRepository {

    public MySqlUsersRepository(DbFactory dbFactory) {
        super("users", dbFactory, new UsersConditionsFactory());
    }

    @Override
    protected User extract(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setSurname(resultSet.getString("surname"))
                .setPasswordHash(resultSet.getString("password_hash"))
                .setPasswordSalt(resultSet.getString("password_salt"))
                .create();
    }

    @Override
    public User add(User entity) {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT " +
                             "INTO users (email, name, surname, " +
                             "password_hash, password_salt) " +
                             "VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getPasswordHash());
            statement.setString(5, entity.getPasswordSalt());
            statement.executeUpdate();

            User user = new UserBuilder()
                    .from(entity)
                    .setId(getGeneratedId(statement))
                    .create();

            subscribersOnAdd.forEach((s) -> s.accept(user));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User updatedEntity) {
        User oldEntity = get(updatedEntity.getId());

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users " +
                             "SET email = ?, name = ?, surname = ?, " +
                             "password_hash = ?, password_salt = ?" +
                             "WHERE id = ?")
        ) {
            statement.setString(1, updatedEntity.getEmail());
            statement.setString(2, updatedEntity.getName());
            statement.setString(3, updatedEntity.getSurname());
            statement.setString(4, updatedEntity.getPasswordHash());
            statement.setString(5, updatedEntity.getPasswordSalt());
            statement.setLong(6, updatedEntity.getId());
            statement.executeUpdate();

            subscribersOnUpdate.forEach((s) -> s.accept(oldEntity, updatedEntity));

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }
}
