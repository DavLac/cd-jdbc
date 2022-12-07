package courses.dev.java.jdbc.assignments;

import java.util.List;

public interface UserRepository {
    User getUserById(int id);
    List<User> getAllUsers();
}
