package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Executor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ExecutorRepository {
    private static final String CREATE_CMD = "insert into executor (name, note) values (?, ?)";
    private static final String UPDATE_CMD = "update executor set name = ?, note = ? where id = ?";
    private static final String DELETE_CMD = "delete from executor where id = ?";

    public boolean create(Executor executor) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, executor.getName());
            preparedStatement.setString(2, executor.getNote());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean update(Executor executor) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, executor.getName());
            preparedStatement.setString(2, executor.getNote());
            preparedStatement.setLong(3, executor.getId());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean delete(long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CMD)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }
}