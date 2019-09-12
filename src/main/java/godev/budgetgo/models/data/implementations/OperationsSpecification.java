package godev.budgetgo.models.data.implementations;

import godev.budgetgo.models.data.Specification;

import java.time.LocalDate;

public class OperationsSpecification implements Specification<Operation> {
    private Long id;
    private Storage storage;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public OperationsSpecification whereId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OperationsSpecification whereStorage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Storage getStorage() {
        return storage;
    }

    public OperationsSpecification whereDateFrom(LocalDate date) {
        this.dateFrom = date;
        return this;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public OperationsSpecification whereDateTo(LocalDate date) {
        this.dateTo = date;
        return this;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public boolean specified(Operation entity) {
        return (id == null || entity.getId() == id)
                && (storage == null || entity.getStorage().equals(storage))
                && (dateFrom == null || entity.getDate().compareTo(dateFrom) >= 0)
                && (dateTo == null || entity.getDate().compareTo(dateTo) <= 0);
    }
}
