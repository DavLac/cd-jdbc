package courses.dev.java.jdbc.assignments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAssignment {
    private static final String JDBC_CONNECTION_DEMO = "jdbc:postgresql://localhost:5432/jdbctraining";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {

        /*
            create user
            show user by id
            Create user
            get all the users
            Update a users
            Get all the users
            delete al the users
            get all the users
         */
        PostgresUserRepository repository = new PostgresUserRepository();

        User user = repository.getUserById(9).orElseThrow();
        System.out.println(user.getName());

        printUsers(repository);

        User userToCreate = new User("Sacha",27);
        System.out.println(repository.createUser(userToCreate));

        printUsers(repository);

        User userUpdate = new User("Igrid",30);
        System.out.println(repository.updateUserById(1,userUpdate));

        printUsers(repository);

        List<User> users = repository.getAllUsers();
        repository.deleteUserById(users.get(0).getId());

        printUsers(repository);
    }

    public static void printUsers(PostgresUserRepository repository){
        List<User> users = repository.getAllUsers();
        users.forEach((System.out::println));
    }
}
