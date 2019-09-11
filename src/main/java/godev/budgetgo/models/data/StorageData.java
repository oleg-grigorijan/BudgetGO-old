package godev.budgetgo.models.data;

import java.util.List;

public interface StorageData extends Identifiable {
    String getName();

    List<UserData> getUsers();
}
