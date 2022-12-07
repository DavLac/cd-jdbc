package courses.dev.java.jdbc.assignments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresUserRepository implements UserRepository {
    private static final String JDBC_CONNECTION_DEMO = "jdbc:postgresql://localhost:5432/jdbctraining";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    @Override
    public Optional<User> getUserById(int id) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            System.out.println("I'm connected");
            try (Statement stmt = con.createStatement()) {
                String selectSql = "SELECT * FROM users WHERE user_id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                    pstmt.setString(1, String.valueOf(id));
                    try (ResultSet resultSet = stmt.executeQuery(pstmt.toString())) {
                        if (resultSet.next()) {
                            return Optional.of(new User(
                                    resultSet.getString("name"),
                                    resultSet.getInt("age"),
                                    resultSet.getInt("user_id")
                            ));
                        }
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            System.out.println("I'm connected");
            try (Statement stmt = con.createStatement()) {
                String selectSql = "SELECT * FROM users";
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        users.add(new User(
                                resultSet.getString("name"),
                                resultSet.getInt("age"),
                                resultSet.getInt("user_id")
                        ));
                    }
                    return users;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public User createUser(User user) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String insertUserSql = "INSERT INTO users VALUES (DEFAULT, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return getUserById(rs.getInt("user_id")).orElseThrow();
                        //new User (rs.getString("name"),rs.getInt("age")....
                    } else {
                        throw new IllegalArgumentException("This id could not be found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("This id could not be found");
    }

    @Override
    public User updateUserById(int id, User user) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String insertUserSql = "UPDATE users SET name = ?, age = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.setInt(3,id);
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return getUserById(id).orElseThrow();
                        //new User (rs.getString("name"),rs.getInt("age")....
                    } else {
                        throw new IllegalArgumentException("This id could not be found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("This id could not be found");
    }

    @Override
    public void deleteUserById(int id) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String insertUserSql = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1,id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
