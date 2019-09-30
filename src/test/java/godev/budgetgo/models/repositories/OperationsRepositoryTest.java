package godev.budgetgo.models.repositories;

import godev.budgetgo.models.data.implementations.*;
import godev.budgetgo.models.dbfactory.RepositoriesFactory;
import godev.budgetgo.models.dbfactory.implementations.MySqlDbFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OperationsRepositoryTest {
    private static OperationsRepository operationsRep;
    private static StoragesRepository storagesRep;
    private static UsersRepository usersRep;
    private static User userOleg;
    private static User userMaria;
    private static Storage storageA;
    private static Storage storageB;

    @BeforeAll
    static void init() {
        RepositoriesFactory repositoriesFactory = new MySqlDbFactory();
        operationsRep = repositoriesFactory.getOperationsRepository();
        storagesRep = repositoriesFactory.getStoragesRepository();
        usersRep = repositoriesFactory.getUsersRepository();
        clearTables();
        initUsers();
        initStorages();
    }

    @AfterEach
    void clearOperationsTable() {
        operationsRep.removeAll(new OperationsSpecification());
    }

    @Test
    void operationAddTest() {
        Operation operation = operationsRep.add(new OperationBuilder()
                .setStorage(storageA)
                .setMoneyDelta(-1000)
                .setCreator(userOleg)
                .create()
        );

        assertEquals(operation, operationsRep.get(operation.getId()));
    }

    @Test
    void operationRemoveTest() {
        Operation operation = operationsRep.add(new OperationBuilder()
                .setStorage(storageA)
                .setMoneyDelta(-1000)
                .setCreator(userOleg)
                .create()
        );
        operationsRep.remove(operation);

        assertNull(operationsRep.get(operation.getId()));
    }

    @Test
    void operationUpdateTest() {
        LocalDate date = LocalDate.of(2019, 9, 14);
        Operation operation = operationsRep.add(new OperationBuilder()
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
        operationsRep.update(updatedOperation);

        assertNotEquals(operation, operationsRep.get(operation.getId()));
        assertEquals(updatedOperation, operationsRep.get(operation.getId()));
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

        Operation[] operations = {
                operationsRep.add(builder.setDate(dateFrom).create()),
                operationsRep.add(builder.setDate(dateBetween).create()),
                operationsRep.add(builder.setDate(dateTo).create()),
                operationsRep.add(builder.setDate(dateFrom.minusDays(1)).create()),
                operationsRep.add(builder.setDate(dateTo.plusDays(1)).create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereDateFrom(dateFrom)
                .whereDateTo(dateTo);

        operationsRep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(operationsRep.get(o.getId()));
            } else {
                assertNotNull(operationsRep.get(o.getId()));
            }
        }
    }

    @Test
    void operationsRemoveAllByStorageTest() {
        OperationBuilder builder = new OperationBuilder().setCreator(userOleg);

        Operation[] operations = {
                operationsRep.add(builder.setStorage(storageA).create()),
                operationsRep.add(builder.setStorage(storageB).create()),
                operationsRep.add(builder.setStorage(storageB).create()),
                operationsRep.add(builder.setStorage(storageA).create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereStorage(storageA);

        operationsRep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(operationsRep.get(o.getId()));
            } else {
                assertNotNull(operationsRep.get(o.getId()));
            }
        }
    }

    @Test
    void operationsRemoveAllByIdTest() {
        OperationBuilder builder = new OperationBuilder()
                .setCreator(userOleg)
                .setStorage(storageA);

        Operation[] operations = {
                operationsRep.add(builder.create()),
                operationsRep.add(builder.create()),
                operationsRep.add(builder.create()),
                operationsRep.add(builder.create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereId(operations[2].getId());

        operationsRep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(operationsRep.get(o.getId()));
            } else {
                assertNotNull(operationsRep.get(o.getId()));
            }
        }
    }

    private static void clearTables() {
        operationsRep.removeAll(new OperationsSpecification());
        storagesRep.removeAll(new StoragesSpecification());
        usersRep.removeAll(new UsersSpecification());
    }

    private static void initUsers() {
        userOleg = usersRep.add(new UserBuilder()
                .setEmail("oleg.grigorijan@gmail.com")
                .setName("Oleg")
                .setSurname("Grigorijan")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );
        userMaria = usersRep.add(new UserBuilder()
                .setEmail("maria.golomako@gmail.com")
                .setName("Maria")
                .setSurname("Golomako")
                .setPasswordHash("abcdef")
                .setPasswordSalt("123456")
                .create()
        );
    }

    private static void initStorages() {
        storageA = storagesRep.add(new StorageBuilder()
                .setName("Visa")
                .addUser(userOleg)
                .addUser(userMaria)
                .setCreator(userOleg)
                .create()
        );
        storageB = storagesRep.add(new StorageBuilder()
                .setName("MasterCard")
                .addUser(userOleg)
                .addUser(userMaria)
                .setCreator(userOleg)
                .create()
        );
    }
}
