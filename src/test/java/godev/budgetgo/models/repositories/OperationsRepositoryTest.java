package godev.budgetgo.models.repositories;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.data.implementations.*;
import godev.budgetgo.models.repositories.implementations.MySqlOperationsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OperationsRepositoryTest {
    private static OperationsRepository repository = new MySqlOperationsRepository();
    private static User userOleg;
    private static User userMaria;
    private static Storage storageA;
    private static Storage storageB;

    @BeforeAll
    static void init() {
        Config.runTestMode();
        clearTables();
        initUsers();
        initStorages();
    }

    @AfterEach
    void clearOperationsTable() {
        Config.getOperationsRepository().removeAll(new OperationsSpecification());
    }

    @Test
    void operationAddTest() {
        Operation operation = repository.add(
                new OperationBuilder()
                        .setStorage(storageA)
                        .setMoneyDelta(-1000)
                        .setCreator(userOleg)
                        .create()
        );

        assertEquals(operation, repository.get(operation.getId()));
    }

    @Test
    void operationRemoveTest() {
        Operation operation = repository.add(
                new OperationBuilder()
                        .setStorage(storageA)
                        .setMoneyDelta(-1000)
                        .setCreator(userOleg)
                        .create()
        );
        repository.remove(operation);

        assertNull(repository.get(operation.getId()));
    }

    @Test
    void operationUpdateTest() {
        LocalDate date = LocalDate.of(2019, 9, 14);
        Operation operation = repository.add(
                new OperationBuilder()
                        .setStorage(storageA)
                        .setMoneyDelta(-1000)
                        .setCreator(userOleg)
                        .setDescription("foo")
                        .setDate(date)
                        .setCreationDate(date)
                        .create()
        );

        Operation updatedOperation = new OperationBuilder()
                .setId(operation.getId())
                .setStorage(storageB)
                .setMoneyDelta(-1200)
                .setCreator(userMaria)
                .setDescription("boo")
                .setDate(date.plusDays(1))
                .setCreationDate(date.plusDays(1))
                .create();
        repository.update(updatedOperation);

        assertNotEquals(operation, repository.get(operation.getId()));
        assertEquals(updatedOperation, repository.get(operation.getId()));
    }

    @Test
    void operationsRemoveAllByDateTest() {
        LocalDate dateFrom = LocalDate.of(2019, 9, 14);
        LocalDate dateTo = dateFrom.plusDays(2);
        LocalDate dateBetween = dateFrom.plusDays(1);

        OperationBuilder builder = new OperationBuilder()
                .setStorage(storageA)
                .setMoneyDelta(-1000)
                .setCreator(userOleg);

        Operation operationFrom = repository.add(
                builder.setDate(dateFrom).create()
        );
        Operation operationBetween = repository.add(
                builder.setDate(dateBetween).create()
        );
        Operation operationTo = repository.add(
                builder.setDate(dateTo).create()
        );
        Operation operationBefore = repository.add(
                builder.setDate(dateFrom.minusDays(1)).create()
        );
        Operation operationAfter = repository.add(
                builder.setDate(dateTo.plusDays(1)).create()
        );

        repository.removeAll(new OperationsSpecification()
                .whereDateFrom(dateFrom)
                .whereDateTo(dateTo)
        );

        assertNull(repository.get(operationFrom.getId()));
        assertNull(repository.get(operationBetween.getId()));
        assertNull(repository.get(operationTo.getId()));
        assertNotNull(repository.get(operationBefore.getId()));
        assertNotNull(repository.get(operationAfter.getId()));
    }

    @Test
    void operationsRemoveAllByStorageAndIdTest() {
        Operation operationA = repository.add(
                new OperationBuilder()
                        .setStorage(storageA)
                        .setMoneyDelta(-1000)
                        .setCreator(userOleg)
                        .create()
        );
        Operation operationB = repository.add(
                new OperationBuilder()
                        .setStorage(storageB)
                        .setMoneyDelta(-1000)
                        .setCreator(userOleg)
                        .create()
        );
        repository.removeAll(new OperationsSpecification()
                .whereStorage(storageA));

        assertNull(repository.get(operationA.getId()));
        assertNotNull(repository.get(operationB.getId()));

        repository.removeAll(new OperationsSpecification()
                .whereId(operationB.getId()));

        assertNull(repository.get(operationB.getId()));
    }

    private static void clearTables() {
        Config.getOperationsRepository().removeAll(new OperationsSpecification());
        Config.getStoragesRepository().removeAll(new StoragesSpecification());
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }

    private static void initUsers() {
        userOleg = Config.getUsersRepository().add(
                new UserBuilder()
                        .setEmail("oleg.grigorijan@gmail.com")
                        .setName("Oleg")
                        .setSurname("Grigorijan")
                        .setPasswordHash("abcdef")
                        .setPasswordSalt("123456")
                        .create()
        );
        userMaria = Config.getUsersRepository().add(new UserBuilder()
                .setEmail("maria.golomako@gmail.com")
                .setName("Maria")
                .setSurname("Golomako")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );
    }

    private static void initStorages() {
        storageA = Config.getStoragesRepository().add(
                new StorageBuilder()
                        .setName("Visa")
                        .addUser(userOleg)
                        .addUser(userMaria)
                        .create()
        );
        storageB = Config.getStoragesRepository().add(
                new StorageBuilder()
                        .setName("MasterCard")
                        .addUser(userOleg)
                        .addUser(userMaria)
                        .create()
        );
    }
}
