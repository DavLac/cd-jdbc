package courses.dev.java.jdbc.assignments;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(int id);
    List<User> getAllUsers();       //should take page number and page size (in case of millions of users)
    User createUser(User user);     //Or return the Id
    User updateUserById(int id, User user);
    void deleteUserById(int id);
}
