package godev.budgetgo.models.repositories.abstractions;

import godev.budgetgo.models.data.abstractions.OperationData;
import godev.budgetgo.models.data.abstractions.StorageData;
import godev.budgetgo.models.data.abstractions.UserData;

public interface RepositoriesFactory {
    Repository<UserData, DataSpecification> createUsersRepository();

    Repository<OperationData, OperationSpecification> createOperationsRepository();

    Repository<StorageData, DataSpecification> createStoragesRepository();
}
