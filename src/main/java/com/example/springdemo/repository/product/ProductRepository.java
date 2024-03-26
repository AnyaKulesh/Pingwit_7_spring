package com.example.springdemo.repository.product;

import com.example.springdemo.repository.product.Product;
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
public class ProductRepository {
    private final DataSource dataSource;

    public ProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAllProducts() {
        String getRequest = """
                SELECT * FROM products
                """;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(getRequest)) {
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBigDecimal(4)
                ));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> findProductById(Integer id) {
        String productFinder = String.format("SELECT * FROM products WHERE id = %d", id);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(productFinder)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Product(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBigDecimal(4)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer createProduct(Product product) {
        String createRequest = """
                INSERT INTO products(id,name,description,price) VALUES(?,?,?,?)
                """;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(createRequest)){
            int id = getNextProductId(statement.getConnection());
            statement.setInt(1, id);
            statement.setString(2, product.name());
            statement.setString(3, product.description());
            statement.setBigDecimal(4, product.price());
            statement.executeUpdate();

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product productToUpdate) {
        String updateRequest = """
                UPDATE products
                SET name = ?, description = ?, price = ?
                WHERE id = ?;
                """;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(updateRequest)) {
            statement.setString(1, productToUpdate.name());
            statement.setString(2, productToUpdate.description());
            statement.setBigDecimal(3, productToUpdate.price());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getNextProductId(Connection connection) {
        String nextIdRequest = """
                SELECT max(id) from products
                """;
        try (PreparedStatement statement = connection.prepareStatement(nextIdRequest)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void deleteProductById(Integer id) {
        String deleteRequest = String.format("DELETE FROM products WHERE id = %d", id);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(deleteRequest)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
