package godev.budgetgo.models.data;

public interface Specification<T extends Identifiable> {
    Specification<T> whereId(Long id);

    Long getId();

    boolean specified(T entity);
}
