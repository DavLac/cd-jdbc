package courses.dev.java.jdbc.assignments;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(int id) throws Exception;
    List<User> getAllUsers(); //should take page number and page size
    User createUser(User user);
    User updateUserById(int id, User user);
    void deleteUserById(int id);
}
