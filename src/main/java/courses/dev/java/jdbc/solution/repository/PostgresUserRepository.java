package courses.dev.java.jdbc.solution.repository;


import courses.dev.java.jdbc.solution.config.AppConfig;
import courses.dev.java.jdbc.solution.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserRepository implements UserRepository {

    public static final String JDBC_CONNECTION_DEMO = AppConfig.load().getProperty("db.postgres.url");
    public static final String DB_USER = AppConfig.load().getProperty("db.postgres.user");
    public static final String DB_PASSWORD = AppConfig.load().getProperty("db.postgres.password");

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
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(sql);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void allUsersGetOlderOnError() {
        List<User> users = getAllUsers();
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {

            String insertUserSql = "UPDATE users SET age=age+1 WHERE id=?";

            boolean autoCommit = con.getAutoCommit();
            try {
                con.setAutoCommit(false);

                PreparedStatement pstmt1 = con.prepareStatement(insertUserSql);
                pstmt1.setInt(1, users.get(0).getId());
                PreparedStatement pstmt2 = con.prepareStatement(insertUserSql);
                pstmt2.setInt(1, users.get(1).getId());

                System.out.println("Updating user " + users.get(0).getName());
                pstmt1.executeUpdate();
                System.out.println("Updating user " + users.get(1).getName());
                pstmt2.executeUpdate();
                throw new RuntimeException("Unexpected error"); // throw an expected error
                // con.commit();
            } catch (Exception exc) {
                System.out.println("Unexpected error -> rollback");
                con.rollback();
            } finally {
                con.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void allUsersGetOlder() {
        List<User> users = getAllUsers();
        try (Connection con = DriverManager
                .getConnection(JDBC_CONNECTION_DEMO, DB_USER, DB_PASSWORD)) {

            String insertUserSql = "UPDATE users SET age=age+1 WHERE id=?";

            boolean autoCommit = con.getAutoCommit();
            try {
                con.setAutoCommit(false);

                PreparedStatement pstmt1 = con.prepareStatement(insertUserSql);
                pstmt1.setInt(1, users.get(0).getId());
                PreparedStatement pstmt2 = con.prepareStatement(insertUserSql);
                pstmt2.setInt(1, users.get(1).getId());
                PreparedStatement pstmt3 = con.prepareStatement(insertUserSql);
                pstmt3.setInt(1, users.get(2).getId());

                pstmt1.executeUpdate();
                pstmt2.executeUpdate();
                pstmt3.executeUpdate();
                con.commit();
            } catch (Exception exc) {
                System.out.println("Unexpected error -> rollback");
                con.rollback();
            } finally {
                con.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
