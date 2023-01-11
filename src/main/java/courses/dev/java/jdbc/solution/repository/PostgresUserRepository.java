package courses.dev.java.jdbc.solution.repository;


import courses.dev.java.jdbc.solution.config.AppConfig;
import courses.dev.java.jdbc.solution.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class PostgresUserRepository implements UserRepository {

    public static final Properties PROPERTIES = AppConfig.load();
    public static final String JDBC_CONNECTION_DEMO = PROPERTIES.getProperty("db.postgres.url");
    public static final String DB_USER = PROPERTIES.getProperty("db.postgres.user");
    public static final String DB_PASSWORD = PROPERTIES.getProperty("db.postgres.password");

    private final Connection connection;

    public PostgresUserRepository() {
        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try (Statement stmt = connection.createStatement()) {
            String selectSql = String.format("SELECT * FROM users WHERE id=%d", id);
            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                if (resultSet.next()) {
                    return Optional.of(new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    ));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Statement stmt = connection.createStatement()) {
            String selectSql = "SELECT * FROM users";
            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    ));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        String insertUserSql = "INSERT INTO users VALUES (DEFAULT, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId((long) rs.getInt(1));
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateUserById(Long id, User user) {
        String insertUserSql = "UPDATE users SET name=?, age=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSql)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();

            user.setId(id);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllUsers() {
        String sql = "DELETE FROM users";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void allUsersGetOlderOnError() {
        List<User> users = getAllUsers();
        try {
            String insertUserSql = "UPDATE users SET age=age+1 WHERE id=?";

            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);

                PreparedStatement pstmt1 = connection.prepareStatement(insertUserSql);
                pstmt1.setLong(1, users.get(0).getId());
                PreparedStatement pstmt2 = connection.prepareStatement(insertUserSql);
                pstmt2.setLong(1, users.get(1).getId());

                System.out.println("Updating user " + users.get(0).getName());
                pstmt1.executeUpdate();
                System.out.println("Updating user " + users.get(1).getName());
                pstmt2.executeUpdate();
                throw new RuntimeException("Unexpected error"); // throw an expected error
                // con.commit();
            } catch (Exception exc) {
                System.out.println("Unexpected error -> rollback");
                connection.rollback();
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void allUsersGetOlder() {
        List<User> users = getAllUsers();
        try {
            String insertUserSql = "UPDATE users SET age=age+1 WHERE id=?";

            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);

                List<PreparedStatement> preparedStatements = new ArrayList<>();
                for(User user : users) {
                    PreparedStatement pstmt = connection.prepareStatement(insertUserSql);
                    pstmt.setLong(1, user.getId());
                    preparedStatements.add(pstmt);
                }

                for(PreparedStatement pstmt : preparedStatements) {
                    pstmt.executeUpdate();
                }

                connection.commit();
            } catch (Exception exc) {
                System.out.println("Unexpected error -> rollback");
                connection.rollback();
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
