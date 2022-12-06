package courses.dev.java.jdbc.solution;

import courses.dev.java.jdbc.solution.model.User;
import courses.dev.java.jdbc.solution.repository.PostgresUserRepository;
import courses.dev.java.jdbc.solution.repository.UserRepository;

public class JdbcAssignment {
    public static void main(String[] args) {
        UserRepository userRepository = new PostgresUserRepository();

        System.out.println("### Create users");
        Integer savedId = userRepository.createUser(new User(null, "bobby", 25));
        System.out.println(userRepository.getUserById(savedId));
        System.out.println(userRepository.getUserById(userRepository.createUser(new User(null, "john", 12))));

        System.out.println("\n### Get all users");
        userRepository.getAllUsers().forEach(System.out::println);
        userRepository.updateUserById(savedId, new User(null, "paul", 29));
        System.out.println("\n### Update user by id=" + savedId);

        System.out.println(userRepository.getUserById(savedId));

        System.out.println("\n### Get user by id=" + savedId);
        System.out.println(userRepository.getUserById(savedId));

        System.out.println("\n### Delete user by id=" + savedId);
        userRepository.deleteUserById(savedId);
        System.out.println(true);

        System.out.println("\n### Create user");
        System.out.println(userRepository.getUserById(userRepository.createUser(new User(null, "martin", 42))));
        System.out.println("### Delete all users");
        userRepository.deleteAllUsers();
        System.out.println(true);

        System.out.println("\n### Get all users");
        System.out.println(userRepository.getAllUsers().size());
    }
}