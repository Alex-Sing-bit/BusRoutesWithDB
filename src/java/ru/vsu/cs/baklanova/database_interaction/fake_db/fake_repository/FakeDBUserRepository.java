package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IUserRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBUserRepository implements IUserRepository<User> {
    private final FakeDB db;

    private static final int maxNameLength = 50;
    private static final int maxPhoneNumberLength = 21;
    private static final int maxPasswordLength = 70;

    public FakeDBUserRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(User entity) {
        entity.setId(db.getCurrentUserId() + 1);
        validate(entity);
        db.addUser(entity);
        return true;
    }

    public void validate(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Stop cannot be null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        } else if (entity.getName().length() > maxNameLength) {
            throw new IllegalArgumentException("Too long user name");
        }
        if (entity.getPhoneNumber() == null || entity.getPhoneNumber() .isEmpty()) {
            throw new IllegalArgumentException("User phone number cannot be null or empty");
        } else if (entity.getPhoneNumber().length() > maxPhoneNumberLength) {
            throw new IllegalArgumentException("Too long user phone number");
        }
        if (getByPhoneNumber(entity.getPhoneNumber()) != null) {
            throw new IllegalArgumentException("Not unique phone number");
        }

        if (entity.getPassword() == null || entity.getPassword() .isEmpty()) {
            throw new IllegalArgumentException("User password cannot be null or empty");
        } else if (entity.getPassword().length() > maxPasswordLength) {
            throw new IllegalArgumentException("Too long user password");
        }
    }

    @Override
    public User getById(int id) {
        return db.getUsers().get(id);
    }

    @Override
    public boolean update(User entity) {
        db.getUsers().put(entity.getId(), entity);
        return true;
    }

    @Override
    public boolean delete(int id) {
        User s = getById(id);
        if (s != null) {
            db.getUsers().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.getUsers().values());
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        String phoneNumberStrip = phoneNumber.strip();
        return db.getUsers().values().stream()
                .filter(user -> Objects.equals(user.getPhoneNumber(), phoneNumberStrip))
                .findFirst()
                .orElse(null);
    }
}
