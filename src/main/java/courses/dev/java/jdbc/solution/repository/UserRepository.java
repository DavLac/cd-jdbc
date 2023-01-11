package courses.dev.java.jdbc.solution.repository;

import courses.dev.java.jdbc.solution.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUserById(Long id, User user);
    void deleteUserById(Long id);
    void deleteAllUsers();
    void allUsersGetOlderOnError();
    void allUsersGetOlder();
}
