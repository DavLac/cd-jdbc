package courses.dev.java.jdbc.assignments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAssignment {


    public static void main(String[] args) {
        UserRepository repo = new PostgressUserRepository();
        repo.getAllUsers().forEach(System.out::println);

    }
}
