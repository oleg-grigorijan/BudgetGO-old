package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.implementations.OperationsSpecification;

import java.sql.Date;

class OperationsConditionsFactory implements ConditionsFactory<OperationsSpecification> {
    @Override
    public Conditions createFrom(OperationsSpecification spec) {
        Conditions result = new Conditions();

        if (spec.getId() != null) {
            result.add("id = ?",
                    StatementSetter.forLong(spec.getId()));
        }

        if (spec.getStorage() != null) {
            result.add("storage_id = ?",
                    StatementSetter.forLong(spec.getStorage().getId()));
        }

        if (spec.getDateFrom() != null) {
            result.add("date >= ?",
                    StatementSetter.forDate(Date.valueOf(spec.getDateFrom())));
        }

        if (spec.getDateTo() != null) {
            result.add("date <= ?",
                    StatementSetter.forDate(Date.valueOf(spec.getDateTo())));
        }

        return result;
    }
}
