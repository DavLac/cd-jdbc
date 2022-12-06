package courses.dev.java.jdbc.solution.repository;

import courses.dev.java.jdbc.solution.model.User;

import java.util.List;

public interface UserRepository {
    User getUserById();
    List<User> getAllUsers();
    User createUser(User user);
    User updateUserById(User user);
    void deleteUserById(Integer id);
}
