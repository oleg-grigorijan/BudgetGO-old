package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.*;

public interface RepositoriesFactory {
    Repository<UserData, Specification> createUsersRepository();

    Repository<OperationData, OperationSpecification> createOperationsRepository();

    Repository<StorageData, Specification> createStoragesRepository();
}
