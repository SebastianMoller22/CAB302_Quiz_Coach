package com.example.quizCoach.database;

import com.example.quizCoach.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<User> USERS = new ArrayList<>();
    private static int autoIncrementedId = 0;

    public MockUserDAO() {
    }

    @Override
    public void addUser(User user) {
        user.setId(autoIncrementedId);
        autoIncrementedId++;
        USERS.add(user);
    }

    @Override
    public void updateUser(User user) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).getId() == user.getId()) {
                USERS.set(i, user);
                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        USERS.remove(user);
    }

    @Override
    public User getUser(int id) {
        for (User user : USERS) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        for (User user : USERS) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(USERS);
    }
}
