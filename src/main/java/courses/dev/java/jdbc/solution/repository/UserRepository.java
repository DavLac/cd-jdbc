package courses.dev.java.jdbc.solution.repository;

import courses.dev.java.jdbc.solution.model.User;

import java.util.List;

public interface UserRepository {
    User getUserById(Integer id);
    List<User> getAllUsers();
    Integer createUser(User user);
    void updateUserById(Integer id, User user);
    void deleteUserById(Integer id);
    void deleteAllUsers();
    void allUsersGetOlderOnError();
    void allUsersGetOlder();
}
