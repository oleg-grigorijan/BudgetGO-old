package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.implementations.StoragesSpecification;

public class StoragesConditionsFactory implements ConditionsFactory<StoragesSpecification> {
    @Override
    public Conditions createFrom(StoragesSpecification spec) {
        Conditions result = new Conditions();

        if (spec.getId() != null) {
            result.add("id = ?",
                    StatementSetter.forLong(spec.getId())
            );
        }

        if (spec.getUser() != null) {
            result.add("id IN (" +
                            "SELECT storage_id " +
                            "FROM users_storages " +
                            "WHERE user_id = ?" +
                            ")",
                    StatementSetter.forLong(spec.getUser().getId())
            );
        }

        return result;
    }
}
