package courses.dev.java.jdbc.assignments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgressUserRepository implements UserRepository {
    private static final String JDBC_CONNECTION_DEMO = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";

    @Override
    public Optional<User> getUserById(int id) throws Exception {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = con.createStatement()) {
                String selectSql = "SELECT * FROM users WHERE id=" + id;
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    if (resultSet.next()) {
                        return Optional.of(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age")));

                    } else {
                        return Optional.empty();
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = con.createStatement()) {
                String selectSql = "SELECT * FROM users";
                try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        users.add(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age")));
                    }
                    return users;

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                    //User user;
                    if (rs.next()) {
                        return new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
                    } else {
                        throw new RuntimeException("user not found");
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUserById(int id, User user) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String updateNameSql = "UPDATE users SET name=?, age=? WHERE id=" + id;
            try (PreparedStatement pstmt = con.prepareStatement(updateNameSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
                    } else {
                        throw new RuntimeException("User not found");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteUserById(int id) {
        try (Connection con = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {
            String deleteUserSql = "DELETE FROM users WHERE id=" + id;
            try (PreparedStatement pstmt = con.prepareStatement(deleteUserSql)) {
                pstmt.executeUpdate();
                System.out.println("deleted user with id " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error");
        }

    }
}
