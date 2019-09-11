package godev.budgetgo.models.data;

public interface UserData extends Identifiable {
    String getName();

    String getSurname();

    String getEmail();

    String getPasswordHash();

    String getPasswordSalt();
}
