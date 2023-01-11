package courses.dev.java.jdbc.solution;

import courses.dev.java.jdbc.solution.model.User;
import courses.dev.java.jdbc.solution.repository.PostgresUserRepository;
import courses.dev.java.jdbc.solution.repository.UserRepository;

public class JdbcAssignment {
    public static void main(String[] args) {
        UserRepository userRepository = new PostgresUserRepository();

        System.out.println("###### User CRUD ######");
        System.out.println("### Create users");
        User savedUser = userRepository.createUser(new User("bobby", 25));
        System.out.println(userRepository.getUserById(savedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        System.out.println(userRepository.getUserById(userRepository.createUser(new User("john", 12)).getId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        System.out.println("\n### Get all users");
        userRepository.getAllUsers().forEach(System.out::println);
        userRepository.updateUserById(savedUser.getId(), new User("paul", 29));
        System.out.println("\n### Update user by id=" + savedUser.getId());

        System.out.println(userRepository.getUserById(savedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        System.out.println("\n### Get user by id=" + savedUser.getId());
        System.out.println(userRepository.getUserById(savedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        System.out.println("\n### Delete user by id=" + savedUser.getId());
        userRepository.deleteUserById(savedUser.getId());
        System.out.println(true);

        System.out.println("\n### Create user");
        System.out.println(userRepository.getUserById(userRepository.createUser(new User("martin", 42)).getId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        System.out.println("### Delete all users");
        userRepository.deleteAllUsers();
        System.out.println(true);

        System.out.println("\n### Get all users");
        System.out.println(userRepository.getAllUsers().size());

        System.out.println("\n\n###### Transactions ######");
        System.out.println("### Create 3 users");
        userRepository.createUser(new User("user1", 1));
        userRepository.createUser(new User("user2", 2));
        userRepository.createUser(new User("user3", 3));
        userRepository.getAllUsers().forEach(System.out::println);
        System.out.println("\n### Add 1 to all user age - ROLLBACK");
        userRepository.allUsersGetOlderOnError();
        userRepository.getAllUsers().forEach(System.out::println);
        System.out.println("\n### Add 1 to all user age - SUCCESS");
        userRepository.allUsersGetOlder();
        userRepository.getAllUsers().forEach(System.out::println);
    }
}