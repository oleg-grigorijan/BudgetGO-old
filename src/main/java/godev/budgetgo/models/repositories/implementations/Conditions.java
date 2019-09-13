package godev.budgetgo.models.repositories.implementations;

import java.util.ArrayList;
import java.util.List;

class Conditions {
    private List<StatementSetter> setters = new ArrayList<>();
    private StringBuilder conditions = new StringBuilder();

    List<StatementSetter> getSetters() {
        return setters;
    }

    String toSql() {
        return new String(conditions);
    }

    void add(String condition, StatementSetter setter) {
        conditions.append(conditions.length() == 0 ? "WHERE " : " AND ")
                .append(condition);
        setters.add(setter);
    }
}