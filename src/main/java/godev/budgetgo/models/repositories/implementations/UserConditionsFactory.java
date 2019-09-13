package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.implementations.UsersSpecification;

public class UserConditionsFactory implements ConditionsFactory<UsersSpecification> {
    @Override
    public Conditions createFrom(UsersSpecification spec) {
        Conditions result = new Conditions();

        if (spec.getId() != null) {
            result.add("id = ?",
                    StatementSetter.forLong(spec.getId()));
        }

        if (spec.getEmail() != null) {
            result.add("email = ?",
                    StatementSetter.forString(spec.getEmail())
            );
        }

        if (spec.getStorage() != null) {
            result.add("id IN (" +
                            "SELECT user_id " +
                            "FROM users_storages " +
                            "WHERE storage_id = ?" +
                            ")",
                    StatementSetter.forLong(spec.getStorage().getId())
            );
        }

        return result;
    }
}
