package godev.budgetgo.models.dbfactory;

import godev.budgetgo.models.repositories.OperationsRepository;
import godev.budgetgo.models.repositories.StoragesRepository;
import godev.budgetgo.models.repositories.UsersRepository;

public interface RepositoriesFactory {
    UsersRepository getUsersRepository();

    StoragesRepository getStoragesRepository();

    OperationsRepository getOperationsRepository();
}
