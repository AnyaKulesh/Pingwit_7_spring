package com.example.springdemo.repository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class UserRepository {
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAllUsers() {
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    " SELECT id, name, surname, email, phone FROM users");
            ResultSet rs = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5))
                );
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findUserById(Integer id) {

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                    " SELECT id, name, surname, email, phone FROM users WHERE id =%d", id));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5))
                );
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
