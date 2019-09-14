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
    private static OperationsRepository rep = new MySqlOperationsRepository();
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
        Operation operation = rep.add(new OperationBuilder()
                .setStorage(storageA)
                .setMoneyDelta(-1000)
                .setCreator(userOleg)
                .create()
        );

        assertEquals(operation, rep.get(operation.getId()));
    }

    @Test
    void operationRemoveTest() {
        Operation operation = rep.add(new OperationBuilder()
                .setStorage(storageA)
                .setMoneyDelta(-1000)
                .setCreator(userOleg)
                .create()
        );
        rep.remove(operation);

        assertNull(rep.get(operation.getId()));
    }

    @Test
    void operationUpdateTest() {
        LocalDate date = LocalDate.of(2019, 9, 14);
        Operation operation = rep.add(new OperationBuilder()
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
        rep.update(updatedOperation);

        assertNotEquals(operation, rep.get(operation.getId()));
        assertEquals(updatedOperation, rep.get(operation.getId()));
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
                rep.add(builder.setDate(dateFrom).create()),
                rep.add(builder.setDate(dateBetween).create()),
                rep.add(builder.setDate(dateTo).create()),
                rep.add(builder.setDate(dateFrom.minusDays(1)).create()),
                rep.add(builder.setDate(dateTo.plusDays(1)).create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereDateFrom(dateFrom)
                .whereDateTo(dateTo);

        rep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(rep.get(o.getId()));
            } else {
                assertNotNull(rep.get(o.getId()));
            }
        }
    }

    @Test
    void operationsRemoveAllByStorageTest() {
        OperationBuilder builder = new OperationBuilder().setCreator(userOleg);

        Operation[] operations = {
                rep.add(builder.setStorage(storageA).create()),
                rep.add(builder.setStorage(storageB).create()),
                rep.add(builder.setStorage(storageB).create()),
                rep.add(builder.setStorage(storageA).create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereStorage(storageA);

        rep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(rep.get(o.getId()));
            } else {
                assertNotNull(rep.get(o.getId()));
            }
        }
    }

    @Test
    void operationsRemoveAllByIdTest() {
        OperationBuilder builder = new OperationBuilder()
                .setCreator(userOleg)
                .setStorage(storageA);

        Operation[] operations = {
                rep.add(builder.create()),
                rep.add(builder.create()),
                rep.add(builder.create()),
                rep.add(builder.create()),
        };

        OperationsSpecification spec = new OperationsSpecification()
                .whereId(operations[2].getId());

        rep.removeAll(spec);

        for (Operation o : operations) {
            if (spec.specified(o)) {
                assertNull(rep.get(o.getId()));
            } else {
                assertNotNull(rep.get(o.getId()));
            }
        }
    }

    private static void clearTables() {
        Config.getOperationsRepository().removeAll(new OperationsSpecification());
        Config.getStoragesRepository().removeAll(new StoragesSpecification());
        Config.getUsersRepository().removeAll(new UsersSpecification());
    }

    private static void initUsers() {
        userOleg = Config.getUsersRepository().add(new UserBuilder()
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
        storageA = Config.getStoragesRepository().add(new StorageBuilder()
                .setName("Visa")
                .addUser(userOleg)
                .addUser(userMaria)
                .create()
        );
        storageB = Config.getStoragesRepository().add(new StorageBuilder()
                .setName("MasterCard")
                .addUser(userOleg)
                .addUser(userMaria)
                .create()
        );
    }
}
