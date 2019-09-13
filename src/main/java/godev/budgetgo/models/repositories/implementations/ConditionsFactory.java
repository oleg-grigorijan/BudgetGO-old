package godev.budgetgo.models.repositories.implementations;

import godev.budgetgo.models.data.Specification;

public interface ConditionsFactory<T extends Specification> {
    Conditions createFrom(T spec);
}
