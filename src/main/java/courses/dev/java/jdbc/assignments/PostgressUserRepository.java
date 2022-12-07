package courses.dev.java.jdbc.assignments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgressUserRepository implements UserRepository{
    private static final String JDBC_CONNECTION_DEMO = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD))
        {
            try (Statement stmt = con.createStatement()) {
                String selectSql = "SELECT * FROM users";
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        users.add(new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age")
                        ));
                    }
                    return users;

                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
