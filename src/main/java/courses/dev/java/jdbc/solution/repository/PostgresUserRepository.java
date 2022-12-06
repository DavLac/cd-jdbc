package courses.dev.java.jdbc.solution.repository;


import courses.dev.java.jdbc.solution.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserRepository implements UserRepository {

    public static final String JDBC_CONNECTION_DEMO = "jdbc:postgresql://localhost:5432/demo";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "password";

    @Override
    public User getUserById(Integer id) {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = con.createStatement()) {
                String selectSql = String.format("SELECT * FROM users WHERE id=%d", id);
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age")
                        );
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer createUser(User user) {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String insertUserSql = "INSERT INTO users VALUES (DEFAULT, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserById(Integer id, User user) {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String insertUserSql = "UPDATE users SET name=?, age=? WHERE id=?";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSql)) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(Integer id) {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM users WHERE id=?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllUsers() {
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM users";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
