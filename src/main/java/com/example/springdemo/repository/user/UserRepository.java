package com.example.springdemo.repository.user;

import com.example.springdemo.repository.user.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserRepository {
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAllUsers() {
        String findRequest = """
                SELECT id, name, surname, email, phone FROM users
                """;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(findRequest)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5))
                );
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     public Set<String> getAllEmails(){
         String findRequest = """
                SELECT email FROM users
                """;
         try (PreparedStatement statement = dataSource.getConnection().prepareStatement(findRequest)) {
             ResultSet resultSet = statement.executeQuery();
             Set<String> emails = new HashSet<>();
             while (resultSet.next()) {
                 emails.add(resultSet.getString(1));
             }
             return emails;
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }

    public Optional<User> findUserById(Integer id) {
        String findUserRequest = String.format(
                "SELECT id, name, surname, email, phone FROM users WHERE id =%d", id);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(findUserRequest)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5))
                );
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer createUser(User user) {
        String createRequest = """
                INSERT INTO users(id, name, surname, email, phone) VALUES(?,?,?,?,?)
                """;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(createRequest)) {
            int id = getNextUserId(statement.getConnection());
            statement.setInt(1, id);
            statement.setString(2, user.name());
            statement.setString(3, user.surname());
            statement.setString(4, user.email());
            statement.setString(5, user.phone());
            statement.executeUpdate();

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getNextUserId(Connection connection) {
        String nextIdRequest = "SELECT nextval('user_id_seq')";
        try (PreparedStatement statement = connection.prepareStatement(nextIdRequest)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void deleteUserById(Integer id) {
        String deleteRequest = String.format(
                "DELETE FROM users WHERE id = %d", id);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(deleteRequest)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
